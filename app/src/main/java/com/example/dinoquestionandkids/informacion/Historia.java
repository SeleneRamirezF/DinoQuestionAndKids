package com.example.dinoquestionandkids.informacion;

import java.io.Serializable;

public class Historia implements Serializable {

    private String descripcion;
    private String nombre;
    private int imagen;

    public Historia() { }

    public Historia(String descripcion, String nombre, int imagen) {
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
