package com.example.eatit.fragments.recetas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.eatit.R;
import com.example.eatit.entities.Receta;
import com.example.eatit.fragments.adapters.AdapterMisRecetas;
import java.util.ArrayList;
import java.util.List;

public class FrameMisRecetas extends Fragment {

    // Declaración de Variables.
    List<Receta> recetas = new ArrayList<>();
    RecyclerView recyclerView;
    AdapterMisRecetas adapterMisRecetas;

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
        View view = LayoutInflater.from(getContext()).inflate(R.layout.frame_mis_recetas,container,false);
        recyclerView = view.findViewById(R.id.fragment_recetas);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));

        mostrarRecetas();
        return view;
    }

    /**
     * Método que crea los ingredientes y los carga en el adapter de ingredientes.
     */
    private void mostrarRecetas() {
        for (int i = 0; i < 20; i++) {
            String nombre = "Mis Recetas " +i;

            recetas.add(new Receta(nombre));
        }
        adapterMisRecetas = new AdapterMisRecetas(recetas, FrameMisRecetas.this.getContext());
        recyclerView.setAdapter(adapterMisRecetas);
    }
}
