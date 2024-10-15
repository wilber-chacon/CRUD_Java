package com.crudjava.Model;

import com.crudjava.Bean.Ocupacion;
import com.crudjava.Bean.Persona;

import java.sql.SQLException;
import java.util.ArrayList;


//Clase utilizada para acceder y manipular la informacion sobre la tabla ocupaciones de la base de datos,
//ademas, hereda de la clase conexion, lo que significa que podra hacer uso de los atributos y metodos de la clase padre
public class OcupacionesDAO extends Conexion{

    //Metodo para listar todos los registros de la tabla, retorna un objeto de tipo ArrayList
    public ArrayList<Ocupacion> listarOcupaciones() throws SQLException {
        conectar();//estableciendo conexion con la base de datos
        //Creando y ejecutando la consulta con sentencias SQL
        stmt = conexion.createStatement();
        rs = stmt.executeQuery("SELECT * FROM ocupaciones");
        ArrayList<Ocupacion> registros = new ArrayList(); //Objeto ArrayList en el que se guardan los registros recuparados

        while(rs.next()){//recorriendo el resultado obtenido
            Ocupacion ocupacion = new Ocupacion(); //instancia de la clase ocupacion
            //guardando los resultados obtenidos de la consulta
            ocupacion.setId_ocupacion(rs.getInt(1));
            ocupacion.setOcupacion(rs.getString(2));
            registros.add(ocupacion);//añadiendo el registro a la lista
        }

        desconectar();//cerrando la conexion con la base de datos
        return registros;//retornando la lista con los registros
    }

    //Metodo utilizado para obteber un registro en especifico, recibe como parametro el id del registro a consultar
    public Ocupacion obtenerOcupacion(int id) throws SQLException {
        Ocupacion ocupacion = new Ocupacion();//instancia de la clase ocupacion
        try {
            conectar();//estableciendo conexion con la base de datos
            //Creando y ejecutando la consulta con sentencias SQL
            query = "SELECT id_ocupacion, ocupacion FROM ocupaciones WHERE id_ocupacion = ?";
            st = conexion.prepareStatement(query);
            st.setInt(1, id);
            rs = st.executeQuery();
            //recorriendo el resultado obtenido
            if (rs.next()) {
                //guardando los resultados obtenidos de la consulta
                ocupacion.setId_ocupacion(rs.getInt("id_ocupacion"));
                ocupacion.setOcupacion(rs.getString("ocupacion"));
            }else {
                ocupacion = null; //no se obtuvo ningun registro
            }

        }catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            desconectar();//cerrando la conexion con la base de datos
        }
        return ocupacion;//Retornando el objeto

    }
}
