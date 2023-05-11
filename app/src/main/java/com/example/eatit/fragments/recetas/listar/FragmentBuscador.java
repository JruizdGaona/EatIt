package com.example.eatit.fragments.recetas.listar;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.eatit.R;
import com.example.eatit.entities.Receta;
import com.example.eatit.fragments.adapters.recetas.AdapterReceta;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Javier Ruiz de Gaona Tre.
 */
public class FragmentBuscador extends Fragment {

    // Declaramos las Variables.
    SearchView searchView;
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    CollectionReference coleccion = database.collection("recetas");
    String email;

    public FragmentBuscador(String email) {this.email = email;}

    /**
     * Método onCreate del fragment de Inicio.
     * @param inflater Variable que inlfa el Layout en la actividad.
     * @param container Contenedor invisible que define la estructura de diseño de View
     * @param savedInstanceState Estado de la instancia de la aplicación.
     *
     * @return Vista creada.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_buscador_recetas, container, false);
    }

    /**
     * Método que se ejecuta una vez se ha creado el Fragment nuevo.
     * Llama al Fragment de Recetas para que las muestre en el Frame.
     * @param view Vista del Fragment creado.
     * @param savedInstanceState Estado de la instancia de la aplicación.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        cargarFragmentRecetas();
        buscar(view);
        cerrar(view);
    }

    /**
     * Método que carga el frgament de las recetas inicialmente.
     */
    private void cargarFragmentRecetas() {
        Fragment fragmentRecetas = new FrameRecetasFiltradas(email);
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.frame_recetas, fragmentRecetas).commit();
    }

    /**
     * Método que añade el Listener al escribir sobre el SearchView.
     * @param view View del fragment actual.
     */
    private void buscar(@NonNull View view) {
        searchView = view.findViewById(R.id.search_view);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filtro(newText);
                return true;
            }
        });
    }

    /**
     * Método que filtra las recetas según ha escrito el usuario en el buscador.
     * @param newText Nombre de la receta que quiere buscar.
     */
    private void filtro(String newText) {
        List<Receta> listRecetasFiltradas = new ArrayList<>();
        List<Receta> recetas = new ArrayList<>();

        Task<QuerySnapshot> obtenerRecetasPopulares = coleccion.get();

        obtenerRecetasPopulares.addOnSuccessListener(recetaSnapshot -> {

            if (!recetaSnapshot.isEmpty()) {
                for (DocumentSnapshot snapshot: recetaSnapshot) {
                    Receta r = snapshot.toObject(Receta.class);
                    recetas.add(r);
                }

                for (Receta r: recetas) {
                    if (r.getNombre().toLowerCase().contains(newText.toLowerCase())) {
                        listRecetasFiltradas.add(r);
                    }
                }

                if (!listRecetasFiltradas.isEmpty()) {
                    Fragment fragmentRecetas = new FrameRecetas(listRecetasFiltradas);
                    FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_recetas, fragmentRecetas).commit();
                } else {
                    Fragment fragmentRecetas = new FrameRecetas(new ArrayList<>());
                    FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_recetas, fragmentRecetas).commit();
                }
            }
        });
    }

    /**
     * Método que cierra el teclado al pulsar sobre cualquier parte del fragment
     * @param view Vista del fragment actual.
     */
    private void cerrar(@NonNull View view) {
        view.setOnTouchListener((v, event) -> {
            // Cerrar el teclado
            InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            return false;
        });
    }
}
