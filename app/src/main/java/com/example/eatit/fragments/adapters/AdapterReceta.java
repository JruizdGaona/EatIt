package com.example.eatit.fragments.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.eatit.R;
import com.example.eatit.entities.Receta;
import com.example.eatit.fragments.ActivityRecetas;
import com.google.android.material.card.MaterialCardView;
import java.util.List;

/**
 * @author Javier Ruiz de Gaona Tre.
 */
public class AdapterReceta extends RecyclerView.Adapter<AdapterReceta.MyViewHolder> {

    // Declaración de variables.
    private List<Receta> recetas;
    private final Context context;
    TextView nombre, dificultad, duracion;

    /**
     * Método que cambia las recetas a mostrar por el adapter según lo que busque el usuario.
     * @param filtros Lista de recetas filtrada por el usuario.
     */
    public void setRecetasFiltradas(List<Receta> filtros) {
        this.recetas = filtros;
        notifyDataSetChanged();
    }

    /**
     * Constructor del Adapter de Recetas.
     * @param re Lista de recetas a añadir en el Fragment.
     * @param context Contexto en el que usamos el Adapter.
     */
    public AdapterReceta(List<Receta> re, Context context){
        this.recetas = re;
        this.context = context;
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

        holder.cv.setOnClickListener((View) -> {
            Intent intent = new Intent(context, ActivityRecetas.class);
            intent.putExtra("receta", recetas.get(position));
            context.startActivity(intent);
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

        public MyViewHolder(View view){
            super(view);
            nombre = view.findViewById(R.id.receta_nombre);
            duracion = view.findViewById(R.id.receta_duracion);
            dificultad = view.findViewById(R.id.receta_dificultad);
            cv = view.findViewById(R.id.layout_list_receta);
        }

        void bindData(@NonNull final Receta item) {
            nombre.setText((item.getNombre()));
            duracion.setText(String.valueOf(item.getDuracion()).concat(" h"));
            dificultad.setText(item.getDificultad());
        }
    }
}