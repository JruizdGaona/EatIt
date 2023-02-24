package com.example.eatit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.eatit.R;
import com.example.eatit.utils.FragmentPrincipal;

import java.util.Objects;

/**
 * @author Javier Ruiz de Gaona Tre.
 */
public class PanelControlActivity extends AppCompatActivity {

    // Declaración de variables.
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ImageView logo;
    TextView recetas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        inicializarVariables();
        cargarRecetas();
        clickMenuLateral();
    }

    /**
     * Método que inicializa todos los componenetes de la Actividad.
     */
    private void inicializarVariables() {
        logo = findViewById(R.id.login_logo);
        recetas = findViewById(R.id.recetas);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
    }

    /**
     * Método que abre el menú lateral al pulsar sobre el botón de menú.
     */
    private void clickMenuLateral() {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.ajustes, R.string.menu);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
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

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
