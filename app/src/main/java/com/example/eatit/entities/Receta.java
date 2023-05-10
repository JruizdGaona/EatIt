package com.example.eatit.entities;

import android.net.Uri;

import java.io.Serializable;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Javier Ruiz de Gaona Tre.
 */
public class Receta implements Serializable {
    private String nombre, dificultad, duracion;
    private List<String> ingredientes;
    private Map<String, String> pasos;
    private String uri;
    private int raciones, popularidad;
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

    public Map<String, String> getPasos() {
        return pasos;
    }

    public void setPasos(Map<String, String> pasos) {
        this.pasos = pasos;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getPopularidad() {return popularidad;}

    public void setPopularidad(int popularidad) {this.popularidad = popularidad;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Receta receta = (Receta) o;
        return Objects.equals(nombre, receta.nombre) && Objects.equals(usuarioId, receta.usuarioId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, dificultad, duracion, ingredientes, pasos, uri, raciones, popularidad, usuarioId);
    }
}
