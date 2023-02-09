package com.example.eatit;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class panelControl extends AppCompatActivity {

    ImageView ajustes, logo, menu;
    TextView recetas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        inicializarVariables();
        cargarRecetas();
        clickAjustes();
        clickMenu();
    }

    private void inicializarVariables() {
        ajustes = findViewById(R.id.ajustes);
        logo = findViewById(R.id.login_logo);
        recetas = findViewById(R.id.recetas);
        menu = findViewById(R.id.menu);
    }

    private void clickAjustes() {
        ajustes.setOnClickListener((View) -> {
            Toast.makeText(panelControl.this, "Ajustes", Toast.LENGTH_SHORT).show();
        });
    }

    private void clickMenu() {
        menu.setOnClickListener((View) -> {
            Toast.makeText(panelControl.this, "Menu", Toast.LENGTH_SHORT).show();
        });
    }

    private void cargarRecetas() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.panel_recetas, new fragment_principal());
        fragmentTransaction.commit();
    }
}
