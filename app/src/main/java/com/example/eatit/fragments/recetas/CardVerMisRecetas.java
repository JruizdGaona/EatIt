package com.example.eatit.fragments.recetas;

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
import com.example.eatit.entities.Receta;

public class CardVerMisRecetas {

    // Declaramos las Variables
    Context context;
    TextView ingredientes, raciones, dificultad, tiempo, descripcion, name;
    String nombre, ing, dif, desc;
    int racion;
    float time;
    AppCompatButton eliminar, editar;


    /**
     * Constructor de la Clase.
     * @param context Contexto del CardView nuevo.
     */
    public CardVerMisRecetas (Context context) {this.context = context;}

    /**
     * Método que ejecuta las operaciones del CardView.
     */
    public void operacionesCardView(Receta receta) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.card_ver_mis_recetas);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        cargarDatos(receta, dialog);

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

    private void cargarDatos(@NonNull Receta receta, @NonNull Dialog dialog) {
        ingredientes = dialog.findViewById(R.id.ingredientes_receta);
        raciones = dialog.findViewById(R.id.raciones);
        dificultad = dialog.findViewById(R.id.dificultad);
        tiempo = dialog.findViewById(R.id.tiempo);
        descripcion = dialog.findViewById(R.id.desc);
        name = dialog.findViewById(R.id.text_nombre_receta);
        editar = dialog.findViewById(R.id.btn_editar_receta);

        nombre = receta.getNombre();
        dif = receta.getDificultad();
        desc = receta.getDescripcion();
        racion = receta.getKcal();
        ing = receta.getIngredientes();
        time = receta.getDuracion();


        name.setText(nombre);
        if (ing == null || ing.isBlank()) ingredientes.setText("Sin Ingredientes");
        else ingredientes.setText(ing);
        raciones.setText(racion + " presonas");
        dificultad.setText(dif);
        tiempo.setText(String.valueOf(time));
        if (desc == null || desc.isBlank()) descripcion.setText("Sin Descripcion");
        else descripcion.setText(ing);

        editarReceta(dialog, receta);
    }

    private void editarReceta(Dialog dialog, Receta r) {
        editar.setOnClickListener((view) -> {
            CardAddRecetas addReceta = new CardAddRecetas(dialog.getContext(), 1, r);
            addReceta.operacionesCardView();

            new Handler().postDelayed(dialog::dismiss, 250);
        });
    }
}
