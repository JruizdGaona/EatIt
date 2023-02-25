package com.example.eatit.utils;

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
import java.util.ArrayList;
import java.util.List;

/**
 * @author Javier Ruiz de Gaona Tre.
 */
public class FragmentRecetas extends Fragment {

    // Declaración de Variables.
    List<Receta> recetas = new ArrayList<>();
    RecyclerView recyclerView;
    AdapterReceta adapter;

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
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_recetas,container,false);
        recyclerView = view.findViewById(R.id.fragment_recetas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        mostrarRecetas();
        return view;
    }

    /**
     * Método que crea las recetas y las carga en el adapter de recetas.
     */
    private void mostrarRecetas() {
        for (int i = 0; i < 20; i++) {
            String nombre = "Receta" +i;
            String dificultad = "Dificultad " +i;

            recetas.add(new Receta(nombre, dificultad, null, (float) i, 0));
        }
        adapter = new AdapterReceta(recetas, FragmentRecetas.this.getContext());
        recyclerView.setAdapter(adapter);
    }
}
