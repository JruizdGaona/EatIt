package com.example.eatit.fragments.recetas.actualizar;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import java.util.Map;

public class FragmentEditarPasosReceta extends Fragment {
    private Receta receta;
    private AppCompatButton guardar, nuevoPaso;
    private int pasoActual, pasosReceta;
    private TextInputEditText pasoET;
    private String paso, recetaOldName;
    private ImageView img_avanzar, img_retroceso;
    private String email;
    private boolean pasoExistente;
    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    CollectionReference coleccion = database.collection("recetas");
    private Uri uri;

    public FragmentEditarPasosReceta(Receta receta, int paso, String email, String nombre) {
        this.receta = receta;
        this.email = email;
        this.pasoActual = paso;
        this.pasosReceta = receta.getPasos().size();
        this.recetaOldName = nombre;

        pasoExistente = pasoActual <= pasosReceta;
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
        siguientePaso();
        pasoAnterior();
        actualizarReceta();
        ocultar();
    }

    private void inicializarVariables(View view) {
        guardar = view.findViewById(R.id.btn_guardar);
        nuevoPaso = view.findViewById(R.id.btn_next);
        pasoET = view.findViewById(R.id.textInput_pasos);
        img_avanzar = view.findViewById(R.id.siguiente_paso);
        img_retroceso = view.findViewById(R.id.anterior_paso);

        if (pasoExistente) pasoET.setText(receta.getPasos().get(String.valueOf(pasoActual)));
    }

    private void siguientePaso() {
        img_avanzar.setOnClickListener((view) -> {
            if (pasoExistente) {
                paso = pasoET.getText().toString();
                receta.getPasos().replace(String.valueOf(pasoActual), receta.getPasos().get(String.valueOf(pasoActual)), paso);
                receta.setPasos(receta.getPasos());
            } else {
                Map<String, String> pasosActuales = receta.getPasos();
                paso = pasoET.getText().toString();
                pasosActuales.put(String.valueOf(pasoActual), paso);
                receta.setPasos(receta.getPasos());
            }

            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.from_right, R.anim.to_left);
            fragmentTransaction.replace(R.id.frame_info, new FragmentEditarPasosReceta(receta, pasoActual + 1, email, recetaOldName));
            fragmentTransaction.commit();
        });

        nuevoPaso.setOnClickListener(view -> {
            if (pasoExistente) {
                paso = pasoET.getText().toString();
                receta.getPasos().replace(String.valueOf(pasoActual), receta.getPasos().get(String.valueOf(pasoActual)), paso);
                receta.setPasos(receta.getPasos());
            } else {
                Map<String, String> pasosActuales = receta.getPasos();
                paso = pasoET.getText().toString();
                pasosActuales.put(String.valueOf(pasoActual), paso);
                receta.setPasos(receta.getPasos());
            }

            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.from_right, R.anim.to_left);
            fragmentTransaction.replace(R.id.frame_info, new FragmentEditarPasosReceta(receta, pasoActual + 1, email, recetaOldName));
            fragmentTransaction.commit();
        });
    }

    private void pasoAnterior() {
        img_retroceso.setOnClickListener((view) -> {
            if (pasoActual != 1) {
                if (pasoExistente) {
                    paso = pasoET.getText().toString();
                    receta.getPasos().replace(String.valueOf(pasoActual), receta.getPasos().get(String.valueOf(pasoActual)), paso);
                    receta.setPasos(receta.getPasos());
                } else {
                    Map<String, String> pasosActuales = receta.getPasos();
                    paso = pasoET.getText().toString();
                    pasosActuales.put(String.valueOf(pasoActual), paso);
                    receta.setPasos(receta.getPasos());
                }

                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.from_left, R.anim.to_right);
                fragmentTransaction.replace(R.id.frame_info, new FragmentEditarPasosReceta(receta, pasoActual - 1, email, recetaOldName));
                fragmentTransaction.commit();
            } else {
                if (pasoExistente) {
                    paso = pasoET.getText().toString();
                    receta.getPasos().replace(String.valueOf(pasoActual), receta.getPasos().get(String.valueOf(pasoActual)), paso);
                    receta.setPasos(receta.getPasos());
                } else {
                    Map<String, String> pasosActuales = receta.getPasos();
                    paso = pasoET.getText().toString();
                    pasosActuales.put(String.valueOf(pasoActual), paso);
                    receta.setPasos(receta.getPasos());
                }

                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.from_left, R.anim.to_right);
                fragmentTransaction.replace(R.id.frame_info, new FragmentEditarIngReceta(receta, email, recetaOldName, uri));
                fragmentTransaction.commit();
            }
        });
    }

    private void actualizarReceta() {
        guardar.setOnClickListener((view) -> {
            Toast.makeText(getContext(), "Uri: " + uri, Toast.LENGTH_SHORT).show();
            paso = pasoET.getText().toString();
            Map<String, String> pasosActuales = receta.getPasos();

            if (!pasosActuales.containsKey(pasoActual)) {
                pasosActuales.put(String.valueOf(pasoActual), paso);
            }

            receta.setPasos(pasosActuales);

            Task<QuerySnapshot> consulta = coleccion.whereEqualTo("nombre", recetaOldName).get();

            consulta.addOnSuccessListener(documentSnapshots -> {
                if (!documentSnapshots.isEmpty()) {
                    for (DocumentSnapshot documentSnapshot : documentSnapshots.getDocuments()) {
                        if (documentSnapshot.getString("nombre").equalsIgnoreCase(recetaOldName)) {
                            Receta oldReceta = documentSnapshot.toObject(Receta.class);
                            if (receta.getUri() == null) receta.setUri(oldReceta.getUri());
                            coleccion.document(documentSnapshot.getId())
                                    .update("nombre", receta.getNombre(),
                                            "dificultad", receta.getDificultad(),
                                            "duracion", receta.getDuracion(),
                                            "ingredientes", receta.getIngredientes(),
                                            "pasos", receta.getPasos(),
                                            "raciones", receta.getRaciones(),
                                            "uri", receta.getUri())
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(getContext(), "Receta actualizada correctamente", Toast.LENGTH_SHORT).show();
                                        getActivity().finish();
                                    }).addOnFailureListener(e -> {
                                        Toast.makeText(getContext(), "Error al actualizar la receta", Toast.LENGTH_SHORT).show();
                                        getActivity().finish();
                                    });
                            return;
                        }
                    }
                }
                Toast.makeText(getContext(), "Error al actualizar la receta", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }).addOnFailureListener(e -> {
                Toast.makeText(getContext(), "Error al actualizar la receta", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            });
        });
    }

    /**
     * Método usado para cerrar el teclado al pulsar sobre otro lado de la pantalla.
     *
     * @return - True, si la vista es distinta de null, False si la View es null.
     */
    public boolean ocultar() {
        View view = this.requireActivity().getCurrentFocus();

        if (view != null) {
            InputMethodManager input = (InputMethodManager) (requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE));
            input.hideSoftInputFromWindow(view.getWindowToken(), 0);
            return true;
        } else {
            return false;
        }
    }
}
