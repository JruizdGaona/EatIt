package com.example.eatit.fragments.recetas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.eatit.R;
import com.example.eatit.entities.Receta;

import java.util.ArrayList;
import java.util.List;

public class FragmentBuscador extends Fragment {

    SearchView searchView;

    /**
     * Método onCreate del fragment de Inicio.
     * @param inflater Variable que inlfa el Layout en la actividad.
     * @param container Contenedor invisible que define la estructura de diseño de View
     * @param savedInstanceState Estado de la instancia de la aplicación.
     *
     * @return Vista creada.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_buscador_recetas, container, false);
    }

    /**
     * Método que se ejecuta una vez se ha creado el Fragment nuevo.
     * Llama al Fragment de Recetas para que las muestre en el Frame.
     * @param view Vista del Fragment creado.
     * @param savedInstanceState Estado de la instancia de la aplicación.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Fragment fragmentRecetas = new FrameRecetas();
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.frame_recetas, fragmentRecetas).commit();

        searchView = view.findViewById(R.id.search_view);
        searchView.clearFocus();
         searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
             @Override
             public boolean onQueryTextSubmit(String query) {
                 return false;
             }

             @Override
             public boolean onQueryTextChange(String newText) {
                 filtro(newText);
                 return true;
             }
         });
    }

    private void filtro(String newText) {
        List<Receta> listRecetasFiltradas = new ArrayList<>();
        List<Receta> recetas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            recetas.add(new Receta("Receta" + i, String.valueOf(i), null, i, i));
        }

        for (Receta r: recetas) {
            if (r.getNombre().toLowerCase().contains(newText.toLowerCase())) {
                listRecetasFiltradas.add(r);
            }
        }

        if (listRecetasFiltradas.isEmpty()) {
            Toast.makeText(getContext(), "Sin coincidencias", Toast.LENGTH_SHORT).show();
        } else {
            Fragment fragmentRecetas = new FrameRecetas(listRecetasFiltradas);
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_recetas, fragmentRecetas).commit();
        }
    }
}
