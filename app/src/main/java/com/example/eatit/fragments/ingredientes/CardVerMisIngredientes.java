package com.example.eatit.fragments.ingredientes;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import com.example.eatit.R;
import com.example.eatit.entities.Ingrediente;
import com.example.eatit.entities.Usuario;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

/**
 * @author Javier Ruiz de Gaona Tre.
 */
public class CardVerMisIngredientes {

    // Declaramos las Variables.
    Context context;
    TextView nombre, fecha_caducidad, tipo;
    String name, fecha, tipo_ingredinte;
    AppCompatButton eliminar, editar;
    FirebaseFirestore database;
    CollectionReference coleccion;
    Usuario usuario;

    /**
     * Constructor de la Clase.
     * @param context Contexto del CardView nuevo.
     */
    public CardVerMisIngredientes (Context context, Usuario usuario) {
        this.context = context;
        database = FirebaseFirestore.getInstance();
        coleccion = database.collection("ingredientes");
        this.usuario = usuario;
    }

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
        tipo = dialog.findViewById(R.id.tipo_ejemplo);
        editar = dialog.findViewById(R.id.btn_editar_ingrediente);
        eliminar = dialog.findViewById(R.id.btn_borrar_ingrediente);

        name = ingrediente.getNombre();
        fecha = ingrediente.getFechaCaducidad();
        tipo_ingredinte = ingrediente.getTipo();

        nombre.setText(name);
        fecha_caducidad.setText(fecha);
        tipo.setText(tipo_ingredinte);

        editarIngrediente(dialog, ingrediente);
        eliminarIngrediente(dialog, ingrediente);
    }

    private void eliminarIngrediente(Dialog dialog, Ingrediente ingrediente) {
        eliminar.setOnClickListener((View) -> {
            Task<QuerySnapshot> consulta = coleccion.whereEqualTo("nombre", ingrediente.getNombre()).get();

            consulta.addOnSuccessListener(documentSnapshots -> {
                if (!documentSnapshots.isEmpty()) {
                    for (DocumentSnapshot documentSnapshot : documentSnapshots.getDocuments()) {
                        if (documentSnapshot.getString("nombre").equalsIgnoreCase(ingrediente.getNombre())) {
                            coleccion.document(documentSnapshot.getId()).delete().addOnSuccessListener(aVoid -> {
                                eliminarIngredienteDelUsuario(ingrediente);
                                Toast.makeText(context, "Ingrediente eliminado correctamente", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }).addOnFailureListener(e -> {
                                Toast.makeText(context, "Error al borrar el ingrediente", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            });
                            return;
                        }
                    }
                }
                Toast.makeText(context, "Error al borrar el ingrediente", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }).addOnFailureListener(e -> {
                Toast.makeText(context, "Error al borrar el ingrediente", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            });
        });
    }

    private void eliminarIngredienteDelUsuario(Ingrediente ingrediente) {
        Task<QuerySnapshot> consultaUsuario = database.collection("usuarios").whereEqualTo("correo", usuario.getCorreo()).get();
        consultaUsuario.addOnSuccessListener(documentSnapshotsUsuario -> {
            if (!documentSnapshotsUsuario.isEmpty()) {
                List<Ingrediente> ingredientesUsuario = usuario.getIngredientes();

                ingredientesUsuario.removeIf(i -> i.getNombre().equalsIgnoreCase(ingrediente.getNombre()));
                String usuarioId = documentSnapshotsUsuario.getDocuments().get(0).getId();

                database.collection("usuarios").document(usuarioId).update("ingredientes", ingredientesUsuario);
            }
        });
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
