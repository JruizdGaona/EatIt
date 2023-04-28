package com.example.eatit.entities;

import java.io.Serializable;
import java.net.URI;
import java.util.List;

/**
 * @author Javier Ruiz de Gaona Tre.
 */
public class Receta implements Serializable {
    private String nombre, dificultad, duracion;
    private List<String> ingredientes, pasos;
    private URI uri;
    private int raciones;
    private String usuarioId;

    public Receta() {}

    public Receta(String nombre, String dificultad, String duracion, int raciones, String usuario) {
        this.nombre = nombre;
        this.dificultad = dificultad;
        this.duracion = duracion;
        this.raciones = raciones;
        this.usuarioId = usuario;
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

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public int getRaciones() {
        return raciones;
    }

    public void setRaciones(int raciones) {
        this.raciones = raciones;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuario(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public List<String> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<String> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public List<String> getPasos() {
        return pasos;
    }

    public void setPasos(List<String> pasos) {
        this.pasos = pasos;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }
}
