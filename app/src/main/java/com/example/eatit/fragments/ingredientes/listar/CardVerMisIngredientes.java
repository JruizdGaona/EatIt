package com.example.eatit.fragments.ingredientes.listar;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.example.eatit.R;
import com.example.eatit.entities.Ingrediente;
import com.example.eatit.entities.Usuario;
import com.example.eatit.fragments.ingredientes.crear.CardAddIngrediente;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Javier Ruiz de Gaona Tre.
 */
public class CardVerMisIngredientes {

    // Declaramos las Variables.
    Context context;
    TextView nombre, fecha_caducidad, tipo, warn_caducado, warn_deshabilitado;
    ImageView warn, warnDisabled;
    String name, fecha, tipo_ingredinte;
    AppCompatButton eliminar, editar, habilitar;
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
        MaterialCardView cardView = dialog.findViewById(R.id.cradView_ver_ingredientes);
        warn_caducado = dialog.findViewById(R.id.caducado_text);
        warnDisabled = dialog.findViewById(R.id.deshabilitado_ic);
        warn_deshabilitado = dialog.findViewById(R.id.deshabilitado_text);
        warn = dialog.findViewById(R.id.caducado_ic);

        if (comprobarFecha(ingrediente)) {
            cardView.setStrokeColor(ContextCompat.getColor(context, R.color.caducado));
            warn_caducado.setVisibility(View.VISIBLE);
            warn.setVisibility(View.VISIBLE);
        }

        if (ingrediente.isDesactivado()) {
            editar = dialog.findViewById(R.id.btn_editar_ingrediente);
            habilitar = dialog.findViewById(R.id.btn_habilitar_ingrediente);
            habilitar.setVisibility(View.VISIBLE);
            editar.setVisibility(View.INVISIBLE);
            warn_deshabilitado.setVisibility(View.VISIBLE);
            warnDisabled.setVisibility(View.VISIBLE);

            habilitarIngrediente(ingrediente);
        }

        cargarDatos(ingrediente, dialog);
        cerrarCardView(dialog);
        dialog.show();
    }

    private void habilitarIngrediente(Ingrediente ingrediente) {
        habilitar.setOnClickListener((View) -> {
            Task<QuerySnapshot> obtenerIngrediente = database.collection("ingredientes").whereEqualTo("nombre", ingrediente.getNombre()).get();

            obtenerIngrediente.addOnSuccessListener(ingredienteSnapshot -> {
                if (!ingredienteSnapshot.isEmpty()) {
                    DocumentSnapshot documentSnapshotIngrediente = ingredienteSnapshot.getDocuments().get(0);
                    String idIngrediente = documentSnapshotIngrediente.getId();
                    DocumentReference ingredienteRef = database.collection("ingredientes").document(idIngrediente);

                    Ingrediente ing = documentSnapshotIngrediente.toObject(Ingrediente.class);
                    ing.setDesactivado(false);

                    ingredienteRef.update("desactivado", ing.isDesactivado());
                }
            });

            Toast.makeText(context, "Ingrediente habilitado", Toast.LENGTH_SHORT).show();
            habilitar.setVisibility(android.view.View.INVISIBLE);
            warn_deshabilitado.setVisibility(View.INVISIBLE);
            warnDisabled.setVisibility(View.INVISIBLE);
            editar.setVisibility(android.view.View.VISIBLE);
        });
    }

    private boolean comprobarFecha(Ingrediente ingrediente) {
        String fechaCad = ingrediente.getFechaCaducidad();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date fecha = formato.parse(fechaCad);

            return fecha.before(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
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

    /**
     * Método que elimina un ingrediente de la base de datos.
     * @param dialog Dialog que contiene el CardView.
     * @param ingrediente Ingrediente que quyeremos borrar.
     */
    private void eliminarIngrediente(Dialog dialog, Ingrediente ingrediente) {
        eliminar.setOnClickListener((View) -> {
            Task<QuerySnapshot> consultaUsuario = database.collection("usuarios").whereEqualTo("correo", usuario.getCorreo()).get();
            consultaUsuario.addOnSuccessListener(documentSnapshotsUsuario -> {
                if (!documentSnapshotsUsuario.isEmpty()) {
                    String usuarioId = documentSnapshotsUsuario.getDocuments().get(0).getId();

                    Task<QuerySnapshot> consulta = coleccion.whereEqualTo("nombre", ingrediente.getNombre()).whereEqualTo("usuarioId", usuarioId).get();

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
                }
            });
        });
    }

    /**
     * Método que al eliminar un ingrediente, lo elimina del usuario asociado a él.
     * @param ingrediente Ingrediente a borrar del Usuario.
     */
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
            CardAddIngrediente addIngrediente = new CardAddIngrediente(dialog.getContext(), 1, ingrediente, usuario);
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
