package com.example.eatit.fragments.recetas.crear;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;
import java.util.Objects;

public class FragmentCrearReceta extends Fragment {

    private final String[] dificultadReceta = {"Fácil", "Media", "Difícil"};
    private AppCompatButton boton_siguiente;
    private final Receta receta;
    TextInputEditText tiempoET, comensalesET, nombreET;
    TextInputLayout tiempo, comensales, nombre;
    private String dificultad;
    String email;

    public FragmentCrearReceta(String email, Receta receta) {
        this.email = email;
        this.receta = receta;
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
        comprobarTiempo();
        comprobarNombre();
        comprobarComensales();
    }

    private void inicializarVariables(View view) {
        boton_siguiente = view.findViewById(R.id.btn_next);
        Spinner spinnerDificultad = view.findViewById(R.id.spinner_dificultad);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, dificultadReceta);
        spinnerDificultad.setAdapter(adapter);
        tiempoET = view.findViewById(R.id.textInput_tiempo);
        tiempo = view.findViewById(R.id.layoutTextInput_tiempo);
        comensalesET = view.findViewById(R.id.textInput_comensales);
        comensales = view.findViewById(R.id.layoutTextInput_comensales);
        nombreET = view.findViewById(R.id.textInput_nombre);
        nombre = view.findViewById(R.id.layoutTextInput_nombre);

        if (receta.getNombre() != null && receta.getDuracion() != null && receta.getRaciones() != 0) {
            tiempoET.setText(receta.getDuracion());
            nombreET.setText(receta.getNombre());
            comensalesET.setText(String.valueOf(receta.getRaciones()));

            int index = Arrays.asList(dificultadReceta).indexOf(receta.getDificultad());
            if (index == -1) {
                index = 0;
            }
            spinnerDificultad.setSelection(index);

            cambiarEstadoBoton(true);
        } else {
            cambiarEstadoBoton(false);
        }

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

    private void comprobarComensales() {
        comensalesET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cambiarEstadoBoton(!charSequence.toString().isEmpty());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty() && editable.toString().matches("^[1-9]\\d*")) {
                    if(Objects.requireNonNull(comensalesET.getText()).toString().isEmpty() || Objects.requireNonNull(tiempoET.getText()).toString().isEmpty() || nombreET.getText().toString().isEmpty()){
                        comensales.setError(null);
                        cambiarEstadoBoton(false);
                    } else {
                        comensales.setError(null);
                        cambiarEstadoBoton(true);
                    }
                }else {
                    comensales.setError("Solo caracteres numéricos");
                    cambiarEstadoBoton(false);
                }
            }
        });
    }

    private void comprobarNombre() {
        nombreET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cambiarEstadoBoton(!charSequence.toString().isEmpty());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty() && editable.toString().matches("[a-zA-ZáéíóúÁÉÍÓÚS\\s]{1,40}")) {
                    if(Objects.requireNonNull(nombreET.getText()).toString().isEmpty() || Objects.requireNonNull(tiempoET.getText()).toString().isEmpty() || comensalesET.getText().toString().isEmpty()){
                        nombre.setError(null);
                        cambiarEstadoBoton(false);
                    }
                    else {
                        nombre.setError(null);
                        cambiarEstadoBoton(true);
                    }
                }else {
                    nombre.setError("Sólo caracteres alfabéticos");
                    cambiarEstadoBoton(false);
                }

                if(editable.length() > 40) {
                    nombre.setError("Máximo caracteres alcanzado");
                }
            }
        });
    }

    private void comprobarTiempo() {
        tiempoET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cambiarEstadoBoton(!charSequence.toString().isEmpty());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().isEmpty() && editable.toString().matches("^[1-9]\\d*")) {
                    if(Objects.requireNonNull(tiempoET.getText()).toString().isEmpty() || Objects.requireNonNull(comensalesET.getText()).toString().isEmpty() || nombreET.getText().toString().isEmpty()){
                        tiempo.setError(null);
                        cambiarEstadoBoton(false);
                    }
                    else {
                        tiempo.setError(null);
                        cambiarEstadoBoton(true);
                    }
                }else {
                    tiempo.setError("Solo caracteres numéricos");
                    cambiarEstadoBoton(false);
                }
            }
        });
    }

    private void cambiarEstadoBoton(boolean estado) {
        this.boton_siguiente.setEnabled(estado);

        if (!this.boton_siguiente.isEnabled()) {
            this.boton_siguiente.setBackgroundResource(R.drawable.btn_login_disabled);
        } else {
            this.boton_siguiente.setBackgroundResource(R.drawable.btn_login);
        }
    }

    private void clickSiguienteBtn() {
        boton_siguiente.setOnClickListener((View) -> {
            receta.setDuracion(tiempoET.getText().toString());
            receta.setRaciones(Integer.parseInt(comensalesET.getText().toString()));
            receta.setDificultad(dificultad);
            receta.setNombre(nombreET.getText().toString());

            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.from_right, R.anim.to_left);
            fragmentTransaction.replace(R.id.frame_info, new FragmentAddIngToReceta(receta, email));
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
