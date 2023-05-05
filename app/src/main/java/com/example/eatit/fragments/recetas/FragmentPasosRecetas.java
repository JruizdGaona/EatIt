package com.example.eatit.fragments.recetas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.eatit.R;
import com.example.eatit.entities.Receta;
import com.example.eatit.entities.Usuario;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FragmentPasosRecetas extends Fragment {

    private Receta receta;
    private AppCompatButton guardar, nuevoPaso;
    private int pasoActual;
    private TextInputEditText pasoET;
    private String paso;
    private ImageView img_avanzar, img_retroceso;
    private String email;
    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    CollectionReference coleccion = database.collection("recetas");

    public FragmentPasosRecetas(Receta receta, int paso, String email) {
        this.receta = receta;
        this.email = email;
        pasoActual = paso + 1;
        if (receta.getPasos() == null) {
            receta.setPasos(new ArrayList<>());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_pasos_receta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inicializarVariables(view);
        crearNuevoPaso();
        guardarReceta();
        clickSiguienteImg();
        clickAnteriorImg();
    }

    private void inicializarVariables(View view) {
        guardar = view.findViewById(R.id.btn_guardar);
        nuevoPaso = view.findViewById(R.id.btn_next);
        pasoET = view.findViewById(R.id.textInput_pasos);
        img_avanzar = view.findViewById(R.id.siguiente_paso);
        img_retroceso = view.findViewById(R.id.anterior_paso);
    }

    private void crearNuevoPaso() {
        nuevoPaso.setOnClickListener((view) -> {
            paso = pasoET.getText().toString();

            List<String> pasosActuales = receta.getPasos();
            pasosActuales.add(paso);

            receta.setPasos(pasosActuales);

            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.from_right, R.anim.to_left);
            fragmentTransaction.replace(R.id.frame_info, new FragmentPasosRecetas(receta, pasoActual, email));
            fragmentTransaction.commit();
        });
    }

    private void clickSiguienteImg() {
        img_avanzar.setOnClickListener((view) -> {
            paso = pasoET.getText().toString();

            List<String> pasosActuales = receta.getPasos();
            pasosActuales.add(paso);

            receta.setPasos(pasosActuales);

            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.from_right, R.anim.to_left);
            fragmentTransaction.replace(R.id.frame_info, new FragmentPasosRecetas(receta, pasoActual, email));
            fragmentTransaction.commit();
        });
    }

    private void clickAnteriorImg() {
        img_retroceso.setOnClickListener((view) -> {
            if (pasoActual == 1) {
                paso = pasoET.getText().toString();

                List<String> pasosActuales = receta.getPasos();
                pasosActuales.add(paso);

                receta.setPasos(pasosActuales);

                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.from_left, R.anim.to_right);
                fragmentTransaction.replace(R.id.frame_info, new FragmentAddIngToReceta(receta, email));
                fragmentTransaction.commit();
            } else {
                paso = pasoET.getText().toString();

                List<String> pasosActuales = receta.getPasos();
                pasosActuales.add(paso);

                receta.setPasos(pasosActuales);

                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.from_left, R.anim.to_right);
                fragmentTransaction.replace(R.id.frame_info, new FragmentPasosRecetas(receta, pasoActual -1, email));
                fragmentTransaction.commit();
            }
        });
    }

    private void guardarReceta() {
        guardar.setOnClickListener((view) -> {
            paso = pasoET.getText().toString();
            List<String> pasosActuales = receta.getPasos();
            pasosActuales.add(paso);

            receta.setPasos(pasosActuales);

            Task<QuerySnapshot> obtenerUsuario = database.collection("usuarios").whereEqualTo("correo", email).get();
            obtenerUsuario.addOnSuccessListener(usuarioSnapshot -> {
                Usuario usuario;

                if (!usuarioSnapshot.isEmpty()) {
                    DocumentSnapshot documentSnapshotUsuario = usuarioSnapshot.getDocuments().get(0);
                    String idUsuario = documentSnapshotUsuario.getId();
                    DocumentReference userRef = database.collection("usuarios").document(idUsuario);

                    usuario = documentSnapshotUsuario.toObject(Usuario.class);
                    List<Receta> recetas = usuario.getRecetasCreadas();

                    if (recetas == null) recetas = new ArrayList<>();
                    receta.setUsuarioId(idUsuario);
                    recetas.add(receta);
                    usuario.setRecetasCreadas(recetas);

                    userRef.update("recetasCreadas", usuario.getRecetasCreadas());
                    coleccion.add(receta);

                    Toast.makeText(getContext(), "Receta creada correctamente", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                } else {
                    Toast.makeText(getContext(), "Error al crear la receta", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
