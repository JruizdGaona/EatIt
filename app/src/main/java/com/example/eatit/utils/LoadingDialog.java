package com.example.eatit.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.TextView;

import com.example.eatit.R;

/**
 * @author Javier Ruiz de Gaona Tre.
 */
public class LoadingDialog {

    // Declaración de variables.
    Context context;
    Dialog dialog;

    /**
     * Constructor del loadingDialog personalizado.
     * @param context Contexto en el que se va a crear el LoadingDialog.
     */
    public LoadingDialog(Context context) {
        this.context = context;
    }

    /**
     * Método que muestra el Dialog en el contexto en el que se ha creado.
     * @param texto Texto que queremos que muestre el loadingDialog.
     */
    public void showDialog(String texto) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView text = dialog.findViewById(R.id.textViewDialog);
        text.setText(texto);

        dialog.create();
        dialog.show();
    }

    /**
     * Método que cierra el Dialog.
     */
    public void closeDialog() {
        dialog.dismiss();
    }
}
