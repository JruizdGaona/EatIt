package com.example.eatit.fragments.recetas.listar;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.eatit.R;
import com.example.eatit.entities.Receta;

public class FragmentRecetas extends Fragment {
    private Receta receta;
    private AppCompatTextView dificultad, tiempo, comensales, ingredientes;
    private AppCompatButton comenzarReceta;
    private String email;
    private TextToSpeech tts;

    public FragmentRecetas(Receta receta, String email, TextToSpeech tts) {
        this.receta = receta;
        this.email = email;
        this.tts = tts;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_paso_uno_recetas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dificultad = view.findViewById(R.id.text_dificultad);
        tiempo = view.findViewById(R.id.text_tiempo);
        comensales = view.findViewById(R.id.text_comensales);
        ingredientes = view.findViewById(R.id.ingredientes_receta);
        comenzarReceta = view.findViewById(R.id.btn_start);
        if (receta != null) {
            dificultad.setText(receta.getDificultad());
            tiempo.setText(String.valueOf(receta.getDuracion()).concat("'"));
            comensales.setText(String.valueOf(receta.getRaciones()));
            ingredientes.setText(mostrarIngredientes());
        }

        start();
    }

    private void start() {
        comenzarReceta.setOnClickListener((view) -> {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.from_right, R.anim.to_left);
            fragmentTransaction.replace(R.id.frame_info, new FragmentPasos(receta, 0, email, tts));
            fragmentTransaction.commit();
        });
    }

    private String mostrarIngredientes() {
        String finalIng = "";

        if (receta.getIngredientes() != null) {
            for (String ing: receta.getIngredientes()) {
                ing = "- " + ing + "\n\n";
                finalIng = finalIng + ing;
            }

            return finalIng;
        }

        return "Sin Ingredientes";
    }
}
