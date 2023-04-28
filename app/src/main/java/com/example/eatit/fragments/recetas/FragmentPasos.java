package com.example.eatit.fragments.recetas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.eatit.R;
import com.example.eatit.entities.Receta;

public class FragmentPasos extends Fragment {

    private Receta receta;
    private AppCompatTextView pasos;
    private ImageView siguiente, anterior;

    public FragmentPasos(Receta receta) {
        this.receta = receta;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_elab_uno, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pasos = view.findViewById(R.id.pasos_receta);
        siguiente = view.findViewById(R.id.siguiente_paso);
        anterior = view.findViewById(R.id.anterior_paso);
        pasos.setText(receta.getDescripcion());

        siguiente.setOnClickListener((View) -> {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.from_right, R.anim.to_left);
            fragmentTransaction.replace(R.id.frame_info, new FragmentPasos(receta));
            fragmentTransaction.commit();
        });

        anterior.setOnClickListener((View) -> {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.from_left, R.anim.to_right);
            fragmentTransaction.replace(R.id.frame_info, new FragmentPasos(receta));
            fragmentTransaction.commit();
        });
    }
}
