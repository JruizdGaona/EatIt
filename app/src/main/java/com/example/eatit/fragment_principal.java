package com.example.eatit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class fragment_principal extends Fragment {

    List<Receta> recetas = new ArrayList<>();
    RecyclerView recyclerView;
    AdapterReceta adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_recetas,container,false);
        recyclerView = view.findViewById(R.id.fragment_recetas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        mostrarRecetas();
        return view;
    }

    private void mostrarRecetas() {
        for (int i = 0; i < 20; i++) {
            String nombre = "Receta" +i;
            recetas.add(new Receta(nombre));
        }
        adapter = new AdapterReceta(recetas, fragment_principal.this.getContext());
        recyclerView.setAdapter(adapter);
    }
}
