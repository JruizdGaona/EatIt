package com.example.eatit.fragments.ingredientes;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.InputType;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
    String tipo;
    Ingrediente ingrediente;
    Spinner spinnerTipo;
    AppCompatButton guardar;
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
    public CardAddIngrediente (Context context, int status, Ingrediente i) {
        this.context = context;
        this.status = status;
        ingrediente = i;
    }

    /**
     * Método que ejecuta las operaciones del CardView.
     */
    public void operacionesCardView() {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.card_add_ingredientes);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        inicializarVariables(dialog);
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
        nombre = dialog.findViewById(R.id.text_add_ingrediente);
        name = dialog.findViewById(R.id.login_textInput_nombreIngrediente);
        fecha = dialog.findViewById(R.id.login_textInput_caducidadIngrediente);
        fecha.setInputType(InputType.TYPE_NULL);
        spinnerTipo = dialog.findViewById(R.id.login_spinner_tipo_ingrediente);

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
        }
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
