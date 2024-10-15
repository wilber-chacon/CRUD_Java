package com.crudjava.Bean;

import java.sql.Date;

//CLase Bean utilizada para almacenar los datos de personas
public class Persona {
    private int id_persona; //guarda el identificador del registro
    private String nombre_persona; //guarda el nombre de la persona
	private int edad_persona; //guarda la edad de la persona
	private String sexo_persona; //guarda el genero de la persona
	private Ocupacion ocupacion; //guarda la ocupacion de la persona
	private Date fecha_nac;//guarda la fehca de nacimiento de la persona

    //Metodo constructor de la clase
    public Persona() {
        this.id_persona = 0;
        this.nombre_persona = "";
        this.edad_persona = 0;
        this.sexo_persona = "";
        this.ocupacion = null;
        this.fecha_nac = null;
    }


    //Metodos getter y setter utilizados para recuperar y asignar informacion en los atributos de la clase
    public int getId_persona() {
        return id_persona;
    }

    public void setId_persona(int id_persona) {
        this.id_persona = id_persona;
    }

    public String getNombre_persona() {
        return nombre_persona;
    }

    public void setNombre_persona(String nombre_persona) {
        this.nombre_persona = nombre_persona;
    }

    public int getEdad_persona() {
        return edad_persona;
    }

    public void setEdad_persona(int edad_persona) {
        this.edad_persona = edad_persona;
    }

    public String getSexo_persona() {
        return sexo_persona;
    }

    public void setSexo_persona(String sexo_persona) {
        this.sexo_persona = sexo_persona;
    }

    public Ocupacion getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(Ocupacion ocupacion) {
        this.ocupacion = ocupacion;
    }

    public Date getFecha_nac() {
        return fecha_nac;
    }

    public void setFecha_nac(Date fecha_nac) {
        this.fecha_nac = fecha_nac;
    }
}
