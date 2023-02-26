package com.example.eatit.utils;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import com.example.eatit.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.Objects;

public class FragmentAjustes extends Fragment {

    ImageView logoApp;
    TextView textoNombreUsuario, textoCambiarContraseña, textoAjustes;
    AppCompatButton btnGuardar;
    TextInputEditText nombreUsuarioET, contraseñaActualET, nuevaContraseñaET, repetirContraseñaET;
    TextInputLayout nombreUsuario, cambiarContraseña, nuevaContrasñea, repetirContraseña;
    LoadingDialog loadingDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_ajustes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        inicializarVariables(view);
        clickGuardar();
    }

    /**
     * Método que inicializa todos los componentes de la Actividad.
     */
    private void inicializarVariables(View view) {
        logoApp = view.findViewById(R.id.login_logo);
        textoAjustes = view.findViewById(R.id.ajustes_titulo);
        textoCambiarContraseña = view.findViewById(R.id.ajustes_texto_contraseña_vieja);
        textoNombreUsuario = view.findViewById(R.id.ajustes_texto_nombre_usuario);
        btnGuardar = view.findViewById(R.id.btn_guardar);
        nombreUsuarioET = view.findViewById(R.id.ajustes_textInput_nombre_usuario);
        contraseñaActualET = view.findViewById(R.id.registro_textInput_contraseña);
        nuevaContraseñaET = view.findViewById(R.id.registro_textInput_contraseña_nueva);
        repetirContraseñaET = view.findViewById(R.id.registro_textInput_contraseña_nueva_repetir);
        nombreUsuario = view.findViewById(R.id.ajustes_layoutTextInput_nombre_usuario);
        cambiarContraseña = view.findViewById(R.id.ajustes_layoutTextInput_contraseña_vieja);
        nuevaContrasñea = view.findViewById(R.id.ajustes_layoutTextInput_contraseña_Nueva);
        repetirContraseña = view.findViewById(R.id.ajustes_layoutTextInput_contraseña_Nueva_repetir);
        loadingDialog = new LoadingDialog(this.getContext());
    }

    /**
     * Método que muestra un AlertDialog para confirmar los cambios y guardarlos.
     */
    private void clickGuardar() {
        btnGuardar.setOnClickListener((View) -> {
            View v = LayoutInflater.from(this.getContext()).inflate(R.layout.activity_confirmar_cambios, null);
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
            Toast.makeText(this.getContext(), "No se ha cambiado ningún dato", Toast.LENGTH_SHORT).show();
        } else {
            new MaterialAlertDialogBuilder(this.requireContext(), R.style.MyThemeOverlay_MaterialComponents_MaterialAlertDialog)
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
                    Toast.makeText(this.getContext(), "Las contraseñas nuevas deben coincidir", Toast.LENGTH_LONG).show();
                }
            } else {
                loadingDialog.closeDialog();
                Toast.makeText(this.getContext(), "La contraseña Actual no es válida", Toast.LENGTH_SHORT).show();
            }
        }

        if (cambiar) {
            Toast.makeText(this.getContext(), "Cambios Guardados Correctamente", Toast.LENGTH_LONG).show();
        }
    }
}
