package com.example.eatit.fragments.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eatit.R;
import com.example.eatit.entities.Ingrediente;
import com.example.eatit.entities.Usuario;
import com.example.eatit.fragments.ingredientes.listar.CardVerMisIngredientes;
import com.google.android.material.card.MaterialCardView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Javier Ruiz de Gaona Tre.
 */
public class AdapterIngrediente extends RecyclerView.Adapter<AdapterIngrediente.MyViewHolder> {

    // Declaración de Variables
    private final List<Ingrediente> ingredientes;
    private final Context context;
    TextView nombre;
    Usuario usuario;
    ImageView foto;

    /**
     * Constructor del Adapter de Ingredientes.
     * @param re Lista de ingredientes a añadir en el Fragment.
     * @param context Contexto en el que usamos el Adapter.
     */
    public AdapterIngrediente(List<Ingrediente> re, Context context, Usuario usuario){
        this.ingredientes = re;
        this.context = context;
        this.usuario = usuario;
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
            CardVerMisIngredientes misIngredientes = new CardVerMisIngredientes(context, usuario);
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
            foto = view.findViewById(R.id.ilm_foto);
            cv = view.findViewById(R.id.layout_list_ingredientes);
        }

        void bindData(@NonNull final Ingrediente item) {
            nombre.setText((item.getNombre()));
            String fechaCad = item.getFechaCaducidad();
            boolean desactivado = item.isDesactivado();

            String tipo = item.getTipo();
            ponerImagen(tipo);

            if (desactivado) {
                cv.findViewById(R.id.disabled_ic_card).setVisibility(View.VISIBLE);
            }

            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date fecha = formato.parse(fechaCad);

                if (fecha.before(new Date())) {
                    cv.setStrokeColor(ContextCompat.getColor(context, R.color.caducado));
                    cv.findViewById(R.id.caducado_ic_card).setVisibility(View.VISIBLE);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void ponerImagen(String tipo) {
        Drawable darwable;

        switch (tipo) {
            case "Otro":
                darwable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.otro, null);
                break;
            case "Carne":
                darwable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.carne_uno, null);
                break;
            case "Pescado":
                darwable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.pescado, null);
                break;
            case "Verdura":
                darwable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.verdura, null);
                break;
            case "Fruta":
                darwable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.fruta, null);
                break;
            case "Lácteos":
                darwable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.lacteo, null);
                break;
            case "Cereales":
                darwable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.cereales, null);
                break;
            default:
                darwable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.sinespec, null);
                break;
        }

        foto.setImageDrawable(darwable);
    }
}
