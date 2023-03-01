package com.example.eatit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.eatit.R;
import com.example.eatit.entities.Ingrediente;
import com.example.eatit.fragments.adapters.AdapterIngrediente;
import java.util.ArrayList;
import java.util.List;

public class FragmentIngredientes extends Fragment {
    List<Ingrediente> ingredientes = new ArrayList<>();
    RecyclerView recyclerView;
    AdapterIngrediente adapterIngrediente;

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
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_ingredientes,container,false);
        recyclerView = view.findViewById(R.id.fragment_ingredientes);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));

        mostrarIngredientes();
        return view;
    }

    /**
     * Método que crea las recetas y las carga en el adapter de recetas.
     */
    private void mostrarIngredientes() {
        for (int i = 0; i < 20; i++) {
            String nombre = "Ingrediente" +i;

            ingredientes.add(new Ingrediente(nombre));
        }
        adapterIngrediente = new AdapterIngrediente(ingredientes, FragmentIngredientes.this.getContext());
        recyclerView.setAdapter(adapterIngrediente);
    }
}
