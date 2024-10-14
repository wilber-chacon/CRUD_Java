package com.crudjava.Model;

import com.crudjava.Bean.Ocupacion;
import com.crudjava.Bean.Persona;

import java.sql.SQLException;
import java.util.ArrayList;

public class OcupacionesDAO extends Conexion{

    public ArrayList<Ocupacion> listarOcupaciones() throws SQLException {
        conectar();
        stmt = conexion.createStatement();
        rs = stmt.executeQuery("SELECT * FROM ocupaciones");
        ArrayList<Ocupacion> registros = new ArrayList();

        while(rs.next()){
            Ocupacion ocupacion = new Ocupacion();
            ocupacion.setId_ocupacion(rs.getInt(1));
            ocupacion.setOcupacion(rs.getString(2));
            registros.add(ocupacion);
        }

        desconectar();
        return registros;
    }

    public Ocupacion obtenerOcupacion(int id) throws SQLException {
        Ocupacion ocupacion = new Ocupacion();
        try {
            conectar();
            query = "SELECT id_ocupacion, ocupacion FROM ocupaciones WHERE id_ocupacion = ?";
            st = conexion.prepareStatement(query);
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                ocupacion.setId_ocupacion(rs.getInt("id_ocupacion"));
                ocupacion.setOcupacion(rs.getString("ocupacion"));
            }else {
                ocupacion = null;
            }

        }catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            desconectar();
        }
        return ocupacion;

    }
}
