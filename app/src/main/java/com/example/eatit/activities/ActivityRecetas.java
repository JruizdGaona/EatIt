package com.example.eatit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.FragmentTransaction;

import com.example.eatit.R;
import com.example.eatit.entities.Receta;
import com.example.eatit.fragments.recetas.FragmentCrearReceta;
import com.example.eatit.fragments.recetas.FragmentRecetas;
import com.google.android.material.imageview.ShapeableImageView;

public class ActivityRecetas extends AppCompatActivity {

    private AppCompatImageView imagenRetroceso;
    private TextView nombreReceta;
    private ShapeableImageView imagenReceta;
    private Receta receta;
    private int crear;

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
        receta = (Receta) intent.getSerializableExtra("receta");
        imagenRetroceso = findViewById(R.id.img_back);
        nombreReceta = findViewById(R.id.recetas);
        imagenReceta = findViewById(R.id.img_receta);
        if (receta != null) {
            nombreReceta.setText(receta.getNombre());
        } else crear = 1;
    }

    private void cargarFragmentInicio() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (crear == 1) {
            fragmentTransaction.replace(R.id.frame_info, new FragmentCrearReceta()).commit();
        } else {
            fragmentTransaction.replace(R.id.frame_info, new FragmentRecetas(receta)).commit();
        }
    }

    private void cerrarActivity() {
        imagenRetroceso.setOnClickListener((view) -> {
            this.finish();
        });
    }
}
