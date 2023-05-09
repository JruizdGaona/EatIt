package com.example.eatit.fragments.recetas.actualizar;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.eatit.R;
import com.example.eatit.entities.Receta;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Arrays;

public class FragmentActualizarReceta extends Fragment {
    private String[] dificultadReceta = {"Fácil", "Media", "Difícil", "Sólo para expertos"};
    private AppCompatButton boton_siguiente;
    private Receta receta;
    private Spinner spinnerDificultad;
    TextInputEditText tiempoET, comensalesET, nombreET;
    private String dificultad;
    String email, recetaOldName;

    public FragmentActualizarReceta(String email, Receta receta) {
        this.email = email;
        this.receta = receta;
        this.recetaOldName = receta.getNombre();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_crear_receta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inicializarVariables(view);

        view.setOnTouchListener((view1, motionEvent) -> ocultar());
        clickSiguienteBtn();
    }

    private void inicializarVariables(View view) {
        boton_siguiente = view.findViewById(R.id.btn_next);
        spinnerDificultad = view.findViewById(R.id.spinner_dificultad);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, dificultadReceta);
        spinnerDificultad.setAdapter(adapter);
        tiempoET = view.findViewById(R.id.textInput_tiempo);
        comensalesET = view.findViewById(R.id.textInput_comensales);
        nombreET = view.findViewById(R.id.textInput_nombre);

        tiempoET.setText(receta.getDuracion());
        comensalesET.setText(String.valueOf(receta.getRaciones()));
        nombreET.setText(receta.getNombre());

        int index = Arrays.asList(dificultadReceta).indexOf(receta.getDificultad());
        if (index == -1) {
            index = 0;
        }
        spinnerDificultad.setSelection(index);

        spinnerDificultad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dificultad = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void clickSiguienteBtn() {
        boton_siguiente.setOnClickListener((View) -> {
            receta.setDuracion(tiempoET.getText().toString());
            receta.setRaciones(Integer.parseInt(comensalesET.getText().toString()));
            receta.setDificultad(dificultad);
            receta.setNombre(nombreET.getText().toString());

            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.from_right, R.anim.to_left);
            fragmentTransaction.replace(R.id.frame_info, new FragmentEditarIngReceta(receta, email, recetaOldName));
            fragmentTransaction.commit();
        });
    }

    /**
     * Método usado para cerrar el teclado al pulsar sobre otro lado de la pantalla.
     *
     * @return - True, si la vista es distinta de null, False si la View es null.
     */
    public boolean ocultar() {
        View view = this.requireActivity().getCurrentFocus();

        if (view != null) {
            InputMethodManager input = (InputMethodManager) (requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE));
            input.hideSoftInputFromWindow(view.getWindowToken(), 0);
            return true;
        } else {
            return false;
        }
    }
}
