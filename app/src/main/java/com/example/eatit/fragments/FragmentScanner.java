package com.example.eatit.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.eatit.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FragmentScanner extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_ingredientes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        FloatingActionButton floatingActionButton = view.findViewById(R.id.btn_flotante);

        floatingActionButton.setOnClickListener((View) -> Toast.makeText(this.getContext(), "Añadir Ingrediente", Toast.LENGTH_SHORT).show());
    }
}
