package com.example.eatit.fragments.ingredientes;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.eatit.R;
import com.example.eatit.entities.Ingrediente;

public class CardVerMisIngredientes {

    Context context;
    TextView cantidad, nombre, fecha_caducidad, kcal, tipo;
    String name, fecha, tipo_ingredinte;
    float cant, calorias;

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
     * Método que cierra el dialog al pulsar sobre el botón de cerrar.
     * @param dialog dialog del cardView.
     */
    private void cerrarCardView(@NonNull Dialog dialog) {
        ImageView imageView = dialog.findViewById(R.id.cerrar);
        imageView.setOnClickListener((View) -> dialog.dismiss());
    }

    private void cargarDatos(@NonNull Ingrediente ingrediente, @NonNull Dialog dialog) {
        nombre = dialog.findViewById(R.id.text_ver_ingrediente);
        fecha_caducidad = dialog.findViewById(R.id.fecha_ingredientes);
        cantidad = dialog.findViewById(R.id.cantidad);
        kcal = dialog.findViewById(R.id.kcal_ejemplo);
        tipo = dialog.findViewById(R.id.tipo_ejemplo);

        name = ingrediente.getNombre();
        fecha = ingrediente.getFechaCaducidad();
        tipo_ingredinte = ingrediente.getTipo();
        cant = ingrediente.getCantidad();
        calorias = ingrediente.getKcal();

        nombre.setText(name);
        cantidad.setText(String.valueOf(cant));
        tipo.setText(tipo_ingredinte);

        if (fecha == null || fecha.isBlank()) fecha_caducidad.setText("Sin especificar");
        else fecha_caducidad.setText(fecha);

        if (calorias == 0.0) kcal.setText("Sin especificar");
        else kcal.setText(String.valueOf(calorias));
    }
}
