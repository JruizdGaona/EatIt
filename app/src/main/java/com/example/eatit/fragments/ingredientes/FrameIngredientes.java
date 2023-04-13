package com.example.eatit.fragments.ingredientes;

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
import com.example.eatit.entities.Ingrediente;
import com.example.eatit.fragments.adapters.AdapterIngrediente;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Javier Ruiz de Gaona Tre.
 */
public class FrameIngredientes extends Fragment {

    // Declaración de Variables.
    List<Ingrediente> ingredientes = new ArrayList<>();
    RecyclerView recyclerView;
    AdapterIngrediente adapterIngrediente;

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
        View view = LayoutInflater.from(getContext()).inflate(R.layout.frame_ingredientes,container,false);
        recyclerView = view.findViewById(R.id.fragment_ingredientes);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));

        FloatingActionButton floatingActionButton = view.findViewById(R.id.btn_flotante);

        floatingActionButton.setOnClickListener((View) -> {
            CardAddIngrediente cardAddIngrediente = new CardAddIngrediente(getContext());

            cardAddIngrediente.operacionesCardView();
        });

        mostrarIngredientes();
        return view;
    }

    /**
     * Método que crea los ingredientes y los carga en el adapter de ingredientes.
     */
    private void mostrarIngredientes() {
        for (int i = 0; i < 20; i++) {
            String nombre = "Ingrediente" + i;
            String fecha = "12/02/202" + i;
            String tipo = "Carne";
            float kcal = i;
            float cantidad = i;

            ingredientes.add(new Ingrediente(nombre, fecha, tipo, kcal, cantidad));
        }
        adapterIngrediente = new AdapterIngrediente(ingredientes, FrameIngredientes.this.getContext());
        recyclerView.setAdapter(adapterIngrediente);
    }
}
