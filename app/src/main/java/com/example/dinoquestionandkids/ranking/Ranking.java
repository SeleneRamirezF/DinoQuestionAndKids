package com.example.dinoquestionandkids.ranking;

import java.io.Serializable;

public class Ranking implements Serializable, Comparable<Ranking> {

    private String nombre;
    private String puntuacion;

    public Ranking(){

    }

    public Ranking(String nombre, String puntuacion) {
        this.nombre = nombre;
        this.puntuacion = puntuacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(String puntuacion) {
        this.puntuacion = puntuacion;
    }

    @Override
    public int compareTo(Ranking o) {
        int puntuacion2 = Integer.parseInt(puntuacion);
        int puntuacion2o = Integer.parseInt(o.puntuacion);
        if (puntuacion2 < puntuacion2o) {
            return 1;
        }
        if (puntuacion2 > puntuacion2o) {
            return -1;
        }
        return 0;
    }
}
