package com.example.eatit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import com.example.eatit.R;
import com.example.eatit.entities.Usuario;
import com.example.eatit.fragments.FragmentAjustes;
import com.example.eatit.fragments.FragmentInicio;
import com.example.eatit.fragments.ingredientes.FragmentMisIngredientes;
import com.example.eatit.fragments.ingredientes.FragmentAddIngredientes;
import com.example.eatit.fragments.recetas.FragmentBuscador;
import com.example.eatit.fragments.recetas.FragmentMisRecetas;
import com.example.eatit.utils.LoadingDialog;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

/**
 * @author Javier Ruiz de Gaona Tre.
 */
public class PanelControlActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Declaración de variables.
    NavigationView navigationView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    int status;
    TextView nombreUsu;
    FirebaseUser user;
    FirebaseFirestore database;
    CollectionReference coleccionUsuarios;
    MenuItem menuItem;
    Usuario usuario;

    /**
     * Método que se ejecuta al crear la activity de panel de control.
     * @param savedInstanceState Estado de la instancia de la aplicación.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        inicializarVariables();
        clickMenuLateral();

        onNavigationItemSelected(menuItem);
        menuItem.setChecked(true);
    }

    /**
     * Método que inicializa todos los componenetes de la Actividad.
     */
    private void inicializarVariables() {
        status = 0;
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolBar);
        navigationView = findViewById(R.id.menu_lateral);
        navigationView.setNavigationItemSelectedListener(this);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        user = getIntent().getParcelableExtra("firebaseUser");
        database = FirebaseFirestore.getInstance();
        coleccionUsuarios = database.collection("usuarios");
        menuItem = navigationView.getMenu().getItem(0);
        View headerView = navigationView.getHeaderView(0);
        nombreUsu = headerView.findViewById(R.id.msg_bienvenida);
    }

    /**
     * Método que obtiene el Usuario con el que se ha iniciado Sesión
     */
    private Usuario obtenerUsuarioActual() {
        Task<QuerySnapshot> consulta = database.collection("usuarios").whereEqualTo("correo", user.getEmail()).get();
        consulta.addOnSuccessListener(documentSnapshots -> {
            if (!documentSnapshots.isEmpty()) {
                DocumentSnapshot documentSnapshot = documentSnapshots.getDocuments().get(0);
                usuario = documentSnapshot.toObject(Usuario.class);
                if (usuario != null) {
                    String text = (String) nombreUsu.getText();
                    String[] textInicio = text.split(", ");
                    String finalText = textInicio[0].concat(", ").concat(usuario.getNombreUsuario()).concat("!");
                    if (finalText.length() > 20) {
                        finalText = textInicio[0].concat(", ").concat("\n")
                                .concat(usuario.getNombreUsuario()).concat("!");
                    }
                    nombreUsu.setText(finalText);
                }
            }
        });

        return usuario;
    }

    /**
     * Método que abre el menú lateral al pulsar sobre el botón de menú.
     */
    private void clickMenuLateral() {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.abierto, R.string.cerrado);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        this.usuario = obtenerUsuarioActual();
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

        switch (item.getItemId()) {
            case R.id.inicio:
                mostrarMensajesCarga(status, loadingDialog);
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame_inicio, new FragmentInicio());
                break;
            /*case R.id.nav_ingredientes2:
                mostrarMensajesCarga(1, loadingDialog);
                status = 1;
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame_inicio, new FragmentAddIngredientes());
                break;*/
            case R.id.nav_ingredientes3:
                mostrarMensajesCarga(1, loadingDialog);
                status = 1;
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame_inicio, new FragmentMisIngredientes());
                break;
            case R.id.nav_recetas2:
                mostrarMensajesCarga(1, loadingDialog);
                status = 1;
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame_inicio, new FragmentBuscador());
                break;
            case R.id.nav_recetas3:
                mostrarMensajesCarga(1, loadingDialog);
                status = 1;
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame_inicio, new FragmentMisRecetas(usuario));
                break;
            case R.id.nav_ajustes:
                mostrarMensajesCarga(1, loadingDialog);
                status = 1;
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame_inicio, new FragmentAjustes(user, usuario, nombreUsu));
                break;
            case R.id.nav_sesion:
                mostrarMensajesCarga(1, loadingDialog);
                status = 1;
                LoginActivity.cambiarEstadoCheckbox(PanelControlActivity.this, false);
                this.finish();
                startActivity(new Intent(PanelControlActivity.this, LoginActivity.class));
                break;
            default:
                mostrarMensajesCarga(1, loadingDialog);
                status = 1;
                Toast.makeText(this, "Opción no Implementada aún", Toast.LENGTH_SHORT).show();
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        if (!isFinishing()) {
            new Handler().postDelayed(() -> {
                fragmentTransaction.commit();
                cerrarMensajeCarga(status, loadingDialog);
            }, 700);
        }
    }

    /**
     * Método que muestra el loadingDialog al cargar los fragments, evitará mostrarlo en la primera
     * carga del fragment de inicio.
     *
     * @param status Estado de la actividad para mostrar la carga.
     * @param loadingDialog loadingDialog que queremos mostrar.
     */
    private void mostrarMensajesCarga(int status, LoadingDialog loadingDialog) {
        if (status != 0) loadingDialog.showDialog("Cargando...");
    }

    /**
     * Método que cierra el loadingDialog en todos los casos en los que se ha creado.
     *
     * @param status Estado de la actividad para cerrar la carga.
     * @param loadingDialog loadingDialog que queremos cerrar.
     */
    private void cerrarMensajeCarga(int status, LoadingDialog loadingDialog) {
        if (status != 0) loadingDialog.closeDialog();
    }
}