package com.example.eatit.entities;

public class Ingrediente {
    private String nombre, fechaCaducidad, tipo, usuarioId;;

    public Ingrediente(String nombre, String fechaCaducidad, String tipo, String usuario) {
        this.nombre = nombre;
        this.fechaCaducidad = fechaCaducidad;
        this.tipo = tipo;
        usuarioId = usuario;
    }

    public Ingrediente(){
    }

    public Ingrediente(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(String fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUsuarioId() {return usuarioId;}

    public void setUsuarioId(String usuarioId) {this.usuarioId = usuarioId;}
}
