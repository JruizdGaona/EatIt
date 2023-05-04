package com.example.eatit.fragments.recetas;

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
import com.example.eatit.entities.Usuario;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FragmentAddIngToReceta extends Fragment {

    private Receta receta;
    private AppCompatTextView dificultad, tiempo, comensales;
    private AppCompatButton botonNext;
    private LinearLayout checkBoxContainer;
    private ImageView img_avanzar, img_retroceso;
    private String email;
    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private CollectionReference coleccion = database.collection("recetas");

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
            receta.setIngredientes(opcionesSeleccionadas);

            Toast.makeText(getContext(), "Se han seleccionado " + opcionesSeleccionadas.size(), Toast.LENGTH_SHORT).show();

            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.from_right, R.anim.to_left);
            fragmentTransaction.replace(R.id.frame_info, new FragmentPasosRecetas(receta, 0, email));
            fragmentTransaction.commit();
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
            receta.setIngredientes(opcionesSeleccionadas);

            Toast.makeText(getContext(), "Se han seleccionado " + opcionesSeleccionadas.size(), Toast.LENGTH_SHORT).show();

            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.from_right, R.anim.to_left);
            fragmentTransaction.replace(R.id.frame_info, new FragmentPasosRecetas(receta, 0, email));
            fragmentTransaction.commit();
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

            Toast.makeText(getContext(), "Se han seleccionado " + opcionesSeleccionadas.size(), Toast.LENGTH_SHORT).show();

            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.from_left, R.anim.to_right);
            fragmentTransaction.replace(R.id.frame_info, new FragmentCrearReceta(email));
            fragmentTransaction.commit();
        });
    }

    private void cargarIngredientes() {
        Task<QuerySnapshot> obtenerUsuario = database.collection("usuarios").whereEqualTo("correo", email).get();
        obtenerUsuario.addOnSuccessListener(usuarioSnapshot -> {
            Usuario usuario;

            if (!usuarioSnapshot.isEmpty()) {
                DocumentSnapshot documentSnapshotUsuario = usuarioSnapshot.getDocuments().get(0);
                String idUsuario = documentSnapshotUsuario.getId();
                DocumentReference userRef = database.collection("usuarios").document(idUsuario);

                usuario = documentSnapshotUsuario.toObject(Usuario.class);
                List<Ingrediente> ingredientes = usuario.getIngredientes();
                rellenarCheckbox(ingredientes);
            }
        });
    }

    private void rellenarCheckbox(List<Ingrediente> ingredientes) {
        for (int i = 0; i < ingredientes.size(); i++) {
            CheckBox checkBox = new CheckBox(getContext());

            checkBox.setPadding(20,20,20,20);
            checkBox.setTextColor(ContextCompat.getColor(requireContext(),R.color.doradoClaro));
            checkBox.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(),R.color.doradoClaro)));
            Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.montserrat_medio);
            checkBox.setTypeface(typeface);

            checkBox.setText(ingredientes.get(i).getNombre());
            checkBoxContainer.addView(checkBox);
        }
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
}
