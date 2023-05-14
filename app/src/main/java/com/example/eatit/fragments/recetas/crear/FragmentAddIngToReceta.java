package com.example.eatit.fragments.recetas.crear;

import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.eatit.R;
import com.example.eatit.entities.Ingrediente;
import com.example.eatit.entities.Receta;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragmentAddIngToReceta extends Fragment {

    private Receta receta;
    private AppCompatTextView dificultad, tiempo, comensales;
    private AppCompatButton botonNext;
    private LinearLayout checkBoxContainer;
    private ImageView img_avanzar, img_retroceso;
    private String email;
    private FirebaseFirestore database = FirebaseFirestore.getInstance();

    public FragmentAddIngToReceta(Receta receta, String email) {
        this.receta = receta;
        this.email = email;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_ingredientes_receta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inicializarVariables(view);

        cargarIngredientes();
        clickSiguienteBtn();
        clickSiguienteImg();
        clickAnteriorImg();
    }

    private void inicializarVariables(View view) {
        dificultad = view.findViewById(R.id.text_dificultad);
        tiempo = view.findViewById(R.id.text_tiempo);
        comensales = view.findViewById(R.id.text_comensales);
        botonNext = view.findViewById(R.id.btn_next);
        checkBoxContainer = view.findViewById(R.id.container_ch);
        img_avanzar = view.findViewById(R.id.siguiente_paso);
        img_retroceso = view.findViewById(R.id.anterior_paso);

        dificultad.setText(receta.getDificultad());
        tiempo.setText(String.valueOf(receta.getDuracion()));
        comensales.setText(String.valueOf(receta.getRaciones()));
    }

    private void clickSiguienteBtn() {
        botonNext.setOnClickListener((view) -> {
            List<String> opcionesSeleccionadas = new ArrayList<>();

            for (int i = 0; i < checkBoxContainer.getChildCount(); i++) {
                View viewch = checkBoxContainer.getChildAt(i);

                if (viewch instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) viewch;

                    if (checkBox.isChecked()) {
                        opcionesSeleccionadas.add(checkBox.getText().toString());
                    }
                }
            }
            if (opcionesSeleccionadas.size() > 0) {
                if (receta.getIngredientes() != null && receta.getIngredientes().size() > 0) {
                    receta.getIngredientes().clear();
                    receta.setIngredientes(opcionesSeleccionadas);
                } else {
                    receta.setIngredientes(opcionesSeleccionadas);
                }

                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.from_right, R.anim.to_left);
                fragmentTransaction.replace(R.id.frame_info, new FragmentPasosRecetas(receta, 0, email));
                fragmentTransaction.commit();
            } else {
                Toast.makeText(getContext(), "Debes seleccionar, por lo menos, un ingrediente", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clickSiguienteImg() {
        img_avanzar.setOnClickListener((view) -> {
            List<String> opcionesSeleccionadas = new ArrayList<>();

            for (int i = 0; i < checkBoxContainer.getChildCount(); i++) {
                View viewch = checkBoxContainer.getChildAt(i);

                if (viewch instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) viewch;

                    if (checkBox.isChecked()) {
                        opcionesSeleccionadas.add(checkBox.getText().toString());
                    }
                }
            }

            if (opcionesSeleccionadas.size() > 0) {
                if (receta.getIngredientes().size() > 0) {
                    receta.getIngredientes().clear();
                    receta.setIngredientes(opcionesSeleccionadas);
                } else {
                    receta.setIngredientes(opcionesSeleccionadas);
                }

                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.from_right, R.anim.to_left);
                fragmentTransaction.replace(R.id.frame_info, new FragmentPasosRecetas(receta, 0, email));
                fragmentTransaction.commit();
            } else {
                Toast.makeText(getContext(), "Debes seleccionar, por lo menos, un ingrediente", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clickAnteriorImg() {
        img_retroceso.setOnClickListener((view) -> {
            List<String> opcionesSeleccionadas = new ArrayList<>();

            for (int i = 0; i < checkBoxContainer.getChildCount(); i++) {
                View viewch = checkBoxContainer.getChildAt(i);

                if (viewch instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) viewch;

                    if (checkBox.isChecked()) {
                        opcionesSeleccionadas.add(checkBox.getText().toString());
                    }
                }
            }
            receta.setIngredientes(opcionesSeleccionadas);

            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.from_left, R.anim.to_right);
            fragmentTransaction.replace(R.id.frame_info, new FragmentCrearReceta(email, receta));
            fragmentTransaction.commit();
        });
    }

    private void cargarIngredientes() {
        Task<QuerySnapshot> obtenerUsuario = database.collection("usuarios").whereEqualTo("correo", email).get();
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

                    List<Ingrediente> ingredientesToShow = new ArrayList<>();

                    for (Ingrediente i: ingredientes) {
                        if (!i.isDesactivado()) {
                            ingredientesToShow.add(i);
                        }
                    }

                    rellenarCheckbox(ingredientesToShow);
                });
            }
        });
    }

    private void rellenarCheckbox(List<Ingrediente> ingredientes) {
        for (Ingrediente i: ingredientes) {
            CheckBox checkBox = new CheckBox(getContext());

            checkBox.setPadding(20,20,20,20);
            checkBox.setTextColor(ContextCompat.getColor(requireContext(),R.color.doradoClaro));
            checkBox.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.doradoClaro)));
            Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.montserrat_medio);
            checkBox.setTypeface(typeface);

            if (receta.getIngredientes() != null && receta.getIngredientes().size() > 0) {
                if (receta.getIngredientes().contains(i.getNombre())) {
                    checkBox.setChecked(true);
                }
            }

            if (comprobarFecha(i)) {
                checkBox.setText(i.getNombre());
                checkBox.setTextColor(ContextCompat.getColor(requireContext(), R.color.caducado));
            } else {
                checkBox.setText(i.getNombre());
                checkBox.setTextColor(ContextCompat.getColor(requireContext(), R.color.doradoClaro));
            }

            checkBoxContainer.addView(checkBox);
        }
    }

    private boolean comprobarFecha(Ingrediente ingrediente) {
        String fechaCad = ingrediente.getFechaCaducidad();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date fecha = formato.parse(fechaCad);

            return fecha.before(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }
}
