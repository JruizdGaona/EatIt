package com.example.eatit;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.TextView;

public class LoadingDialog {
    Context context;
    Dialog dialog;

    public LoadingDialog(Context context) {
        this.context = context;
    }

    public void showDialog(String texto) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView text = dialog.findViewById(R.id.textViewDialog);
        text.setText(texto);

        dialog.create();
        dialog.show();
    }

    public void closeDialog() {
        dialog.dismiss();
    }
}
