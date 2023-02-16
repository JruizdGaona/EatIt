package com.example.eatit;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * @author Javier Ruiz de Gaona Tre.
 */
public class panelControl extends AppCompatActivity {

    // Declaración de variables.
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

    /**
     * Método que inicializa todos los componenetes de la Actividad.
     */
    private void inicializarVariables() {
        ajustes = findViewById(R.id.ajustes);
        logo = findViewById(R.id.login_logo);
        recetas = findViewById(R.id.recetas);
        menu = findViewById(R.id.menu);
    }

    /**
     * Método que inicia la actividad de Ajustes al pulsar el icono de la Actividad.
     */
    private void clickAjustes() {
        ajustes.setOnClickListener((View) -> Toast.makeText(panelControl.this, "Ajustes", Toast.LENGTH_SHORT).show());
    }

    /**
     * Método que inicia la actividad de Menú al pulsar el icono de la Actividad.
     */
    private void clickMenu() {
        menu.setOnClickListener((View) -> Toast.makeText(panelControl.this, "Menu", Toast.LENGTH_SHORT).show());
    }

    /**
     * Método que carga las recetas de la base de datos en el fragment de la Actividad.
     */
    private void cargarRecetas() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.panel_recetas, new fragment_principal());
        fragmentTransaction.commit();
    }
}
