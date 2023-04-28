package com.example.eatit.fragments.recetas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.eatit.R;
import com.example.eatit.entities.Receta;
import com.example.eatit.fragments.adapters.AdapterReceta;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Javier Ruiz de Gaona Tre.
 */
public class FrameRecetas extends Fragment {

    // Declaramos las Variables.
    List<Receta> recetas = new ArrayList<>();
    RecyclerView recyclerView;
    AdapterReceta adapter;
    boolean filtro = false;

    /**
     * Construcotr por defecto del Frame.
     */
    public FrameRecetas() {}

    /**
     * Constructor que usamos para cargar las Recetas según la búsqueda del usuario.
     * @param filtradas Lista con las recetas ya Filtradas según la búsqueda.
     */
    public FrameRecetas (List<Receta> filtradas) {
        this.recetas = filtradas;
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
        for (int i = 0; i < 20; i++) {
            String nombre = "Receta" +i;
            String dificultad = "Dificultad " +i;

            recetas.add(new Receta(nombre, dificultad, "Hola", i, null));
        }
        adapter = new AdapterReceta(recetas, FrameRecetas.this.getContext());
        recyclerView.setAdapter(adapter);
    }

    /**
     * Método que muestra las recetas Filtradas por la búsqueda del Usuario.
     */
    private void mostrarRecetasFiltradas() {
        adapter = new AdapterReceta(recetas, FrameRecetas.this.getContext());
        recyclerView.setAdapter(adapter);
    }
}
