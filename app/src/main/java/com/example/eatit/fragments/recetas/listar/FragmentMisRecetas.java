package com.example.eatit.fragments.recetas.listar;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.eatit.R;
import com.example.eatit.entities.Usuario;
import com.example.eatit.fragments.recetas.listar.FrameMisRecetas;

/**
 * @author Javier Ruiz de Gaona Tre.
 */
public class FragmentMisRecetas extends Fragment {

    private Usuario usuario;
    private AppCompatImageView info;

    public FragmentMisRecetas (Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Método onCreate del fragment de Mis Recetas.
     * @param inflater Variable que inlfa el Layout en la actividad.
     * @param container Contenedor invisible que define la estructura de diseño de View
     * @param savedInstanceState Estado de la instancia de la aplicación.
     *
     * @return Vista creada.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_mis_recetas, container, false);
    }

    /**
     * Método que se ejecuta una vez se ha creado el Fragment nuevo.
     * Llama al Fragment de Mis Recetas para que las muestre en el Frame.
     * @param view Vista del Fragment creado.
     * @param savedInstanceState Estado de la instancia de la aplicación.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        info = view.findViewById(R.id.info_receta);
        abrirInfo();

        Fragment fragmentMisRecetas = new FrameMisRecetas(usuario);
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.frame_mis_recetas, fragmentMisRecetas).commit();
    }

    private void abrirInfo() {
        info.setOnClickListener((View) -> {
            Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.card_info_receta);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            cerrarCardView(dialog);
            dialog.show();
        });
    }

    private void cerrarCardView(Dialog dialog) {
        ImageView imageView = dialog.findViewById(R.id.cerrar_info_receta);
        imageView.setOnClickListener((View) -> dialog.dismiss());
    }
}
