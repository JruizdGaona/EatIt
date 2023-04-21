package com.example.eatit.fragments.ingredientes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.eatit.R;
import com.example.eatit.entities.Ingrediente;
import com.example.eatit.entities.Usuario;
import com.example.eatit.fragments.adapters.AdapterIngrediente;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Javier Ruiz de Gaona Tre.
 */
public class FrameIngredientes extends Fragment {

    // Declaración de Variables.
    List<Ingrediente> ingredientes = new ArrayList<>();
    RecyclerView recyclerView;
    AdapterIngrediente adapterIngrediente;
    Usuario usuario;
    FirebaseFirestore database;
    CollectionReference coleccion;
    private boolean paused = false;

    public FrameIngredientes (Usuario usuario) {this.usuario = usuario;}

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
        View view = LayoutInflater.from(getContext()).inflate(R.layout.frame_ingredientes,container,false);
        recyclerView = view.findViewById(R.id.fragment_ingredientes);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));

        FloatingActionButton floatingActionButton = view.findViewById(R.id.btn_flotante);

        floatingActionButton.setOnClickListener((View) -> {
            paused = true;
            this.onPause();
        });

        mostrarIngredientes();
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();

        if (paused) {
            paused = false;
            CardAddIngrediente cardAddIngrediente = new CardAddIngrediente(getContext(), usuario);

            cardAddIngrediente.operacionesCardView();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        database = FirebaseFirestore.getInstance();
        coleccion = database.collection("ingredientes");

        coleccion.addSnapshotListener((snapshot, e) -> {
            if (e != null) {
                return;
            }
            mostrarIngredientes();
        });
    }

    /**
     * Método que crea los ingredientes y los carga en el adapter de ingredientes.
     */
    private void mostrarIngredientes() {
        ingredientes = usuario.getIngredientes();
        adapterIngrediente = new AdapterIngrediente(ingredientes, FrameIngredientes.this.getContext());
        recyclerView.setAdapter(adapterIngrediente);
    }
}
