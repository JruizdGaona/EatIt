package com.example.eatit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.eatit.R;
import com.example.eatit.utils.FragmentPrincipal;
import com.google.android.material.navigation.NavigationView;
import java.util.Objects;

/**
 * @author Javier Ruiz de Gaona Tre.
 */
public class PanelControlActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Declaración de variables.
    NavigationView navigationView;
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

        MenuItem menuItem = navigationView.getMenu().getItem(0);
        onNavigationItemSelected(menuItem);
        menuItem.setChecked(true);
    }

    /**
     * Método que inicializa todos los componenetes de la Actividad.
     */
    private void inicializarVariables() {
        logo = findViewById(R.id.login_logo);
        recetas = findViewById(R.id.recetas);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolBar);
        navigationView = findViewById(R.id.menu_lateral);
        navigationView.setNavigationItemSelectedListener(this);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mostrarOpcion(item);
        return true;
    }

    private int mostrarOpcion(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.inicio:
                return 0;
            case R.id.nav_ingredientes2:
                Toast.makeText(this, "Añadir Ingredientes", Toast.LENGTH_SHORT).show();
                return 0;
            case R.id.nav_ingredientes3:
                Toast.makeText(this, "Ver Ingredientes", Toast.LENGTH_SHORT).show();
                return 0;
            case R.id.nav_recetas2:
                Toast.makeText(this, "Recomendador", Toast.LENGTH_SHORT).show();
                return 0;
            case R.id.nav_recetas3:
                Toast.makeText(this, "Ver Recetas", Toast.LENGTH_SHORT).show();
                return 0;
            case R.id.nav_ajustes:
                startActivity(new Intent(PanelControlActivity.this, AjustesActivity.class));
                return 0;
            case R.id.nav_sesion:
                return 0;
            default:
                Toast.makeText(this, "Opción no Implementada aún", Toast.LENGTH_SHORT).show();
        }
        return 0;
    }
}
