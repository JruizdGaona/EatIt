package com.example.eatit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class registro extends Activity {

    // Declaramos las variables.
    ImageView logoApp;
    TextView textoRegistro, textoCorreo, textoContraseña, textoContraseñaOlvidada, textoNombre, textoApellido, textoLogin;
    AppCompatButton botonRegistro;
    TextInputEditText contraseñaET, correoET, nombreET, apellidoET;
    TextInputLayout contraseña, correo, nombre, apellido;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        final ProgressDialog progressDialog = new ProgressDialog(this);

        inicializarVariables();
        cambiarEstadoBoton(false);

        comprobarCampos();
        clickLogin();
        clickBotonRegistro(progressDialog);
    }

    /**
     * Método que inicializa todos los componentes de la Actividad.
     */
    private void inicializarVariables() {
        logoApp = findViewById(R.id.registro_logo);
        textoRegistro = findViewById(R.id.registro_titulo);
        textoCorreo = findViewById(R.id.registro_texto_correo);
        textoContraseña = findViewById(R.id.registro_texto_contraseña);
        textoNombre = findViewById(R.id.registro_texto_nombre);
        textoApellido = findViewById(R.id.registro_texto_apellido);
        textoLogin = findViewById(R.id.registro_texto_login);
        botonRegistro = findViewById(R.id.btn_registro);
        contraseñaET = findViewById(R.id.registro_textInput_contraseña);
        correoET = findViewById(R.id.registro_textInput_correo);
        nombreET = findViewById(R.id.registro_textInput_nombre);
        apellidoET = findViewById(R.id.registro_textInput_apellido);
        contraseña = findViewById(R.id.registro_layoutTextInput_contraseña);
        correo = findViewById(R.id.registro_layoutTextInput_correo);
        nombre = findViewById(R.id.registro_layoutTextInput_nombre);
        apellido = findViewById(R.id.registro_layoutTextInput_apellido);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    /**
     * Método para comprobar que los datos del usuario son válidos.
     */
    private void comprobarCampos() {
        comprobarNombre();
        comprobarApellido();
        comprobarCorreo();
        comprobarContraseña();
    }

    /**
     * Método que comprueba el nombre del Usuario.
     */
    private void comprobarNombre() {
        nombreET.addTextChangedListener(new TextWatcher() {
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
                if(!editable.toString().isEmpty() && editable.toString().matches("[a-zA-ZáéíóúÁÉÍÓÚS\\s]{1,25}")) {
                    if(correoET.getText().toString().isEmpty() || contraseñaET.getText().toString().isEmpty()){
                        nombre.setError(null);
                        cambiarEstadoBoton(false);
                    } else {
                        nombre.setError(null);
                        cambiarEstadoBoton(true);
                    }
                }else {
                    nombre.setError("Solo caracteres Alfanuméricos");
                    cambiarEstadoBoton(false);
                }
                if(editable.length() > 25) {
                    nombre.setError("Máximo caracteres alcanzado");
                }
            }
        });
    }

    /**
     * Método que comprueba que el apellido del usuario es válido
     */
    private void comprobarApellido() {
        apellidoET.addTextChangedListener(new TextWatcher() {
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
                if(!editable.toString().isEmpty() && editable.toString().matches("[a-zA-ZáéíóúÁÉÍÓÚS\\s]{1,25}")) {
                    if(apellidoET.getText().toString().isEmpty() || contraseñaET.getText().toString().isEmpty()){
                        apellido.setError(null);
                        cambiarEstadoBoton(false);
                    } else {
                        apellido.setError(null);
                        cambiarEstadoBoton(true);
                    }
                }else {
                    apellido.setError("Solo caracteres Alfanuméricos");
                    cambiarEstadoBoton(false);
                }

                if(editable.length() > 25) {
                    apellido.setError("Máximo caracteres alcanzado");
                }
            }
        });
    }

    /**
     * Método que compueba el correo electrónico introducido por el usuario.
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
                if(!editable.toString().isEmpty() && editable.toString().matches("^[A-Za-z0-9]+@[a-z]+\\.[a-z]+$")) {
                    if(nombreET.getText().toString().isEmpty() || contraseñaET.getText().toString().isEmpty()){
                        correo.setError(null);
                        cambiarEstadoBoton(false);
                    } else {
                        correo.setError(null);
                        cambiarEstadoBoton(true);
                    }
                }else {
                    correo.setError("Correo electrónico no válido");
                    cambiarEstadoBoton(false);
                }
            }
        });
    }

    /**
     * Método que comprueba la contraseña del ususario.
     */
    private void comprobarContraseña() {
        contraseñaET.addTextChangedListener(new TextWatcher() {
            boolean condEmail = correoET.getText().toString().matches("^[A-Za-z0-9]+@[a-z]\\.[a-z]+$");
            boolean condContraseña = contraseñaET.getText().toString().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,15}$");
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
                if(!editable.toString().isEmpty() && editable.toString().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,15}$")) {
                    if(nombreET.getText().toString().isEmpty() || correoET.getText().toString().isEmpty()){
                        contraseña.setError(null);
                        cambiarEstadoBoton(false);
                    }
                    else if (!condEmail && condContraseña){
                        correo.setError("Correo electrónico no válido");
                        contraseña.setError(null);
                        cambiarEstadoBoton(false);
                    } else {
                        contraseña.setError(null);
                        cambiarEstadoBoton(true);
                    }
                }else {
                    contraseña.setError("Min 8 Max 15 | 1 Mayuscula | 1 Minuscula | 1 Numero | 1 Caracter especial @#$%^&+=");
                    cambiarEstadoBoton(false);
                }

                if(editable.length() > 15) {
                    contraseña.setError("Máximo caracteres alcanzado");
                }
            }
        });
    }

    /**
     * Método que registra al usuario nuevo al pulsar el botón.
     */
    private void clickBotonRegistro(ProgressDialog progressDialog) {
        botonRegistro.setOnClickListener((View) -> {
            String correo = correoET.getText().toString();
            String contraseña = correoET.getText().toString();

            registroFirebase(correo, contraseña);
        });
    }

    /**
     * Método que registra un nuevo usuario en la aplicación.
     * @param correo Correo del nuevo usuario.
     * @param contraseña Contraseña del nuevo usuario.
     */
    private void registroFirebase(String correo, String contraseña) {
        firebaseAuth.createUserWithEmailAndPassword(correo, contraseña).addOnCompleteListener((task) -> {
            if (task.isSuccessful()) {
                enviarCorreoValidacion();
                startActivity(new Intent(registro.this, login.class));
            } else {
                Toast.makeText(registro.this, "Error al realizar el registro, pruebe más tarde", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Método que envía al usuario un correo de validación de la cuenta.
     */
    private void enviarCorreoValidacion() {
        firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(registro.this, "Usuario registrado, verifique su correo", Toast.LENGTH_LONG).show();
                correoET.setText("");
                contraseñaET.setText("");
                nombreET.setText("");
                apellidoET.setText("");
            } else {
                Toast.makeText(registro.this, "Error al realizar el registro, pruebe más tarde", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Método que comprueba el estado del botón de Registro y lo cambia.
     * @param estado Estado al que queremos poner el botón.
     */
    private void cambiarEstadoBoton(boolean estado) {
        this.botonRegistro.setEnabled(estado);

        if (!this.botonRegistro.isEnabled()) {
            this.botonRegistro.setBackgroundResource(R.drawable.btn_login_disabled);
        } else {
            this.botonRegistro.setBackgroundResource(R.drawable.btn_login);
        }
    }

    /**
     * Método que al pulsar sobre Ya tengo cuenta nos manda a la pantalla de login.
     */
    private void clickLogin() {
        textoLogin.setOnClickListener((View) -> {
            this.finish();
            startActivity(new Intent(registro.this, login.class));
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
