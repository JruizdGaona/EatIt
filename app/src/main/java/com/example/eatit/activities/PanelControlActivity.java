package com.example.eatit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.eatit.R;
import com.example.eatit.gestionUsers.LoginActivity;
import com.example.eatit.utils.FragmentPrincipal;

/**
 * @author Javier Ruiz de Gaona Tre.
 */
public class PanelControlActivity extends AppCompatActivity {

    // Declaración de variables.
    ImageView logo, menu, ajustes;
    TextView recetas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        inicializarVariables();
        cargarRecetas();
        clickMenu();
        clickAjustes();
    }

    /**
     * Método que inicializa todos los componenetes de la Actividad.
     */
    private void inicializarVariables() {
        logo = findViewById(R.id.login_logo);
        recetas = findViewById(R.id.recetas);
        menu = findViewById(R.id.menu);
        ajustes = findViewById(R.id.ajustes);
    }

    /**
     * Método que inicia la actividad de Ajustes al pulsar el icono de la Actividad.
     */
    private void clickAjustes() {
        ajustes.setOnClickListener((View) -> startActivity(new Intent(PanelControlActivity.this, AjustesActivity.class)));
    }

    /**
     * Método que inicia la actividad de Menú al pulsar el icono de la Actividad.
     */
    private void clickMenu() {
        menu.setOnClickListener((View) -> Toast.makeText(PanelControlActivity.this, "Menu", Toast.LENGTH_SHORT).show());
    }

    /**
     * Método que carga las recetas de la base de datos en el fragment de la Actividad.
     */
    private void cargarRecetas() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.panel_recetas, new FragmentPrincipal());
        fragmentTransaction.commit();
    }
}
