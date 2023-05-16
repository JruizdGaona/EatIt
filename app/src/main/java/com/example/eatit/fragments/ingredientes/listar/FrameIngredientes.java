package com.example.eatit.fragments.ingredientes.listar;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.eatit.R;
import com.example.eatit.activities.ScanActivity;
import com.example.eatit.entities.Ingrediente;
import com.example.eatit.entities.Usuario;
import com.example.eatit.fragments.adapters.AdapterIngrediente;
import com.example.eatit.fragments.ingredientes.crear.CardAddIngrediente;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Javier Ruiz de Gaona Tre.
 */
public class FrameIngredientes extends Fragment {

    // Declaración de Variables.
    private static final int REQUEST_CODE_SCAN = 1001;
    RecyclerView recyclerView;
    AdapterIngrediente adapterIngrediente;
    Usuario usuario;
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    CollectionReference coleccion;
    private boolean paused = false, form = false, barcode = false;

    public FrameIngredientes (Usuario usuario) {this.usuario = usuario;}

    /**
     * Método onCreate del fragment de Ingredientes.
     * @param inflater Variable que inlfa el Layout en la actividad.
     * @param container Contenedor invisible que define la estructura de diseño de View
     * @param savedInstanceState Estado de la instancia de la aplicación.
     *
     * @return Vista creada.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.frame_ingredientes,container,false);
        recyclerView = view.findViewById(R.id.fragment_ingredientes);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mostrarIngredientes();

        FloatingActionButton btn_barcode = view.findViewById(R.id.btn_barcode);
        FloatingActionButton btn_formulario = view.findViewById(R.id.btn_formulario);

        btn_barcode.setOnClickListener((View) -> {
            paused = true;
            barcode = true;
            this.onPause();
        });

        btn_formulario.setOnClickListener((View) -> {
            paused = true;
            form = true;
            this.onPause();
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        if (paused && form) {
            paused = false;
            form = false;
            CardAddIngrediente cardAddIngrediente = new CardAddIngrediente(getContext(), usuario);

            cardAddIngrediente.operacionesCardView();
        } else if (paused && barcode) {
            paused = false;
            barcode = false;

            Intent intent = new Intent(getActivity(), ScanActivity.class);
            startActivityForResult(intent, REQUEST_CODE_SCAN);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SCAN) {
            if (resultCode == Activity.RESULT_OK) {
                // Aquí obtienes el resultado del escaneo
                String barcodeResult = data.getStringExtra("SCAN_RESULT");
                if (barcodeResult == null || barcodeResult.isBlank()) {
                    Toast.makeText(getContext(), "Error al escanear el código", Toast.LENGTH_SHORT).show();
                } else {
                    // Código para hacer la solicitud GET a la API de Open Food Facts
                    new MyAsyncTask(getContext(), usuario).execute(barcodeResult);
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        database = FirebaseFirestore.getInstance();
        coleccion = database.collection("ingredientes");

        coleccion.addSnapshotListener((snapshot, e) -> {
            if (e != null) {
                return;
            }
            mostrarIngredientes();
        });
    }

    /**
     * Método que crea los ingredientes y los carga en el adapter de ingredientes.
     */
    private void mostrarIngredientes() {
        Task<QuerySnapshot> obtenerUsuario = database.collection("usuarios").whereEqualTo("correo", usuario.getCorreo()).get();

        obtenerUsuario.addOnSuccessListener(usuarioSnapshot -> {
            if (!usuarioSnapshot.isEmpty()) {
                DocumentSnapshot documentSnapshotUsuario = usuarioSnapshot.getDocuments().get(0);
                String idUsuario = documentSnapshotUsuario.getId();

                Task<QuerySnapshot> obtenerIngredientesUsuario = database.collection("ingredientes").whereEqualTo("usuarioId", idUsuario).get();

                obtenerIngredientesUsuario.addOnSuccessListener(ingredientesSnapshot -> {
                    List<Ingrediente> ingredientes = new ArrayList<>();

                    if (!ingredientesSnapshot.isEmpty()) {
                        List<DocumentSnapshot> documents = ingredientesSnapshot.getDocuments();
                        if (!documents.isEmpty()) {
                            for (DocumentSnapshot ds: documents) {
                                Ingrediente ing = ds.toObject(Ingrediente.class);
                                ingredientes.add(ing);
                            }
                        }
                    }

                    adapterIngrediente = new AdapterIngrediente(ingredientes, FrameIngredientes.this.getContext(), usuario);
                    recyclerView.setAdapter(adapterIngrediente);
                });
            }
        });
    }

    private static class MyAsyncTask extends AsyncTask<String, Void, String> {
        private WeakReference<Context> contextRef;
        private Usuario usuario;

        public MyAsyncTask(Context context, Usuario usuario) {
            contextRef = new WeakReference<>(context);
            this.usuario = usuario;
        }

        @Override
        protected String doInBackground(String... params) {
            String barcodeResult = params[0];

            OkHttpClient client = new OkHttpClient();

            String url = "https://world.openfoodfacts.org/api/v0/product/" + barcodeResult + ".json";
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    return response.body().string();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String responseBody) {
            Context context = contextRef.get();
            if (context != null) {
                if (responseBody != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody);
                        JSONObject productObject = jsonObject.getJSONObject("product");
                        Ingrediente ingrediente = new Ingrediente();

                        if (productObject.has("product_name")) {
                            String name = productObject.getString("product_name");
                            ingrediente.setNombre(name);
                        }

                        if (productObject.has("expiration_date")) {
                            String date = productObject.getString("expiration_date");
                            if (!date.isBlank()) {
                                String dateFinal = date.replaceAll("-","/");
                                String formattedDate = "";

                                try {
                                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                                    Date parsedDate = inputFormat.parse(date);

                                    SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
                                    formattedDate = outputFormat.format(parsedDate);

                                    ingrediente.setFechaCaducidad(formattedDate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Calendar calendar = Calendar.getInstance();

                                int year = calendar.get(Calendar.YEAR);
                                int month = calendar.get(Calendar.MONTH) + 1;
                                int day = calendar.get(Calendar.DAY_OF_MONTH);

                                String fechaCaducidad = day + "/" + month + "/" + year;

                                ingrediente.setFechaCaducidad(fechaCaducidad);

                            }
                        } else {
                            Calendar calendar = Calendar.getInstance();

                            int year = calendar.get(Calendar.YEAR);
                            int month = calendar.get(Calendar.MONTH) + 1;
                            int day = calendar.get(Calendar.DAY_OF_MONTH);

                            String fechaCaducidad = day + "/" + month + "/" + year;

                            ingrediente.setFechaCaducidad(fechaCaducidad);

                        }

                        CardAddIngrediente cardAddIngrediente = new CardAddIngrediente(context, 2, ingrediente, usuario);
                        cardAddIngrediente.operacionesCardView();
                    } catch (JSONException e) {
                        Toast.makeText(context, "Exception: Error al cargar el ingrediente", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Error al cargar el ingrediente", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
