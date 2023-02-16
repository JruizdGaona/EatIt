package com.example.eatit;

import java.io.Serializable;

/**
 * @author Javier Ruiz de Gaona Tre.
 */
public class Receta implements Serializable {
    private String nombre;

    public Receta() {
    }

    public Receta(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
