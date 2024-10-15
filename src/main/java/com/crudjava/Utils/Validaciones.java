package com.crudjava.Utils;

import com.crudjava.Model.OcupacionesDAO;
import com.crudjava.Model.PersonaDAO;
import java.sql.SQLException;


//clase utilizada para validar la informacion enviada por medio del formulario
public class Validaciones {

    //Metodo para validar que el nombre no contenga caractes numericos,
    // recibe como parametro una variable de tipo string con el nombre y retorna un valor booleano según sea el resultado
    public boolean validarNombre(String nombre){
        String expresionNombre = "^[a-zA-Z áéíóúÁÉÍÓÚñÑs]*$";
        if(nombre.length()==0 || nombre.equals("") || !nombre.matches(expresionNombre)) {
            return false; //contiene algun valor numerico o es vacío
        }
        return true;
    }

    //Metodo para validar que el atributo genero no contenga caractes numericos,
    // recibe como parametro una variable de tipo string con el genero y retorna un valor booleano según sea el resultado
    public boolean validarGenero(String genero){
        String expresion = "^[a-zA-Z áéíóúÁÉÍÓÚñÑs]*$";
        if(genero.length()==0 || genero.equals("") || !genero.matches(expresion)) {
            return false; //contiene algun valor numerico o es vacío
        }
        return true;
    }

    //Metodo para validar que la fecha ingresada cumpla con la estructura año-mes-dia,
    // recibe como parametro una variable de tipo string con la fecha y retorna un valor booleano según sea el resultado
    public boolean validarFecha(String fecha){
        String expresion = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$";
        if(!fecha.matches(expresion)) {
            return false; //no cumple con la expresion regular
        }
        return true;
    }

    //Metodo para validar que el atributo ocupacion exista en la base de datos,
    // recibe como parametro una variable de tipo string con el id y asi consultar su informacion en la base de datos,
    // posteriormente retorna un valor booleano según sea el resultado
    public boolean validarOcupacion(String id){
        OcupacionesDAO ocupacionesDAO = new OcupacionesDAO();
        try {
            if (ocupacionesDAO.obtenerOcupacion(Integer.parseInt(id)) != null){
                return true; //mantiene existencia en la base de datos
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }catch (Exception ex){
            System.out.println(ex);

        }

        return false;
    }


    //Metodo para validar que el regsitro exista en la base de datos,
    // recibe como parametro una variable de tipo string con el id y asi consultar su informacion en la base de datos,
    // posteriormente retorna un valor booleano según sea el resultado
    public boolean validaridPersona(String id){
        PersonaDAO personaDAO = new PersonaDAO();
        try {
            if (personaDAO.obtenerPersona(Integer.parseInt(id)) == null){
                return false; //no mantiene existencia en la base de datos
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }catch (Exception ex){
            return false;
        }

        return true;
    }
}
