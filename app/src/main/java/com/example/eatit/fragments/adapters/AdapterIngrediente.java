package com.example.eatit.fragments.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.eatit.R;
import com.example.eatit.entities.Ingrediente;
import com.example.eatit.fragments.ingredientes.CardVerMisIngredientes;
import com.google.android.material.card.MaterialCardView;
import java.util.List;

/**
 * @author Javier Ruiz de Gaona Tre.
 */
public class AdapterIngrediente extends RecyclerView.Adapter<AdapterIngrediente.MyViewHolder> {

    // Declaración de Variables
    private final List<Ingrediente> ingredientes;
    private final Context context;
    TextView nombre;

    /**
     * Constructor del Adapter de Ingredientes.
     * @param re Lista de ingredientes a añadir en el Fragment.
     * @param context Contexto en el que usamos el Adapter.
     */
    public AdapterIngrediente(List<Ingrediente> re, Context context){
        this.ingredientes = re;
        this.context = context;
    }

    /**
     * Método que crea el ViewHolder para meter ahí las CardViews con la información de los Ingredientes.
     * @param parent View desde el que se va a crear el ViewHolder.
     * @param viewType Referencia que permite definir diferentes tipos de vistas.
     *
     * @return ViewHolder creado.
     */
    @NonNull
    @Override
    public AdapterIngrediente.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_ingredientes,parent,false);
        return new AdapterIngrediente.MyViewHolder(itemView);
    }

    /**
     * Método que introduce una Animación al cargar nuevos Ingredientes.
     * @param holder ViewHolder que contiene todos los CardViews y se les pone la animación.
     * @param position Posicion del nuevo Ingrediente.
     */
    @Override
    public void onBindViewHolder(@NonNull AdapterIngrediente.MyViewHolder holder, int position) {
        holder.cv.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition));
        holder.bindData(ingredientes.get(position));

        holder.cv.setOnClickListener((view)-> {
            CardVerMisIngredientes misIngredientes = new CardVerMisIngredientes(context);

            misIngredientes.operacionesCardView(ingredientes.get(position));
        });
    }

    /**
     * Método que cuenta los elementos que hay en la lista.
     *
     * @return Número de elementos de la lista.
     */
    @Override
    public int getItemCount() {
        return ingredientes.size();
    }

    /**
     * Método que rellena las CardViews con los datos de los ingredientes.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder{
        MaterialCardView cv;

        public MyViewHolder(View view){
            super(view);
            nombre = view.findViewById(R.id.ingrediente_nombre);
            cv = view.findViewById(R.id.layout_list_ingredientes);
        }

        void bindData(@NonNull final Ingrediente item) {
            nombre.setText((item.getNombre()));
        }
    }
}
