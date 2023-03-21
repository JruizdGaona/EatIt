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
import com.example.eatit.entities.Usuario;
import com.example.eatit.fragments.adapters.AdapterMisRecetas;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Javier Ruiz de Gaona Tre.
 */
public class FrameMisRecetas extends Fragment {

    // Declaramos las Variables.
    List<Receta> recetas = new ArrayList<>();
    RecyclerView recyclerView;
    AdapterMisRecetas adapterMisRecetas;
    Usuario usuario;

    public FrameMisRecetas(Usuario usuario) {
        this.usuario = usuario;
    }

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

        return view;
    }

    /**
     * Método que se ejecuta una vez se ha creado el Fragment nuevo.
     * Llama al Fragment de recetas para que las muestre en el Frame.
     * @param view Vista del Fragment creado.
     * @param savedInstanceState Estado de la instancia de la aplicación.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mostrarRecetas();
        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingActionButtonMisRecetas);

        floatingActionButton.setOnClickListener((View) -> {
            CardAddRecetas cardAddRecetas = new CardAddRecetas(getContext(), usuario);

            cardAddRecetas.operacionesCardView();
        });
    }

    /**
     * Método que crea los ingredientes y los carga en el adapter de ingredientes.
     */
    private void mostrarRecetas() {
        recetas = usuario.getRecetasCreadas();
        adapterMisRecetas = new AdapterMisRecetas(recetas, FrameMisRecetas.this.getContext());
        recyclerView.setAdapter(adapterMisRecetas);
    }
}
