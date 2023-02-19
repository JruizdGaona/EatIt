package com.example.eatit;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.Objects;

/**
 * @author Javier Ruiz de Gaona Tre.
 */
public class AjustesActivity  extends Activity {

    // Declaramos las variables.
    ImageView logoApp, flecha;
    TextView textoNombreUsuario, textoCambiarContraseña, textoAjustes;
    AppCompatButton btnGuardar;
    TextInputEditText nombreUsuarioET, contraseñaActualET, nuevaContraseñaET, repetirContraseñaET;
    TextInputLayout nombreUsuario, cambiarContraseña, nuevaContrasñea, repetirContraseña;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        inicializarVariables();
        clickRetroceso();
        clickGuardar();
    }

    /**
     * Método que inicializa todos los componentes de la Actividad.
     */
    private void inicializarVariables() {
        logoApp = findViewById(R.id.login_logo);
        textoAjustes = findViewById(R.id.ajustes_titulo);
        textoCambiarContraseña = findViewById(R.id.ajustes_texto_contraseña_vieja);
        textoNombreUsuario = findViewById(R.id.ajustes_texto_nombre_usuario);
        btnGuardar = findViewById(R.id.btn_guardar);
        nombreUsuarioET = findViewById(R.id.ajustes_textInput_nombre_usuario);
        contraseñaActualET = findViewById(R.id.registro_textInput_contraseña);
        nuevaContraseñaET = findViewById(R.id.registro_textInput_contraseña_nueva);
        repetirContraseñaET = findViewById(R.id.registro_textInput_contraseña_nueva_repetir);
        nombreUsuario = findViewById(R.id.ajustes_layoutTextInput_nombre_usuario);
        cambiarContraseña = findViewById(R.id.ajustes_layoutTextInput_contraseña_vieja);
        nuevaContrasñea = findViewById(R.id.ajustes_layoutTextInput_contraseña_Nueva);
        repetirContraseña = findViewById(R.id.ajustes_layoutTextInput_contraseña_Nueva_repetir);
        flecha = findViewById(R.id.flecha_ajustes);
        loadingDialog = new LoadingDialog(this);
    }

    /**
     * Método que cierra la Actividad de Ajustes y nos envía al panel de control.
     */
    private void clickRetroceso() {
        flecha.setOnClickListener((View) -> this.finish());
    }

    /**
     * Método que muestra un AlertDialog para confirmar los cambios y guardarlos.
     */
    private void clickGuardar() {
        btnGuardar.setOnClickListener((View) -> {
            View v = LayoutInflater.from(AjustesActivity.this).inflate(R.layout.activity_confirmar_cambios, null);
            String nombreUsuario, contraseñaActual, nuevaContraseña, repetirContraseña;

            nombreUsuario = Objects.requireNonNull(nombreUsuarioET.getText()).toString();
            contraseñaActual = Objects.requireNonNull(contraseñaActualET.getText()).toString();
            nuevaContraseña = Objects.requireNonNull(nuevaContraseñaET.getText()).toString();
            repetirContraseña = Objects.requireNonNull(repetirContraseñaET.getText()).toString();

            crearAlertDialog(v, nombreUsuario, contraseñaActual, nuevaContraseña, repetirContraseña);
        });
    }

    /**
     * Método que crea el AlertDialog para confirmar los cambios.
     *
     * @param v View sobre la que vamos a crear el MaterialAlert.
     * @param nombreUsario Nuevo nombre de Usuario.
     * @param contraseñaActual Contraseña Actual del Usuario.
     * @param nuevaContraseña Nueva Contraseña del Usuario.
     * @param repetirContraseña Nueva Contraseña del Usuario Repetida.
     */
    private void crearAlertDialog(View v, String nombreUsario, String contraseñaActual, String nuevaContraseña, String repetirContraseña) {
        if (nombreUsario.length() == 0 && contraseñaActual.length() == 0 && nuevaContraseña.length() == 0 && repetirContraseña.length() == 0 ) {
            Toast.makeText(AjustesActivity.this, "No se ha cambiado ningún dato", Toast.LENGTH_SHORT).show();
        } else {
            new MaterialAlertDialogBuilder(AjustesActivity.this, R.style.MyThemeOverlay_MaterialComponents_MaterialAlertDialog)
                    .setTitle("Confirmar Cambios")
                    .setView(v)
                    .setPositiveButton("Confirmar", (dialogInterface, i) -> {
                                loadingDialog.showDialog("Guardando Cambios...");

                                guardarCambios(nombreUsario, contraseñaActual, nuevaContraseña, repetirContraseña);
                            }
                    ).setNegativeButton("Cancelar", (dialogInterface, i) -> dialogInterface.cancel())
                    .show();
        }
    }

    /**
     * Método que cambia y guarda los cambios del usuario.
     *
     * @param nombreUsario Nuevo nombre de Usuario.
     * @param contraseñaActual Contraseña Actual del Usuario.
     * @param nuevaContraseña Nueva Contraseña del Usuario.
     * @param repetirContraseña Nueva Contraseña del Usuario Repetida.
     */
    private void guardarCambios(String nombreUsario, String contraseñaActual, String nuevaContraseña, String repetirContraseña) {
        boolean cambiar = false;

        if (nombreUsario.length() != 0) {
            cambiar = true;
            loadingDialog.closeDialog();
            // Lógica
        }
        if (nuevaContraseña.length() != 0) {
            if (contraseñaActual.matches("Hola")) {
                if (repetirContraseña.matches(nuevaContraseña)) {
                    loadingDialog.closeDialog();
                    cambiar = true;
                    // Lógica
                } else {
                    loadingDialog.closeDialog();
                    Toast.makeText(AjustesActivity.this, "Las contraseñas nuevas deben coincidir", Toast.LENGTH_LONG).show();
                }
            } else {
                loadingDialog.closeDialog();
                Toast.makeText(AjustesActivity.this, "La contraseña Actual no es válida", Toast.LENGTH_SHORT).show();
            }
        }

        if (cambiar) {
            Toast.makeText(AjustesActivity.this, "Cambios Guardados Correctamente", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Método usado para cerrar el teclado al pulsar sobre otro lado de la pantalla.
     * @param event Objeto utilizado para informar eventos de movimiento.
     *
     * @return True, si la vista es distinta de null, False si la View es null.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        View view = this.getCurrentFocus();

        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            return true;
        } else {
            return false;
        }
    }
}
