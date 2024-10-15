package com.crudjava.Bean;


//CLase Bean utilizada para almacenar los datos de Ocupaciones
public class Ocupacion {
    private int id_ocupacion;//guarda el identificador del registro
	private String ocupacion;//guarda el nombre de la ocupacion

    //Metodo constructor de la clase
    public Ocupacion() {
        this.id_ocupacion = 0;
        this.ocupacion = "";
    }



    //Metodos getter y setter utilizados para recuperar y asignar informacion en los atributos de la clase
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
