package com.example.eatit;

import java.io.Serializable;

/**
 * @author Javier Ruiz de Gaona Tre.
 */
public class Receta implements Serializable {
    private String nombre, dificultad;
    private float duracion;

    public Receta(String nombre, String dificultad, float duracion) {
        this.nombre = nombre;
        this.dificultad = dificultad;
        this.duracion = duracion;
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

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public float getDuracion() {
        return duracion;
    }

    public void setDuracion(float duracion) {
        this.duracion = duracion;
    }
}
