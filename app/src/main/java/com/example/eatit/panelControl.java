package com.example.eatit;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class panelControl extends AppCompatActivity {

    List<Receta> recetas = new ArrayList<>();
    RecyclerView recyclerView;
    AdapterReceta adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.panel_recetas, new fragment_principal());
        fragmentTransaction.commit();
        //recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));

        //mostrarRecetas();
    }

    private void mostrarRecetas() {
        for (int i = 0; i < 20; i++) {
            String nombre = "Receta" +i;
            recetas.add(new Receta(nombre));
        }
        adapter = new AdapterReceta(recetas, getApplicationContext());
        //recyclerView.setAdapter(adapter);
    }
}
