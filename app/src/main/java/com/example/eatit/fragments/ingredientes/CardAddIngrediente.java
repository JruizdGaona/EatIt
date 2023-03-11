package com.example.eatit.fragments.ingredientes;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.example.eatit.R;
import com.example.eatit.entities.Ingrediente;
import com.example.eatit.entities.Receta;
import com.google.android.material.textfield.TextInputEditText;

/**
 * @author Javier Ruiz de Gaona Tre.
 */
public class CardAddIngrediente {

    // Declaramos las Variables
    Context context;
    int status;
    TextView nombre;
    TextInputEditText name, cantidad, fecha;
    Ingrediente ingrediente;

    /**
     * Constructor de la Clase.
     * @param context Contexto del CardView nuevo.
     */
    public CardAddIngrediente (Context context) {this.context = context;}

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
        cerrarCardView(dialog);
        dialog.show();
    }

    private void inicializarVariables(@NonNull Dialog dialog) {
        nombre = dialog.findViewById(R.id.text_add_ingrediente);
        name = dialog.findViewById(R.id.login_textInput_nombreIngrediente);
        cantidad = dialog.findViewById(R.id.login_textInput_cantidadIngrediente);
        fecha = dialog.findViewById(R.id.login_textInput_caducidadIngrediente);

        if (status == 1) {
            nombre.setText(ingrediente.getNombre());
            name.setText(ingrediente.getNombre());
            cantidad.setText(String.valueOf(ingrediente.getCantidad()));
            fecha.setText(ingrediente.getFechaCaducidad());
        }
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
     * Método que cierra el dialog al pulsar sobre el botón de cerrar.
     * @param dialog dialog del cardView.
     */
    private void cerrarCardView(@NonNull Dialog dialog) {
        ImageView imageView = dialog.findViewById(R.id.cerrar);
        imageView.setOnClickListener((View) -> dialog.dismiss());
    }
}
