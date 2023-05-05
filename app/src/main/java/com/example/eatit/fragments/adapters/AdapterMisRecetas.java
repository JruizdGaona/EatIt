package com.example.eatit.fragments.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eatit.R;
import com.example.eatit.entities.Ingrediente;
import com.example.eatit.entities.Receta;
import com.example.eatit.activities.ActivityRecetas;
import com.example.eatit.entities.Usuario;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

/**
 * @author Javier Ruiz de Gaona Tre.
 */
public class AdapterMisRecetas extends RecyclerView.Adapter<AdapterMisRecetas.MyViewHolder> {

    // Declaración de variables.
    private final List<Receta> recetas;
    private final Context context;
    TextView nombre;
    private String email;
    private ShapeableImageView imagen;
    FirebaseFirestore database;
    CollectionReference coleccion;

    /**
     * Constructor del Adapter de Recetas.
     * @param re Lista de recetas a añadir en el Fragment.
     * @param context Contexto en el que usamos el Adapter.
     */
    public AdapterMisRecetas(List<Receta> re, Context context, String correo){
        this.recetas = re;
        this.context = context;
        this.email = correo;
        database = FirebaseFirestore.getInstance();
        coleccion = database.collection("recetas");
    }

    /**
     * Método que crea el ViewHolder para meter ahí las CardViews con la información de las Recetas.
     * @param parent View desde el que se va a crear el ViewHolder.
     * @param viewType Referencia que permite definir diferentes tipos de vistas.
     *
     * @return ViewHolder creado.
     */
    @NonNull
    @Override
    public AdapterMisRecetas.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_mis_recetas,parent,false);
        return new AdapterMisRecetas.MyViewHolder(itemView);
    }

    /**
     * Método que introduce una Animación al cargar nuevas Recetas.
     * @param holder ViewHolder que contiene todos los CardViews y se les pone la animación.
     * @param position Posicion de la nueva Receta.
     */
    @Override
    public void onBindViewHolder(AdapterMisRecetas.MyViewHolder holder, int position) {
        holder.cv.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition));
        holder.bindData(recetas.get(position));

        holder.cv.setOnClickListener((view) -> {
            Intent intent = new Intent(context, ActivityRecetas.class);
            intent.putExtra("receta", recetas.get(position));
            intent.putExtra("email", email);
            context.startActivity(intent);
        });

        holder.cv.setOnLongClickListener(view -> {
            Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.card_op_recetas);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            eliminarReceta(dialog, recetas.get(position));
            cerrarCardView(dialog);
            dialog.show();
            Toast.makeText(context, "Pulsación Laraga", Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void eliminarReceta(Dialog dialog, Receta receta) {
        AppCompatButton eliminar = dialog.findViewById(R.id.btn_delete);

        eliminar.setOnClickListener((view) -> {
            Task<QuerySnapshot> consulta = coleccion.whereEqualTo("nombre", receta.getNombre()).get();

            consulta.addOnSuccessListener(documentSnapshots -> {
                if (!documentSnapshots.isEmpty()) {
                    for (DocumentSnapshot documentSnapshot : documentSnapshots.getDocuments()) {
                        if (documentSnapshot.getString("nombre").equalsIgnoreCase(receta.getNombre())) {
                            coleccion.document(documentSnapshot.getId()).delete().addOnSuccessListener(aVoid -> {
                                eliminarRecetaDelUsuario(receta);
                                Toast.makeText(context, "Receta eliminada correctamente", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }).addOnFailureListener(e -> {
                                Toast.makeText(context, "Error al borrar la receta", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            });
                            return;
                        }
                    }
                }
                Toast.makeText(context, "Error al borrar la receta", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }).addOnFailureListener(e -> {
                Toast.makeText(context, "Error al borrar la receta", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            });
        });
    }

    private void eliminarRecetaDelUsuario(Receta receta) {
        Task<QuerySnapshot> consultaUsuario = database.collection("usuarios").whereEqualTo("correo", email).get();
        consultaUsuario.addOnSuccessListener(documentSnapshotsUsuario -> {
            if (!documentSnapshotsUsuario.isEmpty()) {
                DocumentSnapshot documentSnapshotUsuario = documentSnapshotsUsuario.getDocuments().get(0);
                Usuario usuario = documentSnapshotUsuario.toObject(Usuario.class);
                List<Receta> recetasUsuario = usuario.getRecetasCreadas();

                recetasUsuario.removeIf(i -> i.getNombre().equalsIgnoreCase(receta.getNombre()));
                String usuarioId = documentSnapshotUsuario.getId();

                database.collection("usuarios").document(usuarioId).update("recetasCreadas", recetasUsuario);
            }
        });
    }

    private void cerrarCardView(Dialog dialog) {
        ImageView imageView = dialog.findViewById(R.id.cerrar_op);
        imageView.setOnClickListener((View) -> dialog.dismiss());
    }

    /**
     * Método que cuenta los elementos que hay en la lista.
     *
     * @return Número de elementos de la lista.
     */
    @Override
    public int getItemCount() {
        return recetas.size();
    }

    /**
     * Método que rellena las CardViews con los datos de las recetas.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder{
        MaterialCardView cv;

        public MyViewHolder(View view){
            super(view);
            nombre = view.findViewById(R.id.mis_recetas_nombre);
            imagen = view.findViewById(R.id.ilm_foto);
            cv = view.findViewById(R.id.layout_list_mis_recetas);
        }

        void bindData(final Receta item) {
            if (item.getUri() != null && !item.getUri().isEmpty()) {
                Glide.with(context)
                        .load(item.getUri())
                        .centerCrop()
                        .into(imagen);
            }
            nombre.setText((item.getNombre()));
        }
    }
}
