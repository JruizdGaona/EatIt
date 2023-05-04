package com.example.eatit.fragments.ingredientes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.eatit.R;
import com.example.eatit.entities.Usuario;

/**
 * @author Javier Ruiz de Gaona tre.
 */
public class FragmentMisIngredientes extends Fragment {

    private Usuario usuario;
    private String correo;

    public FragmentMisIngredientes(Usuario usuario) {
        this.usuario = usuario;
        this.correo = usuario.getCorreo();
    }

    /**
     * Método onCreate del fragment de Ingredientes.
     * @param inflater Variable que inlfa el Layout en la actividad.
     * @param container Contenedor invisible que define la estructura de diseño de View
     * @param savedInstanceState Estado de la instancia de la aplicación.
     *
     * @return Vista creada.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_mis_ingredientes, container, false);
    }

    /**
     * Método que se ejecuta una vez se ha creado el Fragment nuevo.
     * Llama al Fragment de Ingredientes para que las muestre en el Frame.
     * @param view Vista del Fragment creado.
     * @param savedInstanceState Estado de la instancia de la aplicación.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Fragment fragmentIngredientes = new FrameIngredientes(usuario);
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.frame_ingredientes, fragmentIngredientes).commit();
    }
}
