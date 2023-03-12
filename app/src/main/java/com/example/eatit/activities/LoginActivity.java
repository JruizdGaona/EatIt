package com.example.eatit.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import com.example.eatit.utils.LoadingDialog;
import com.example.eatit.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import java.util.Objects;

/**
 * @author Javier Ruiz de Gaona Tre.
 */
public class LoginActivity extends Activity {

    // Declaramos las variables.
    ImageView logoApp;
    TextView textoLogin, textoCorreo, textoContraseña, textoContraseñaOlvidada, textoRegistrarse;
    CheckBox mantenerSesion;
    AppCompatButton botonLogin;
    TextInputEditText contraseñaET, correoET;
    TextInputLayout contraseña, correo;
    FirebaseAuth firebaseAuth;
    LoadingDialog loadingDialog;
    boolean estaActivado;

    /**
     * Método que se ejecuta ak crear la actividad de Login.
     * @param savedInstanceState Estado de la instancia de la aplicación.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inicializarVariables();
        cambiarEstadoBoton(false);

        inicioAuto();
        chbxLogin();

        comprobarCorreo();
        comprobarContraseña();
        clickRegistrarse();
        clickRestaurarContraseña();
        clickBotonLogin();
    }

    /**
     * Método que inicializa todos los componentes de la Actividad.
     */
    private void inicializarVariables() {
        logoApp = findViewById(R.id.login_logo);
        textoLogin = findViewById(R.id.login_titulo);
        textoCorreo = findViewById(R.id.login_texto_correo);
        textoContraseña = findViewById(R.id.login_texto_contraseña);
        textoContraseñaOlvidada = findViewById(R.id.login_contraseña_olvidada);
        textoRegistrarse = findViewById(R.id.login_registrarse);
        mantenerSesion = findViewById(R.id.checkBoxLogin);
        botonLogin = findViewById(R.id.btn_login);
        contraseñaET = findViewById(R.id.login_textInput_contraseña);
        correoET = findViewById(R.id.login_textInput_correo);
        contraseña = findViewById(R.id.login_layoutTextInput_contraseña);
        correo = findViewById(R.id.login_layoutTextInput_correo);
        firebaseAuth = FirebaseAuth.getInstance();
        estaActivado = mantenerSesion.isChecked();
        loadingDialog = new LoadingDialog(this);
    }

