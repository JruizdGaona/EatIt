package com.example.eatit.fragments.recetas;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.example.eatit.R;
import com.example.eatit.activities.LoginActivity;
import com.example.eatit.activities.RegistroActivity;

public class CardAddRecetas {

    // Declaramos las Variables
    Context context;

    /**
     * Constructor de la Clase.
     * @param context Contexto del CardView nuevo.
     */
    public CardAddRecetas (Context context) {this.context = context;}

    /**
     * Método que ejecuta las operaciones del CardView.
     */
    public void operacionesCardView() {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.card_add_recetas);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        cerrarTeclado(dialog);
        cerrarCardView(dialog);
        dialog.show();
    }

    /**
     * Método usado para cerrar el teclado al pulsar sobre otro lado de la pantalla.
     * @param dialog Dialog en el que mostramos el cardView.
     */
    private void cerrarTeclado(@NonNull Dialog dialog) {
        CardView cardView = dialog.findViewById(R.id.cradView_add_recetas);
        ScrollView scrollView = cardView.findViewById(R.id.scrollView);

        cardView.setOnTouchListener((view, motionEvent) -> {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            return false;
        });

        scrollView.setOnTouchListener((view, motionEvent) -> {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            return false;
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