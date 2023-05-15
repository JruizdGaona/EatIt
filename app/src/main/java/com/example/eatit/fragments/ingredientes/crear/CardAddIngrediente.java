package com.example.eatit.fragments.ingredientes.crear;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import com.example.eatit.R;
import com.example.eatit.entities.Ingrediente;
import com.example.eatit.entities.Usuario;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * @author Javier Ruiz de Gaona Tre.
 */
public class CardAddIngrediente {

    // Declaramos las Variables.
    Context context;
    String[] tipoIngrediente = {"Sin especificar", "Carne", "Pescado", "Verdura", "Fruta", "Lácteos", "Cereales", "Otro"};
    int status;
    TextView nombre;
    DatePickerDialog datePicker;
    TextInputEditText name, fecha;
    TextInputLayout nameLayout,fechaLayout;
    String tipo;
    Ingrediente ingrediente;
    Spinner spinnerTipo;
    AppCompatButton guardar, actualizar;
    Usuario usuario;
    FirebaseFirestore database;
    CollectionReference coleccion;

    /**
     * Constructor de la Clase.
     * @param context Contexto del CardView nuevo.
     */
    public CardAddIngrediente (Context context, Usuario usuario) {
        this.context = context;
        this.usuario = usuario;
        database = FirebaseFirestore.getInstance();
        coleccion = database.collection("ingredientes");
    }

    /**
     * Constructor de la Clase cuando venimos del Fragment de Ver Ingrediente.
     * @param context Contexto del CardView nuevo.
     * @param status Estado desde el que se llama, 1 al venir del frgament de Ver Ingrediente.
     * @param i Ingrediente que estamos viendo.
     */
    public CardAddIngrediente (Context context, int status, Ingrediente i, Usuario usuario) {
        this.context = context;
        this.status = status;
        this.usuario = usuario;
        ingrediente = i;
        database = FirebaseFirestore.getInstance();
        coleccion = database.collection("ingredientes");
    }

