package com.example.eatit.fragments.ingredientes;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.eatit.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * @author Javier Ruiz de Gaona Tre.
 */
public class FragmentAddIngredientes extends Fragment {

    /**
     * Método onCreate del fragment de AñadirIngrdientes.
     * @param inflater Variable que inlfa el Layout en la actividad.
     * @param container Contenedor invisible que define la estructura de diseño de View
     * @param savedInstanceState Estado de la instancia de la aplicación.
     *
     * @return Vista creada.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_ingredientes, container, false);
    }

    /**
     * Método que se ejecuta una vez se ha creado el nuevo Fragment.
     * @param view Vista del nuevo fragment ya creada.
     * @param savedInstanceState Estado de la instancia de la aplicación.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        FloatingActionButton floatingActionButton = view.findViewById(R.id.btn_flotante);

        floatingActionButton.setOnClickListener((View) -> {
            CardAddIngrediente cardAddIngrediente = new CardAddIngrediente(getContext());

            cardAddIngrediente.operacionesCardView();
        });
    }
}
