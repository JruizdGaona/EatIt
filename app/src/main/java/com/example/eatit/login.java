package com.example.eatit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class login extends Activity {

    // Declaramos las variables.
    ImageView logoApp;
    TextView textoLogin, textoCorreo, textoContraseña, textoContraseñaOlvidada, textoRegistrarse;
    CheckBox mantenerSesion;
    AppCompatButton botonLogin;
    TextInputEditText contraseñaET, correoET;
    TextInputLayout contraseña, correo;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final ProgressDialog progressDialog = new ProgressDialog(this);

        inicializarVariables();
        cambiarEstadoBoton(false);

        comprobarCorreo();
        comprobarContraseña();
        clickRegistrarse();
        clickBotonLogin(progressDialog);
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
    }

    /**
     * Método que comprueba que el correo introducido cumple con las restricciones impuestas.
     */
    private void comprobarCorreo() {
        correoET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().isEmpty()) {
                    cambiarEstadoBoton(false);
                } else {
                    cambiarEstadoBoton(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()) {
                    if(correoET.getText().toString().isEmpty() || contraseñaET.getText().toString().isEmpty()){
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
                if(charSequence.toString().isEmpty()) {
                    cambiarEstadoBoton(false);
                } else {
                    cambiarEstadoBoton(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()) {
                    if(contraseñaET.getText().toString().isEmpty() || correoET.getText().toString().isEmpty()){
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
            startActivity(new Intent(login.this, registro.class));
        });
    }

    /**
     * Método que le da funcionalidad al botón de Inicio de Sesión.
     * @param progressDialog Muestra un indicador de progreso.
     */
    private void clickBotonLogin(ProgressDialog progressDialog) {
        botonLogin.setOnClickListener((View) -> {
            //progressDialog.show();
            String correo = correoET.getText().toString();
            String contraseña = contraseñaET.getText().toString();

            inicioSesionFirebase(correo, contraseña);
        });
    }

    /**
     * Método que Inicia Sesión a través de FirebaseAuth.
     * @param correo Correo Electrónico del usuario.
     * @param contraseña Contraseña del Usuario.
     */
    private void inicioSesionFirebase(String correo, String contraseña) {
        firebaseAuth.signInWithEmailAndPassword(correo, contraseña).addOnCompleteListener((task) -> {
            if (task.isSuccessful()) {
                if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                    this.finish();
                    startActivity(new Intent(login.this, panelControl.class));
                    Toast.makeText(login.this, "Hecho", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(login.this, "Correo no validado", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(login.this, "Correo o contraseña no válidos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Método usado para cerrar el teclado al pulsar sobre otro lado de la pantalla.
     * @param event - Objeto utilizado para informar eventos de movimiento.
     * @return - True, si la vista es distinta de null, False si la View es null.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Guardamos la vista seleccionada.
        View view = this.getCurrentFocus();

        // Si no es null (Tenemos una vista seleccionada), cerramos el teclado.
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            return true;
        } else {
            return false;
        }
    }
}
