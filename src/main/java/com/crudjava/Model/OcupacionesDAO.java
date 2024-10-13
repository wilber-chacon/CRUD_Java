package com.crudjava.Model;

import com.crudjava.Bean.Ocupacion;
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
}
