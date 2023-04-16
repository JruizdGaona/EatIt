package com.example.eatit.fragments.ingredientes;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import com.example.eatit.R;
import com.example.eatit.entities.Ingrediente;

/**
 * @author Javier Ruiz de Gaona Tre.
 */
public class CardVerMisIngredientes {

    // Declaramos las Variables.
    Context context;
    TextView cantidad, nombre, fecha_caducidad, kcal, tipo;
    String name, fecha, tipo_ingredinte;
    float cant, calorias;
    AppCompatButton eliminar, editar;

    /**
     * Constructor de la Clase.
     * @param context Contexto del CardView nuevo.
     */
    public CardVerMisIngredientes (Context context) {this.context = context;}

    /**
     * Método que ejecuta las operaciones del CardView.
     */
    public void operacionesCardView(Ingrediente ingrediente) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.card_ver_ingredientes);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        cargarDatos(ingrediente, dialog);
        cerrarCardView(dialog);
        dialog.show();
    }

    /**
     * Método que varga los datos del Ingrediente seleccionadoa en el CardView.
     * @param ingrediente Ingrediente que queremos ver.
     * @param dialog Dialog que contiene el CardView.
     */
    private void cargarDatos(@NonNull Ingrediente ingrediente, @NonNull Dialog dialog) {
        nombre = dialog.findViewById(R.id.text_ver_ingrediente);
        fecha_caducidad = dialog.findViewById(R.id.fecha_ingredientes);
        cantidad = dialog.findViewById(R.id.cantidad);
        kcal = dialog.findViewById(R.id.kcal_ejemplo);
        tipo = dialog.findViewById(R.id.tipo_ejemplo);
        editar = dialog.findViewById(R.id.btn_editar_ingrediente);

        name = ingrediente.getNombre();
        fecha = ingrediente.getFechaCaducidad();
        tipo_ingredinte = ingrediente.getTipo();

        nombre.setText(name);
        cantidad.setText(String.valueOf(cant));
        tipo.setText(tipo_ingredinte);

        if (fecha == null || fecha.isBlank()) fecha_caducidad.setText("Sin especificar");
        else fecha_caducidad.setText(fecha);

        if (calorias == 0.0) kcal.setText("Sin especificar");
        else kcal.setText(String.valueOf(calorias));

        editarIngrediente(dialog, ingrediente);
    }

    /**
     * Método que se ejecuta al pulsar el botón de Editar en el Ingrediente, nos carga el CardView
     * de crear ingrediente para editarlo.
     * @param dialog Dialog que contiene el CardView.
     * @param ingrediente Ingrediente que queremos editar.
     */
    private void editarIngrediente(Dialog dialog, Ingrediente ingrediente) {
        editar.setOnClickListener((view) -> {
            CardAddIngrediente addIngrediente = new CardAddIngrediente(dialog.getContext(), 1, ingrediente);
            addIngrediente.operacionesCardView();

            new Handler().postDelayed(dialog::dismiss, 250);
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
