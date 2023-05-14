package com.example.eatit.fragments.ingredientes.listar;

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
import com.example.eatit.entities.Ingrediente;
import com.example.eatit.entities.Usuario;
import com.example.eatit.fragments.adapters.AdapterIngrediente;
import com.example.eatit.fragments.ingredientes.crear.CardAddIngrediente;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Javier Ruiz de Gaona Tre.
 */
public class FrameIngredientes extends Fragment {

    // Declaración de Variables.
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
            Toast.makeText(this.getContext(), "Escaneando codigo de barras...", Toast.LENGTH_SHORT).show();
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
}