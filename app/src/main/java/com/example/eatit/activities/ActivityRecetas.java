package com.example.eatit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.FragmentTransaction;

import com.example.eatit.R;
import com.example.eatit.entities.Receta;
import com.example.eatit.fragments.recetas.FragmentCrearReceta;
import com.example.eatit.fragments.recetas.FragmentRecetas;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.Serializable;

public class ActivityRecetas extends AppCompatActivity implements Serializable {

    private AppCompatImageView imagenRetroceso;
    private TextView nombreReceta;
    private ShapeableImageView imagenReceta;
    private Receta receta;
    private boolean crear = false;
    String email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_receta);

        inicializarVariables();
        cargarFragmentInicio();
        cerrarActivity();
    }

    private void inicializarVariables() {
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        receta = (Receta) intent.getSerializableExtra("receta");
        imagenRetroceso = findViewById(R.id.img_back);
        nombreReceta = findViewById(R.id.recetas);
        imagenReceta = findViewById(R.id.img_receta);
        if (receta != null) {
            nombreReceta.setText(receta.getNombre());
        } else crear = true;
    }

    private void cargarFragmentInicio() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (crear) {
            fragmentTransaction.replace(R.id.frame_info, new FragmentCrearReceta(email)).commit();
        } else {
            fragmentTransaction.replace(R.id.frame_info, new FragmentRecetas(receta, email)).commit();
        }
    }

    private void cerrarActivity() {
        imagenRetroceso.setOnClickListener((view) -> this.finish());
    }
}
