package com.example.eatit.entities;

import java.io.Serializable;
import java.util.List;

/**
 * @author Javier Ruiz de Gaona Tre.
 */
public class Usuario implements Serializable {
    String correo, nombre, apellido, nombreUsuario;
    List<Receta> recetasCreadas;
    List<Receta> recetasGuardadas;
    List<Ingrediente> ingredientes;

    public Usuario() {}

    public Usuario(String correo, String nombre, String apellido) {
        this.correo = correo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nombreUsuario = this.nombre.concat(" ").concat(this.apellido);
    }

    public String getCorreo() {
        return correo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public List<Receta> getRecetasCreadas() {
        return recetasCreadas;
    }

    public void setRecetasCreadas(List<Receta> recetasCreadas) {
        this.recetasCreadas = recetasCreadas;
    }

    public List<Receta> getRecetasGuardadas() {
        return recetasGuardadas;
    }

    public void setRecetasGuardadas(List<Receta> recetasGuardadas) {
        this.recetasGuardadas = recetasGuardadas;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }
}
