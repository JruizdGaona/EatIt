package com.example.eatit.entities;

public class Ingrediente {
    private String nombre, fechaCaducidad, tipo;
    private float kcal, cantidad;

    public Ingrediente(String nombre, String fechaCaducidad, String tipo, float kcal, float cantidad) {
        this.nombre = nombre;
        this.fechaCaducidad = fechaCaducidad;
        this.tipo = tipo;
        this.kcal = kcal;
        this.cantidad = cantidad;
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

    public float getKcal() {
        return kcal;
    }

    public void setKcal(float kcal) {
        this.kcal = kcal;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }
}