    /**
     * Método que ejecuta las operaciones del CardView.
     */
    public void operacionesCardView() {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.card_add_ingredientes);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        inicializarVariables(dialog);
        comprobarNombre();
        comprobarFecha();
        cerrarTeclado(dialog);
        seleccionarFechaFocus();
        seleccionarFechaClick();
        guardarIngrediente(dialog);
        cerrarCardView(dialog);
        dialog.show();
    }

    /**
     * Método que inicializa las variables del CardView.
     * @param dialog Dialog en el que se encuentra el cardView.
     */
    private void inicializarVariables(@NonNull Dialog dialog) {
        guardar = dialog.findViewById(R.id.btn_guardar_ingrediente);
        actualizar = dialog.findViewById(R.id.btn_actualizar_ingrediente);
        nombre = dialog.findViewById(R.id.text_add_ingrediente);
        name = dialog.findViewById(R.id.login_textInput_nombreIngrediente);
        fecha = dialog.findViewById(R.id.login_textInput_caducidadIngrediente);
        fecha.setInputType(InputType.TYPE_NULL);
        spinnerTipo = dialog.findViewById(R.id.login_spinner_tipo_ingrediente);
        nameLayout = dialog.findViewById(R.id.login_layoutTextInput_nombre_ingrediente);
        fechaLayout = dialog.findViewById(R.id.login_layoutTextInput_caducidad_ingrediente);
        cambiarEstadoBoton(false);
        actualizar.setVisibility(View.INVISIBLE);
        guardar.setVisibility(View.VISIBLE);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.spinner_item, tipoIngrediente);
        spinnerTipo.setAdapter(adapter);

        spinnerTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tipo = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (status == 1) {
            nombre.setText(ingrediente.getNombre());
            name.setText(ingrediente.getNombre());
            fecha.setText(ingrediente.getFechaCaducidad());
            spinnerTipo.setSelection(setTipo(ingrediente.getTipo()));
            cambiarEstadoBoton(true);

            guardar.setVisibility(View.INVISIBLE);
            actualizar.setVisibility(View.VISIBLE);

            actualizar(dialog, ingrediente);
        }
    }

    private void comprobarNombre() {
        name.addTextChangedListener(new TextWatcher() {
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
                    if(Objects.requireNonNull(fecha.getText()).toString().isEmpty()){
                        nameLayout.setError(null);
                        cambiarEstadoBoton(false);
                    }
                    else {
                        nameLayout.setError(null);
                        cambiarEstadoBoton(true);
                    }
                }else {
                    nameLayout.setError("El nombre no puede estar vacío");
                    cambiarEstadoBoton(false);
                }
            }
        });
    }

    private void comprobarFecha() {
        fecha.addTextChangedListener(new TextWatcher() {
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
                    if(Objects.requireNonNull(name.getText()).toString().isEmpty()){
                        fechaLayout.setError(null);
                        cambiarEstadoBoton(false);
                    }
                    else {
                        fechaLayout.setError(null);
                        cambiarEstadoBoton(true);
                    }
                }else {
                    fechaLayout.setError("La fecha de caducidad no puede estar vacía");
                    cambiarEstadoBoton(false);
                }
            }
        });
    }

    private void cambiarEstadoBoton(boolean estado) {
        this.guardar.setEnabled(estado);
        this.actualizar.setEnabled(estado);

        if (!estado) {
            this.guardar.setBackgroundResource(R.drawable.btn_login_disabled);
            this.actualizar.setBackgroundResource(R.drawable.btn_login_disabled);
        } else {
            this.guardar.setBackgroundResource(R.drawable.btn_login);
            this.actualizar.setBackgroundResource(R.drawable.btn_login);
        }
    }

    private void actualizar(Dialog dialog, Ingrediente ingrediente) {
        actualizar.setOnClickListener((view) -> {
            Ingrediente newIngrediente = actualizarValoresIngrediente(ingrediente);

            actualizarIngrediente(dialog, ingrediente, newIngrediente);

        });
    }

    private Ingrediente actualizarValoresIngrediente(Ingrediente ingrediente) {
        if (name.getText() == null || name.getText().toString().isBlank()) {
            ingrediente.setNombre(ingrediente.getNombre());
        } else {
            ingrediente.setNombre(name.getText().toString());
        }

        if (fecha.getText() == null || fecha.getText().toString().isBlank()) {
            ingrediente.setFechaCaducidad(ingrediente.getFechaCaducidad());
        } else {
            ingrediente.setFechaCaducidad(fecha.getText().toString());
        }

        if (tipo == null || tipo.isBlank()) {
            ingrediente.setTipo(ingrediente.getTipo());
        } else {
            ingrediente.setTipo(tipo);
        }

        return ingrediente;
    }

    private void actualizarIngrediente(Dialog dialog, Ingrediente ingrediente, Ingrediente newIngrediente) {
        Task<QuerySnapshot> consulta = coleccion.whereEqualTo("nombre", ingrediente.getNombre()).get();

        consulta.addOnSuccessListener(documentSnapshots -> {
            if (!documentSnapshots.isEmpty()) {
                for (DocumentSnapshot documentSnapshot : documentSnapshots.getDocuments()) {
                    if (documentSnapshot.getString("nombre").equalsIgnoreCase(ingrediente.getNombre())) {
                        coleccion.document(documentSnapshot.getId())
                        .update("nombre", newIngrediente.getNombre(),
                            "fechaCaducidad", newIngrediente.getFechaCaducidad(),
                            "tipo", newIngrediente.getTipo())
                        .addOnSuccessListener(aVoid -> {
                            actualizarIngredienteDelUsuario(ingrediente, newIngrediente);
                            Toast.makeText(context, "Ingrediente actualizado correctamente", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }).addOnFailureListener(e -> {
                            Toast.makeText(context, "Error al actualizar el ingrediente", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        });
                        return;
                    }
                }
            }
            Toast.makeText(context, "Error al actualizar el ingrediente", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }).addOnFailureListener(e -> {
            Toast.makeText(context, "Error al actualizar el ingrediente", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
    }

    private void actualizarIngredienteDelUsuario(Ingrediente ingrediente, Ingrediente newIngrediente) {
        Task<QuerySnapshot> consultaUsuario = database.collection("usuarios").whereEqualTo("correo", usuario.getCorreo()).get();
        consultaUsuario.addOnSuccessListener(documentSnapshotsUsuario -> {
            if (!documentSnapshotsUsuario.isEmpty()) {
                List<Ingrediente> ingredientesUsuario = usuario.getIngredientes();

                for (int i = 0; i < ingredientesUsuario.size(); i++) {
                    if (ingredientesUsuario.get(i).getNombre().equalsIgnoreCase(ingrediente.getNombre())) {
                        ingredientesUsuario.set(i, newIngrediente);
                        break;
                    }
                }

                String usuarioId = documentSnapshotsUsuario.getDocuments().get(0).getId();
                database.collection("usuarios").document(usuarioId).update("ingredientes", ingredientesUsuario);
            }
        });
    }

    private int setTipo(String tipo) {
        for (int i = 0; i < tipoIngrediente.length; i++) {
            if (tipoIngrediente[i].equalsIgnoreCase(tipo)) {
                return i;
            }
        }
        return 0;
    }

    /**
     * Método usado para cerrar el teclado al pulsar sobre otro lado de la pantalla.
     * @param dialog Dialog en el que mostramos el cardView.
     */
    private void cerrarTeclado(@NonNull Dialog dialog) {
        CardView cardView = dialog.findViewById(R.id.cradView_add_ingredientes);

        cardView.setOnTouchListener((view, motionEvent) -> {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            return false;
        });
    }

    /**
     * Método que abre un DatePicker cuando el Focus está sobre el EditText de la fecha de
     * caducidad.
     */
    private void seleccionarFechaFocus() {
        fecha.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                final Calendar calendario = Calendar.getInstance();
                int dia = calendario.get(Calendar.DAY_OF_MONTH);
                int mes = calendario.get(Calendar.MONTH);
                int año = calendario.get(Calendar.YEAR);

                DatePickerDialog datePicker = new DatePickerDialog(context,
                        (view, year, monthOfYear, dayOfMonth) ->
                                fecha.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year)
                        , año, mes, dia);

                datePicker.show();
            }
        });
    }

    /**
     * Método que abre un DatePicker cuando se pulsa sobre el EdiText de la fecha de caducidad.
     */
    private void seleccionarFechaClick() {
        fecha.setOnClickListener(view -> {
            final Calendar calendario = Calendar.getInstance();
            int dia = calendario.get(Calendar.DAY_OF_MONTH);
            int mes = calendario.get(Calendar.MONTH);
            int año = calendario.get(Calendar.YEAR);

            datePicker = new DatePickerDialog(CardAddIngrediente.this.context,
                    (view1, year, monthOfYear, dayOfMonth) ->
                            fecha.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year)
                    , año, mes, dia);

            datePicker.show();
        });
    }

    private void guardarIngrediente(Dialog dialog) {
        guardar.setOnClickListener((View) -> {
            if (name.getText() != null && !name.getText().toString().isBlank()) {
                if (fecha.getText() != null && !fecha.getText().toString().isBlank()) {
                    if (tipo != null && !tipo.isBlank()) {
                        insertarIngrediente(dialog);
                        Toast.makeText(context, "Ingrediente creado correctamente", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void insertarIngrediente(Dialog dialog) {
        String nombre = name.getText().toString();
        String fechaCad = fecha.getText().toString();
        String tipo = this.tipo;

        Task<QuerySnapshot> consulta = database.collection("usuarios").whereEqualTo("correo", usuario.getCorreo()).get();
        consulta.addOnSuccessListener(documentSnapshots -> {
            if (!documentSnapshots.isEmpty()) {
                DocumentSnapshot documentSnapshot = documentSnapshots.getDocuments().get(0);
                String id = documentSnapshot.getId();
                DocumentReference userRef = database.collection("usuarios").document(id);

                Ingrediente ingrediente = new Ingrediente(nombre, fechaCad, tipo, id);
                List<Ingrediente> ingredientesUsuario = usuario.getIngredientes();

                if (ingredientesUsuario == null) ingredientesUsuario = new ArrayList<>();

                ingredientesUsuario.add(ingrediente);
                usuario.setIngredientes(ingredientesUsuario);

                userRef.update("ingredientes", usuario.getIngredientes());
                coleccion.add(ingrediente);
                dialog.dismiss();
            }
        });
    }

    /**
     * Método que cierra el dialog al pulsar sobre el botón de cerrar.
     * @param dialog dialog del cardView.
     */
    private void cerrarCardView(@NonNull Dialog dialog) {
        ImageView imageView = dialog.findViewById(R.id.cerrar);
        imageView.setOnClickListener((View) -> dialog.dismiss());
    }
}