    /**
     * Método que comprueba que el correo introducido cumple con las restricciones impuestas.
     */
    private void comprobarCorreo() {
        correoET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cambiarEstadoBoton(!charSequence.toString().isEmpty());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()) {
                    if(Objects.requireNonNull(correoET.getText()).toString().isEmpty() || Objects.requireNonNull(contraseñaET.getText()).toString().isEmpty()){
                        correo.setError(null);
                        cambiarEstadoBoton(false);
                    } else {
                        correo.setError(null);
                        cambiarEstadoBoton(true);
                    }
                }else {
                    correo.setError("El correo no puede estar vacío");
                    cambiarEstadoBoton(false);
                }
            }
        });
    }

    /**
     * Método que comprueba que la contraseña introducida cumple con las restricciones impuestas.
     */
    private void comprobarContraseña() {
        contraseñaET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cambiarEstadoBoton(!charSequence.toString().isEmpty());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()) {
                    if(Objects.requireNonNull(contraseñaET.getText()).toString().isEmpty() || Objects.requireNonNull(correoET.getText()).toString().isEmpty()){
                        contraseña.setError(null);
                        cambiarEstadoBoton(false);
                    }
                    else {
                        contraseña.setError(null);
                        cambiarEstadoBoton(true);
                    }
                }else {
                    contraseña.setError("La contraseña no puede estar vacía");
                    cambiarEstadoBoton(false);
                }
            }
        });
    }

    /**
     * Método que comprueba el estado del botón de Inicio de Sesión y lo cambia.
     * @param estado Estado al que queremos poner el botón.
     */
    private void cambiarEstadoBoton(boolean estado) {
        this.botonLogin.setEnabled(estado);

        if (!this.botonLogin.isEnabled()) {
            this.botonLogin.setBackgroundResource(R.drawable.btn_login_disabled);
        } else {
            this.botonLogin.setBackgroundResource(R.drawable.btn_login);
        }
    }

    /**
     * Método que al pulsar sobre Registrar, nos inicia la actividad de registro
     */
    private void clickRegistrarse() {
        textoRegistrarse.setOnClickListener((View) -> {
            this.finish();
            startActivity(new Intent(LoginActivity.this, RegistroActivity.class));
        });
    }

    /**
     * Método que permitirá al usuario cambiar la contraseña en caso de que se la haya olvidado.
     */
    private void clickRestaurarContraseña() {
        textoContraseñaOlvidada.setOnClickListener((View) -> {
            View v = LayoutInflater.from(LoginActivity.this).inflate(R.layout.activity_recordar_password, null);

            crearMaterialAlert(v);
        });
    }

    /**
     * Método que nos crea el Material Alert para que el usuario introduzca su correo para
     * reestablecer la contraseña.
     * @param v View sobre la que vamos a crear el MaterialAlert.
     */
    private void crearMaterialAlert(View v) {
        new MaterialAlertDialogBuilder(LoginActivity.this, R.style.MyThemeOverlay_MaterialComponents_MaterialAlertDialog)
                .setTitle("Recuperar Contraseña")
                .setView(v)
                .setPositiveButton("Enviar", (dialogInterface, i) -> {
                    loadingDialog.showDialog("Enviando Correo...");
                    EditText correoRecovery = v.findViewById(R.id.cambiarContraseña_editTextCorreo);
                    String correo = correoRecovery.getText().toString();

                    reestablecerContraseña(correo);
                }
        ).setNegativeButton("Cancelar", (dialogInterface, i) -> dialogInterface.cancel())
        .show();
    }

    /**
     * Método que envía el correo de reestablecimiento de la contraseña a través de FireBase.
     * @param correo Correo electrónico introducido por el usuario.
     */
    private void reestablecerContraseña(@NonNull String correo) {
        if (correo.isEmpty() || !correo.matches("^[A-Za-z0-9.]+@[a-z]+\\.[a-z]+$")) {
            loadingDialog.closeDialog();
            Toast.makeText(LoginActivity.this, "Correo electrónico no válido", Toast.LENGTH_LONG).show();
        } else {
            firebaseAuth.sendPasswordResetEmail(correo).addOnCompleteListener((Task) -> {
                if (Task.isSuccessful()) {
                    loadingDialog.closeDialog();
                    Toast.makeText(LoginActivity.this, "Correo de recuperación enviado", Toast.LENGTH_SHORT).show();
                } else {
                    loadingDialog.closeDialog();
                    Toast.makeText(LoginActivity.this, "Cuenta no registrada", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * Método que le da funcionalidad al botón de Inicio de Sesión.
     */
    private void clickBotonLogin() {
        botonLogin.setOnClickListener((View) -> {
            loadingDialog.showDialog("Iniciando sesión...");
            String correo = Objects.requireNonNull(correoET.getText()).toString();
            String contraseña = Objects.requireNonNull(contraseñaET.getText()).toString();

            inicioSesionFirebase(correo, contraseña);
        });
    }

    /**
     * Método que Inicia Sesión a través de FirebaseAuth.
     * @param correo Correo Electrónico del usuario.
     * @param contraseña Contraseña del Usuario.
     */
    private void inicioSesionFirebase(@NonNull String correo, @NonNull String contraseña) {
        firebaseAuth.signInWithEmailAndPassword(correo, contraseña).addOnCompleteListener((task) -> {
            if (task.isSuccessful()) {
                if (Objects.requireNonNull(firebaseAuth.getCurrentUser()).isEmailVerified()) {
                    this.finish();
                    startActivity(new Intent(LoginActivity.this, PanelControlActivity.class).putExtra("firebaseUser", firebaseAuth.getCurrentUser()));
                    loadingDialog.closeDialog();
                } else {
                    loadingDialog.closeDialog();
                    Toast.makeText(LoginActivity.this, "Correo no validado", Toast.LENGTH_SHORT).show();
                }
            } else {
                loadingDialog.closeDialog();
                Toast.makeText(LoginActivity.this, "Correo o contraseña no válidos", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * Método que nos envía a la pantalla de inicio si está marcado el checkbox.
     */
    private void inicioAuto() {
        if (obtenerEstadoCheckBox()) {
            this.finish();
            startActivity(new Intent(LoginActivity.this, PanelControlActivity.class).putExtra("firebaseUser", firebaseAuth.getCurrentUser()));
        }
    }

    /**
     * Método que le da la funcionalidad al CheckBox y guarda su valor.
     */
    private void chbxLogin() {
        mantenerSesion.setOnClickListener(view -> {
            if (estaActivado) {
                mantenerSesion.setChecked(false);
            }
            estaActivado = mantenerSesion.isChecked();
            guardarEstadoCheckBox();
        });
    }

    /**
     * Método que cambia el estado del checkbox de inicio de sesión automático.
     * @param c Interfaz a la información global sobre un entorno de aplicación.
     * @param b Boolean que recibe el valor del checkbox.
     */
    public static void cambiarEstadoCheckbox(@NonNull Context c, boolean b) {
        SharedPreferences sharedPreferences = c.getSharedPreferences("Correo", MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("BotonChecked", b).apply();
    }

    /**
     * Método que guarda el valor del Checkbox.
     */
    public void guardarEstadoCheckBox() {
        SharedPreferences sharedPreferences = getSharedPreferences("Correo", MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("BotonChecked", mantenerSesion.isChecked()).apply();
    }

    /**
     * Método que obtiene el valor del Checkbox.
     */
    public boolean obtenerEstadoCheckBox() {
        SharedPreferences sharedPreferences = getSharedPreferences("Correo", MODE_PRIVATE);
        return sharedPreferences.getBoolean("BotonChecked", false);
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
