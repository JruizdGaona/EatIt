package com.example.eatit.fragments.recetas.listar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.eatit.R;
import com.example.eatit.entities.Ingrediente;
import com.example.eatit.entities.Receta;
import com.example.eatit.entities.Usuario;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FragmentPasos extends Fragment {

    private Receta receta;
    private AppCompatTextView pasos;
    private ImageView siguiente, anterior;
    private int numPaso;
    private AppCompatButton botonSiguiente, botonFin, botonAnterior;
    private TextView textoPasos;
    private String email;
    private FirebaseFirestore database = FirebaseFirestore.getInstance();

    public FragmentPasos(Receta receta, int numPaso, String email) {
        this.receta = receta;
        this.numPaso = numPaso;
        this.email = email;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_elab_uno, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pasos = view.findViewById(R.id.pasos_receta);
        siguiente = view.findViewById(R.id.siguiente_paso);
        anterior = view.findViewById(R.id.anterior_paso);
        pasos.setText(receta.getPasos().get(String.valueOf(numPaso+1)));
        botonSiguiente = view.findViewById(R.id.btn_siguiente);
        botonAnterior = view.findViewById(R.id.btn_anterior);
        botonFin = view.findViewById(R.id.btn_finalizar);
        botonFin.setVisibility(View.INVISIBLE);
        textoPasos = view.findViewById(R.id.pasos);

        int showPaso = numPaso + 1;
        if (numPaso == 0) textoPasos.setText(textoPasos.getText().toString().replace("PASO 1", "PASO " + 1));
        else textoPasos.setText(textoPasos.getText().toString().replace("PASO 1", "PASO " + showPaso));

        gestionPasos();
    }

    private void gestionPasos() {
        if (numPaso == 0 && numPaso == receta.getPasos().size() - 1) {
            siguiente.setVisibility(View.INVISIBLE);
            botonSiguiente.setVisibility(View.INVISIBLE);
            botonFin.setVisibility(View.VISIBLE);

            botonFin.setOnClickListener((View) -> {
                cargarRecetas();
                actualizarPopularidadRecetas();
                actualizarEstadoIngredientes();
            });

            anterior.setOnClickListener((View) -> {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.from_left, R.anim.to_right);
                fragmentTransaction.replace(R.id.frame_info, new FragmentRecetas(receta, email));
                fragmentTransaction.commit();
            });

            botonAnterior.setOnClickListener((View) -> {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.from_left, R.anim.to_right);
                fragmentTransaction.replace(R.id.frame_info, new FragmentRecetas(receta, email));
                fragmentTransaction.commit();
            });
        } else if (numPaso == 0) {
            siguiente.setOnClickListener((View) -> {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.from_right, R.anim.to_left);
                fragmentTransaction.replace(R.id.frame_info, new FragmentPasos(receta, numPaso + 1, email));
                fragmentTransaction.commit();
            });

            anterior.setOnClickListener((View) -> {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.from_left, R.anim.to_right);
                fragmentTransaction.replace(R.id.frame_info, new FragmentRecetas(receta, email));
                fragmentTransaction.commit();
            });

            botonSiguiente.setOnClickListener((View) -> {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.from_right, R.anim.to_left);
                fragmentTransaction.replace(R.id.frame_info, new FragmentPasos(receta, numPaso + 1, email));
                fragmentTransaction.commit();
            });

            botonAnterior.setOnClickListener((View) -> {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.from_left, R.anim.to_right);
                fragmentTransaction.replace(R.id.frame_info, new FragmentRecetas(receta, email));
                fragmentTransaction.commit();
            });
        } else if (numPaso == receta.getPasos().size() - 1) {
            siguiente.setVisibility(View.INVISIBLE);
            botonSiguiente.setVisibility(View.INVISIBLE);
            botonFin.setVisibility(View.VISIBLE);

            botonFin.setOnClickListener((View) -> {
                cargarRecetas();
                actualizarPopularidadRecetas();
                actualizarEstadoIngredientes();
            });

            anterior.setOnClickListener((View) -> {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.from_left, R.anim.to_right);
                fragmentTransaction.replace(R.id.frame_info, new FragmentPasos(receta, numPaso - 1, email));
                fragmentTransaction.commit();
            });

            botonAnterior.setOnClickListener((View) -> {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.from_left, R.anim.to_right);
                fragmentTransaction.replace(R.id.frame_info, new FragmentPasos(receta, numPaso - 1, email));
                fragmentTransaction.commit();
            });
        } else {
            siguiente.setOnClickListener((View) -> {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.from_right, R.anim.to_left);
                fragmentTransaction.replace(R.id.frame_info, new FragmentPasos(receta, numPaso + 1, email));
                fragmentTransaction.commit();
            });

            anterior.setOnClickListener((View) -> {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.from_left, R.anim.to_right);
                fragmentTransaction.replace(R.id.frame_info, new FragmentPasos(receta, numPaso - 1, email));
                fragmentTransaction.commit();
            });

            botonSiguiente.setOnClickListener((View) -> {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.from_right, R.anim.to_left);
                fragmentTransaction.replace(R.id.frame_info, new FragmentPasos(receta, numPaso + 1, email));
                fragmentTransaction.commit();
            });

            botonAnterior.setOnClickListener((View) -> {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.from_left, R.anim.to_right);
                fragmentTransaction.replace(R.id.frame_info, new FragmentPasos(receta, numPaso - 1, email));
                fragmentTransaction.commit();
            });
        }
    }

    private void cargarRecetas() {
        Task<QuerySnapshot> obtenerUsuario = database.collection("usuarios").whereEqualTo("correo", email).get();
        obtenerUsuario.addOnSuccessListener(usuarioSnapshot -> {
            Usuario usuario;

            if (!usuarioSnapshot.isEmpty()) {
                DocumentSnapshot documentSnapshotUsuario = usuarioSnapshot.getDocuments().get(0);
                String idUsuario = documentSnapshotUsuario.getId();
                DocumentReference userRef = database.collection("usuarios").document(idUsuario);

                usuario = documentSnapshotUsuario.toObject(Usuario.class);
                List<Receta> recetasFinalizadas = usuario.getRecetasGuardadas();

                if (recetasFinalizadas == null) recetasFinalizadas = new ArrayList<>();

                if (!recetasFinalizadas.contains(receta)) {
                    recetasFinalizadas.add(receta);
                    usuario.setRecetasGuardadas(recetasFinalizadas);

                    userRef.update("recetasGuardadas", usuario.getRecetasGuardadas());
                }

                Toast.makeText(getContext(), "¡Receta Completada!", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            } else {
                Toast.makeText(getContext(), "Error al completar la Receta", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void actualizarPopularidadRecetas() {
        Task<QuerySnapshot> obtenerReceta = database.collection("recetas").whereEqualTo("nombre", receta.getNombre()).get();
        obtenerReceta.addOnSuccessListener(recetaSnapshot -> {
            Receta receta;

            if (!recetaSnapshot.isEmpty()) {
                DocumentSnapshot documentSnapshotReceta = recetaSnapshot.getDocuments().get(0);
                String idReceta = documentSnapshotReceta.getId();
                DocumentReference recetaRef = database.collection("recetas").document(idReceta);

                receta = documentSnapshotReceta.toObject(Receta.class);
                int popularidad = receta.getPopularidad();

                popularidad++;
                receta.setPopularidad(popularidad);

                recetaRef.update("popularidad", receta.getPopularidad());
            }
        });
    }

    private void actualizarEstadoIngredientes() {
        for (String nombreIngrediente: receta.getIngredientes()) {
            Task<QuerySnapshot> obtenerIngrediente = database.collection("ingredientes").whereEqualTo("nombre", nombreIngrediente).get();

            obtenerIngrediente.addOnSuccessListener(ingredienteSnapshot -> {
               Ingrediente ingrediente;

               if (!ingredienteSnapshot.isEmpty()) {
                   DocumentSnapshot documentSnapshotIngrediente = ingredienteSnapshot.getDocuments().get(0);
                   String idIngrediente = documentSnapshotIngrediente.getId();
                   DocumentReference ingredienteRef = database.collection("ingredientes").document(idIngrediente);

                   ingrediente = documentSnapshotIngrediente.toObject(Ingrediente.class);
                   ingrediente.setDesactivado(true);

                   ingredienteRef.update("desactivado", ingrediente.isDesactivado());
               }
            });
        }
    }
}
