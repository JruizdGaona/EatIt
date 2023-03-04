package com.example.eatit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import com.example.eatit.R;
import com.example.eatit.fragments.FragmentAjustes;
import com.example.eatit.fragments.FragmentInicio;
import com.example.eatit.fragments.ingredientes.FragmentMisIngredientes;
import com.example.eatit.fragments.ingredientes.FragmentAddIngredientes;
import com.example.eatit.fragments.recetas.FragmentMisRecetas;
import com.example.eatit.utils.LoadingDialog;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        inicializarVariables();
        clickMenuLateral();

        MenuItem menuItem = navigationView.getMenu().getItem(0);
        onNavigationItemSelected(menuItem);
        menuItem.setChecked(true);
    }

    /**
     * Método que inicializa todos los componenetes de la Actividad.
     */
    private void inicializarVariables() {
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
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.abierto, R.string.cerrado);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    /**
     * Método que cierra el menú lateral al pulsar el botón de retroceso del dispositivo.
     */
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Método que obtiene el Item que se selecciona del menú.
     * @param item Item seleccionado por el Usuario.
     *
     * @return true
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mostrarOpcion(item);
        return true;
    }

    /**
     * Método que cambia el Fragmento dependiendo del Item seleccionado por el usuario.
     * @param item Item que ha seleccionado el Usuario.
     */
    private void mostrarOpcion(@NonNull MenuItem item) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        LoadingDialog loadingDialog = new LoadingDialog(this);
        loadingDialog.showDialog("Cargando...");

        switch (item.getItemId()) {
            case R.id.inicio:
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame_inicio, new FragmentInicio());
                break;
            case R.id.nav_ingredientes2:
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame_inicio, new FragmentAddIngredientes());
                break;
            case R.id.nav_ingredientes3:
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame_inicio, new FragmentMisIngredientes());
                break;
            case R.id.nav_recetas2:
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                Toast.makeText(this, "Recomendador", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_recetas3:
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame_inicio, new FragmentMisRecetas());
                break;
            case R.id.nav_ajustes:
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame_inicio, new FragmentAjustes());
                break;
            case R.id.nav_sesion:
                LoginActivity.cambiarEstadoCheckbox(PanelControlActivity.this, false);
                this.finish();
                startActivity(new Intent(PanelControlActivity.this, LoginActivity.class));
                break;
            default:
                Toast.makeText(this, "Opción no Implementada aún", Toast.LENGTH_SHORT).show();
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        if (!isFinishing()) {
            new Handler().postDelayed(() -> {
                fragmentTransaction.commit();
                loadingDialog.closeDialog();
            }, 700);
        }
    }
}