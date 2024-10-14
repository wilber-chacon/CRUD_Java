package com.crudjava.Utils;

import com.crudjava.Model.OcupacionesDAO;
import com.crudjava.Model.PersonaDAO;
import java.sql.SQLException;

public class Validaciones {

    public boolean validarNombre(String nombre){
        String expresionNombre = "^[a-zA-Z áéíóúÁÉÍÓÚñÑs]*$";
        if(nombre.length()==0 || nombre.equals("") || !nombre.matches(expresionNombre)) {
            return false;
        }
        return true;
    }

    public boolean validarGenero(String genero){
        String expresion = "^[a-zA-Z áéíóúÁÉÍÓÚñÑs]*$";
        if(genero.length()==0 || genero.equals("") || !genero.matches(expresion)) {
            return false;
        }
        return true;
    }
    public boolean validarFecha(String fecha){
        String expresion = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$";
        if(!fecha.matches(expresion)) {
            return false;
        }
        return true;
    }


    public boolean validarOcupacion(String id){
        OcupacionesDAO ocupacionesDAO = new OcupacionesDAO();
        try {
            if (ocupacionesDAO.obtenerOcupacion(Integer.parseInt(id)) != null){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }catch (Exception ex){
            System.out.println(ex);

        }

        return false;
    }


    public boolean validaridPersona(String id){
        PersonaDAO personaDAO = new PersonaDAO();
        try {
            if (personaDAO.obtenerPersona(Integer.parseInt(id)) == null){
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }catch (Exception ex){
            return false;
        }

        return true;
    }
}
