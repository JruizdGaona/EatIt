package com.example.eatit.entities;

import java.io.Serializable;
import java.util.List;

/**
 * @author Javier Ruiz de Gaona Tre.
 */
public class Usuario implements Serializable {
    String correo, nombre, apellido;
    List<Receta> recetas;

    public Usuario() {}

    public Usuario(String correo, String nombre, String apellido) {
        this.correo = correo;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public List<Receta> getRecetas() {
        return recetas;
    }

    public void setRecetas(List<Receta> recetas) {
        this.recetas = recetas;
    }
}