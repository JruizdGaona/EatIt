package com.example.eatit.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import com.example.eatit.R;
import com.example.eatit.entities.Usuario;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

/**
 * @author Javier Ruiz de Gaona Tre.
 */
public class FragmentAjustes extends Fragment {

    TextView textoNombreUsuario, textoCambiarContraseña, textoAjustes, nombreUsu;
    AppCompatButton btnGuardar;
    TextInputEditText nombreUsuarioET, contraseñaActualET, nuevaContraseñaET, repetirContraseñaET;
    TextInputLayout nombreUsuario, cambiarContraseña, nuevaContrasñea, repetirContraseña;
    FirebaseUser firebaseUser;
    FirebaseAuth auth;
    Usuario usuario;
    FirebaseFirestore database;

    /**
     * Constructor para el Fragment de Ajustes.
     * @param firebaseUser Usuario actual de la aplicación.
     */
    public FragmentAjustes (FirebaseUser firebaseUser, Usuario usuario, TextView nombreUsu) {
        this.firebaseUser = firebaseUser;
        this.usuario = usuario;
        this.nombreUsu = nombreUsu;
    }

    /**
     * Método onCreate del fragment de Ajustes.
     * @param inflater Variable que inlfa el Layout en la actividad.
     * @param container Contenedor invisible que define la estructura de diseño de View
     * @param savedInstanceState Estado de la instancia de la aplicación.
     *
     * @return Vista creada.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_ajustes, container, false);
    }

    /**
     * Método que se ejecuta una vez se ha creado el nuevo Fragment.
     * @param view Vista del nuevo fragment ya creada.
     * @param savedInstanceState Estado de la instancia de la aplicación.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        inicializarVariables(view);
        comprobarNombre();
        comprobarContraseñas();
        clickGuardar();

        view.setOnTouchListener((view1, motionEvent) -> ocultar());
    }

    /**
     * Método que inicializa todos los componentes de la Actividad.
     */
    private void inicializarVariables(@NonNull View view) {
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
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
    }

    private void comprobarNombre() {
        nombreUsuarioET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cambiarEstadoBoton(!charSequence.toString().isBlank());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty()) {
                    if (editable.toString().matches("[a-zA-ZáéíóúÁÉÍÓÚS\\s]{1,10}")) {
                        nombreUsuario.setError(null);
                        cambiarEstadoBoton(true);
                    } else {
                        nombreUsuario.setError("Nombre de usuario no válido");
                        cambiarEstadoBoton(false);
                    }
                } else {
                    nombreUsuario.setError("Nombre de usuario vacío, no se actualizará");
                    cambiarEstadoBoton(true);
                }

