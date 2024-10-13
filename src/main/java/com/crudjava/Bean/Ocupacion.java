package com.crudjava.Bean;

public class Ocupacion {

    private int id_ocupacion;
	private String ocupacion;

    public Ocupacion() {
        this.id_ocupacion = 0;
        this.ocupacion = "";
    }


    public int getId_ocupacion() {
        return id_ocupacion;
    }

    public void setId_ocupacion(int id_ocupacion) {
        this.id_ocupacion = id_ocupacion;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }
}
