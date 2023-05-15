package com.example.eatit.fragments.recetas.actualizar;

import android.content.Context;
import android.net.Uri;
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

public class FragmentActualizarReceta extends Fragment {
    private String[] dificultadReceta = {"Fácil", "Media", "Difícil", "Sólo para expertos"};
    private AppCompatButton boton_siguiente;
    private Receta receta;
    private Spinner spinnerDificultad;
    TextInputEditText tiempoET, comensalesET, nombreET;
    TextInputLayout tiempo, nombre, comensales;
    private String dificultad;
    String email, recetaOldName;
    Uri uri;

    public FragmentActualizarReceta(String email, Receta receta, Uri uri) {
        this.email = email;
        this.receta = receta;
        this.recetaOldName = receta.getNombre();
        this.uri = uri;
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
        comprobarTiempo();
        comprobarNombre();
        comprobarComensales();

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
        tiempo = view.findViewById(R.id.layoutTextInput_tiempo);
        nombre = view.findViewById(R.id.layoutTextInput_nombre);
        comensales = view.findViewById(R.id.layoutTextInput_comensales);

        tiempoET.setText(receta.getDuracion());
        comensalesET.setText(String.valueOf(receta.getRaciones()));
        nombreET.setText(receta.getNombre());
        cambiarEstadoBoton(true);

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
            fragmentTransaction.replace(R.id.frame_info, new FragmentEditarIngReceta(receta, email, recetaOldName, uri));
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