                if(editable.length() > 10) {
                    nombreUsuario.setError("Máximo caracteres alcanzado");
                }
            }
        });
    }

    private void comprobarContraseñas() {
        nuevaContraseñaET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cambiarEstadoBoton(!charSequence.toString().isBlank());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isBlank()) {
                    if (editable.toString().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,15}$")) {
                        nuevaContrasñea.setError(null);
                        cambiarEstadoBoton(true);
                    } else {
                        nuevaContrasñea.setError("Min 8 Max 15 | 1 Mayuscula | 1 Minuscula | 1 Numero | 1 Caracter especial @#$%^&+=");
                        cambiarEstadoBoton(false);
                    }
                } else {
                    nuevaContrasñea.setError("Contraseña vacía, no se actualizará");
                    cambiarEstadoBoton(true);
                }

                if(editable.length() > 15) {
                    nuevaContrasñea.setError("Máximo caracteres alcanzado");
                }
            }
        });

        repetirContraseñaET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cambiarEstadoBoton(!charSequence.toString().isBlank());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isBlank()) {
                    if (editable.toString().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,15}$")) {
                        repetirContraseña.setError(null);
                        cambiarEstadoBoton(true);
                    } else {
                        repetirContraseña.setError("Min 8 Max 15 | 1 Mayuscula | 1 Minuscula | 1 Numero | 1 Caracter especial @#$%^&+=");
                        cambiarEstadoBoton(false);
                    }
                }else {
                    repetirContraseña.setError("Contraseña vacía, no se actualizará");
                    cambiarEstadoBoton(true);
                }

                if(editable.length() > 15) {
                    repetirContraseña.setError("Máximo caracteres alcanzado");
                }
            }
        });
    }

    private void cambiarEstadoBoton(boolean estado) {
        this.btnGuardar.setEnabled(estado);

        if (!estado) {
            this.btnGuardar.setBackgroundResource(R.drawable.btn_login_disabled);
        } else {
            this.btnGuardar.setBackgroundResource(R.drawable.btn_login);
        }
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
            Toast.makeText(FragmentAjustes.this.getContext(), "No se ha cambiado ningún dato", Toast.LENGTH_SHORT).show();
        } else {
            Drawable draw = getResources().getDrawable(R.drawable.background_alert);
            new MaterialAlertDialogBuilder(this.requireContext(), R.style.alert)
                    .setTitle("Confirmar Cambios")
                    .setView(v)
                    .setBackground(draw)
                    .setPositiveButton("Confirmar", (dialogInterface, i) -> guardarCambios(nombreUsario, contraseñaActual, nuevaContraseña, repetirContraseña))
                    .setNegativeButton("Cancelar", (dialogInterface, i) -> dialogInterface.cancel())
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
        if (nombreUsario.length() != 0) {
            usuario.setNombreUsuario(nombreUsario);
            Task<QuerySnapshot> consulta = database.collection("usuarios").whereEqualTo("correo", usuario.getCorreo()).get();
            consulta.addOnSuccessListener(documentSnapshots -> {
                if (!documentSnapshots.isEmpty()) {
                    DocumentSnapshot documentSnapshot = documentSnapshots.getDocuments().get(0);
                    String id = documentSnapshot.getId();
                    DocumentReference userRef = database.collection("usuarios").document(id);
                    userRef.update("nombreUsuario", nombreUsario);

                    String text = (String) nombreUsu.getText();
                    String[] textInicio = text.split(", ");
                    String finalText = textInicio[0].concat(", ").concat(usuario.getNombreUsuario()).concat("!");
                    if (finalText.length() > 20) {
                        finalText = textInicio[0].concat(", ").concat("\n")
                                .concat(usuario.getNombreUsuario()).concat("!");
                    }
                    nombreUsu.setText(finalText);
                }
            });
            Toast.makeText(FragmentAjustes.this.getContext(), "Nombre de Usuario actualizado", Toast.LENGTH_LONG).show();
        }

        if (nuevaContraseña.length() != 0) {
            auth.signInWithEmailAndPassword(Objects.requireNonNull(firebaseUser.getEmail()), contraseñaActual).addOnCompleteListener((task) -> {
                if (task.isSuccessful()) {
                    if (repetirContraseña.matches(nuevaContraseña)) {
                        if (nuevaContraseña.length() < 6) {
                            Toast.makeText(FragmentAjustes.this.getContext(), "La contraseña debe tener como mínmo 6 caracteres", Toast.LENGTH_LONG).show();
                        } else {
                            firebaseUser.updatePassword(nuevaContraseña);
                            Toast.makeText(FragmentAjustes.this.getContext(), "Cambios guardados correctamente", Toast.LENGTH_SHORT).show();
                            nombreUsuarioET.setText("");
                            contraseñaActualET.setText("");
                            nuevaContraseñaET.setText("");
                            repetirContraseñaET.setText("");
                        }
                    } else {
                        Toast.makeText(FragmentAjustes.this.getContext(), "La nueva contraseña debe coincidir", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(FragmentAjustes.this.getContext(), "No se ha podido actualizar la contraseña", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * Método usado para cerrar el teclado al pulsar sobre otro lado de la pantalla.
     *
     * @return - True, si la vista es distinta de null, False si la View es null.
     */
    public boolean ocultar() {
        View view = this.requireActivity().getCurrentFocus();

        if (view != null) {
            InputMethodManager input = (InputMethodManager) (requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE));
            input.hideSoftInputFromWindow(view.getWindowToken(), 0);
            return true;
        } else {
            return false;
        }
    }
}
