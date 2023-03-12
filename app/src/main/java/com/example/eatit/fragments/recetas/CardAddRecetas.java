package com.example.eatit.fragments.recetas;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import com.example.eatit.R;
import com.example.eatit.entities.Receta;
import com.google.android.material.textfield.TextInputEditText;

/**
 * @author Javier Ruiz de Gaona Tre.
 */
public class CardAddRecetas {

    // Declaramos las Variables.
    Context context;
    int status;
    TextView nombre;
    TextInputEditText name, numPersonas, dificultad, tiempo, descripcion;
    Receta receta;

    /**
     * Constructor de la Clase.
     * @param context Contexto del CardView nuevo.
     */
    public CardAddRecetas (Context context) {this.context = context;}

    /**
     * Constructor de la Clase cuando venimos del Fragment de Ver Receta.
     * @param context Contexto del CardView nuevo.
     * @param status Estado para saber si venimos desde la pestaña de editar.
     * @param r Receta que estamos viendo.
     */
    public CardAddRecetas (Context context, int status, Receta r) {
        this.context = context;
        this.status = status;
        receta = r;
    }

    /**
     * Método que ejecuta las operaciones del CardView.
     */
    public void operacionesCardView() {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.card_add_recetas);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        inicializarVariables(dialog);
        cerrarTeclado(dialog);
        cerrarCardView(dialog);
        dialog.show();
    }

    /**
     * Método que inicializa las variables del CardView.
     * @param dialog Dialog que contiene el CardView.
     */
    private void inicializarVariables(@NonNull Dialog dialog) {
        nombre = dialog.findViewById(R.id.text_add_receta);
        name = dialog.findViewById(R.id.login_textInput_nombreReceta);
        numPersonas = dialog.findViewById(R.id.login_textInput_personasReceta);
        dificultad = dialog.findViewById(R.id.login_textInput_nivelReceta);
        tiempo = dialog.findViewById(R.id.login_textInput_duracionReceta);
        descripcion = dialog.findViewById(R.id.login_textInput_detalleReceta);

        if (status == 1) {
            nombre.setText(receta.getNombre());
            name.setText(receta.getNombre());
            tiempo.setText(String.valueOf(receta.getDuracion()));
            descripcion.setText(receta.getDescripcion());
        }
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
