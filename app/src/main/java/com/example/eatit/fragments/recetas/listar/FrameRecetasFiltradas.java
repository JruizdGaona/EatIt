package com.example.eatit.fragments.recetas.listar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eatit.R;
import com.example.eatit.entities.Receta;
import com.example.eatit.fragments.adapters.recetas.AdapterReceta;
import com.example.eatit.fragments.adapters.recetas.AdapterRecetasFiltradas;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FrameRecetasFiltradas extends Fragment {

    List<Receta> recetas = new ArrayList<>();
    RecyclerView recyclerView;
    AdapterReceta adapter;
    AdapterRecetasFiltradas adapterFiltro;
    String email;
    boolean filtro = false;
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    CollectionReference coleccion = database.collection("recetas");

    /**
     * Construcotr por defecto del Frame.
     */
    public FrameRecetasFiltradas(String email) {this.email = email;}

    /**
     * Constructor que usamos para cargar las Recetas según la búsqueda del usuario.
     * @param filtradas Lista con las recetas ya Filtradas según la búsqueda.
     */
    public FrameRecetasFiltradas (List<Receta> filtradas, String email) {
        this.recetas = filtradas;
        this.email = email;
        filtro = true;
    }

    /**
     * Método onCreate del fragment de Recetas.
     * @param inflater Variable que inlfa el Layout en la actividad.
     * @param container Contenedor invisible que define la estructura de diseño de View
     * @param savedInstanceState Estado de la instancia de la aplicación.
     *
     * @return Vista creada.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.frame_recetas,container,false);
        recyclerView = view.findViewById(R.id.fragment_recetas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        if (!filtro) mostrarRecetas();
        else mostrarRecetasFiltradas();
        return view;
    }

    /**
     * Método que crea las recetas y las carga en el adapter de recetas.
     */
    private void mostrarRecetas() {
        Task<QuerySnapshot> obtenerRecetasPopulares = coleccion.get();

        obtenerRecetasPopulares.addOnSuccessListener(recetaSnapshot -> {
            List<Receta> recetasPopulares = new ArrayList<>();

            if (!recetaSnapshot.isEmpty()) {
                for (DocumentSnapshot snapshot: recetaSnapshot) {
                    Receta r = snapshot.toObject(Receta.class);
                    recetasPopulares.add(r);
                }
            }

            adapter = new AdapterReceta(recetasPopulares, FrameRecetasFiltradas.this.getContext(), email);
            recyclerView.setAdapter(adapter);
        });
    }

    /**
     * Método que muestra las recetas Filtradas por la búsqueda del Usuario.
     */
    private void mostrarRecetasFiltradas() {
        adapterFiltro = new AdapterRecetasFiltradas(recetas, FrameRecetasFiltradas.this.getContext(), email);
        recyclerView.setAdapter(adapterFiltro);
    }
}
