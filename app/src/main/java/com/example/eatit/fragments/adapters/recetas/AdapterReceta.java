package com.example.eatit.fragments.adapters.recetas;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eatit.R;
import com.example.eatit.entities.Receta;
import com.example.eatit.activities.ActivityRecetas;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

/**
 * @author Javier Ruiz de Gaona Tre.
 */
public class AdapterReceta extends RecyclerView.Adapter<AdapterReceta.MyViewHolder> {

    // Declaración de variables.
    private List<Receta> recetas;
    String email;
    private final Context context;

    /**
     * Constructor del Adapter de Recetas.
     * @param re Lista de recetas a añadir en el Fragment.
     * @param context Contexto en el que usamos el Adapter.
     */
    public AdapterReceta(List<Receta> re, Context context, String email){
        this.recetas = re;
        this.context = context;
        this.email = email;
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
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_receta,parent,false);
        return new MyViewHolder(itemView);
    }

    /**
     * Método que introduce una Animación al cargar nuevas Recetas.
     * @param holder ViewHolder que contiene todos los CardViews y se les pone la animación.
     * @param position Posicion de la nueva Receta.
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.cv.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition));
        holder.bindData(recetas.get(position));
        holder.ingredientes1.setVisibility(android.view.View.INVISIBLE);
        holder.ingredientes2.setVisibility(android.view.View.INVISIBLE);
        holder.btn_detalle.setVisibility(android.view.View.INVISIBLE);
        holder.expanded = false;
        ViewGroup.LayoutParams params = holder.cv.getLayoutParams();
        params.height = 615; // altura en píxeles
        holder.cv.setLayoutParams(params);

        holder.cv.setOnClickListener((View) -> {
            if (holder.expanded) {
                holder.ingredientes1.setVisibility(android.view.View.INVISIBLE);
                holder.ingredientes2.setVisibility(android.view.View.INVISIBLE);
                holder.btn_detalle.setVisibility(android.view.View.INVISIBLE);
                holder.expanded = false;
                ViewGroup.LayoutParams params1 = holder.cv.getLayoutParams();
                params1.height = 615; // altura en píxeles
                holder.cv.setLayoutParams(params1);
            } else {
                holder.ingredientes1.setVisibility(android.view.View.VISIBLE);
                holder.ingredientes2.setVisibility(android.view.View.VISIBLE);
                holder.btn_detalle.setVisibility(android.view.View.VISIBLE);
                holder.expanded = true;
                ViewGroup.LayoutParams params2 = holder.cv.getLayoutParams();
                params2.height = 900; // altura en píxeles
                holder.cv.setLayoutParams(params2);
            }

            if (holder.expanded) {
                holder.btn_detalle.setOnClickListener((view) -> {
                    Intent intent = new Intent(context, ActivityRecetas.class);
                    intent.putExtra("receta", recetas.get(position));
                    intent.putExtra("email", email);
                    context.startActivity(intent);
                });
            }
        });
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
        TextView nombre, dificultad, duracion, ingredientes1, ingredientes2;
        ShapeableImageView imagen;
        AppCompatButton btn_detalle;
        boolean expanded;

        public MyViewHolder(View view){
            super(view);
            btn_detalle = view.findViewById(R.id.btn_cocinar);
            ingredientes1 = view.findViewById(R.id.receta_ing1);
            ingredientes2 = view.findViewById(R.id.receta_ing2);
            nombre = view.findViewById(R.id.receta_nombre);
            duracion = view.findViewById(R.id.receta_duracion);
            dificultad = view.findViewById(R.id.receta_dificultad);
            imagen = view.findViewById(R.id.img_receta);
            cv = view.findViewById(R.id.layout_list_receta);
        }

        void bindData(@NonNull final Receta item) {
            if (item.getUri() != null && !item.getUri().isEmpty()) {
                Glide.with(context)
                        .load(item.getUri())
                        .centerCrop()
                        .into(imagen);
            }
            nombre.setText((item.getNombre()));
            duracion.setText(String.valueOf(item.getDuracion()).concat(" h"));
            dificultad.setText(item.getDificultad());
        }
    }
}