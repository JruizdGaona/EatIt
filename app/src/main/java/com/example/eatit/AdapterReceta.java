package com.example.eatit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class AdapterReceta extends RecyclerView.Adapter<AdapterReceta.MyViewHolder> {

    private List<Receta> recetas;
    private Context context;
    private LayoutInflater mInflater;
    TextView nombre;

    public AdapterReceta(List<Receta> re, Context context){
        this.recetas = re;
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.from(parent.getContext()).inflate(R.layout.list_receta,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.cv.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition));
        holder.bindData(recetas.get(position));
    }

    @Override
    public int getItemCount() {
        return recetas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        MaterialCardView cv;

        public MyViewHolder(View view){
            super(view);
            nombre = view.findViewById(R.id.ilm_nombre);
            cv = view.findViewById(R.id.layout_list_receta);
        }

        void bindData(final Receta item) {
            nombre.setText((item.getNombre()));
        }
    }
}
