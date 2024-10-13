package com.crudjava.Model;

import com.crudjava.Bean.Ocupacion;
import com.crudjava.Bean.Persona;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class PersonaDAO extends Conexion{

    public ArrayList<Persona> findAll() throws SQLException {
        conectar();
        stmt = conexion.createStatement();
        rs = stmt.executeQuery("SELECT p.id_persona, p.nombre_persona, p.edad_persona, p.sexo_persona, o.id_ocupacion, o.ocupacion, p.fecha_nac FROM persona as p JOIN ocupaciones as o ON p.id_ocupacion = o.id_ocupacion");
        ArrayList<Persona> registros = new ArrayList();

        while(rs.next()){
            Persona persona = new Persona();
            Ocupacion ocupacion = new Ocupacion();
            persona.setId_persona(rs.getInt(1));
            persona.setNombre_persona(rs.getString(2));
            persona.setEdad_persona(rs.getInt(3));
            persona.setSexo_persona(rs.getString(4));
            ocupacion.setId_ocupacion(rs.getInt(5));
            ocupacion.setOcupacion(rs.getString(6));
            persona.setFecha_nac(Date.valueOf(rs.getString(7)));
            persona.setOcupacion(ocupacion);

            registros.add(persona);
        }

        desconectar();
        return registros;
    }


    public int insertarRegistro(Persona persona) throws SQLException {
        try {
            conectar();
            query = "INSERT INTO persona (nombre_persona, edad_persona, sexo_persona, id_ocupacion, fecha_nac) VALUES (?,?,?,?,?)";
            st = conexion.prepareStatement(query);
            st.setString(1, persona.getNombre_persona());
            st.setInt(2, persona.getEdad_persona());
            st.setString(3, persona.getSexo_persona());
            st.setInt(4, persona.getOcupacion().getId_ocupacion());
            st.setDate(5, persona.getFecha_nac());

            return st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            desconectar();
        }

        return 0;
    }

}
