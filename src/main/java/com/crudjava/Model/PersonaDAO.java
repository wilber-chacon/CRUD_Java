package com.crudjava.Model;

import com.crudjava.Bean.Ocupacion;
import com.crudjava.Bean.Persona;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

//Clase utilizada para acceder y manipular la informacion sobre la tabla persona de la base de datos,
//ademas, hereda de la clase conexion, lo que significa que podra hacer uso de los atributos y metodos de la clase padre
public class PersonaDAO extends Conexion{

    //Metodo para listar todos los registros de la tabla, retorna un objeto de tipo ArrayList
    public ArrayList<Persona> findAll() throws SQLException {
        conectar();//estableciendo conexion con la base de datos
        //Creando y ejecutando la consulta con sentencias SQL
        stmt = conexion.createStatement();
        rs = stmt.executeQuery("SELECT p.id_persona, p.nombre_persona, p.edad_persona, p.sexo_persona, o.id_ocupacion, o.ocupacion, p.fecha_nac FROM persona as p JOIN ocupaciones as o ON p.id_ocupacion = o.id_ocupacion");
        ArrayList<Persona> registros = new ArrayList();//Objeto ArrayList en el que se guardan los registros recuparados

        //recorriendo el resultado obtenido
        while(rs.next()){
            Persona persona = new Persona();//instancia de la clase persona
            Ocupacion ocupacion = new Ocupacion();//instancia de la clase ocupacion
            //guardando los resultados obtenidos de la consulta
            persona.setId_persona(rs.getInt(1));
            persona.setNombre_persona(rs.getString(2));
            persona.setEdad_persona(rs.getInt(3));
            persona.setSexo_persona(rs.getString(4));
            ocupacion.setId_ocupacion(rs.getInt(5));
            ocupacion.setOcupacion(rs.getString(6));
            persona.setFecha_nac(Date.valueOf(rs.getString(7)));
            persona.setOcupacion(ocupacion);

            registros.add(persona);//añadiendo el registro a la lista
        }

        desconectar();//cerrando la conexion con la base de datos
        return registros;//retornando la lista con los registros
    }


    //Metodo utilizado para insertar un nuevo registro en la base de datos, recibe un objeto de tipo persona
    public int insertarRegistro(Persona persona) throws SQLException {
        try {
            conectar();//estableciendo conexion con la base de datos
            //Creando la sentencia SQL con datos parametrizados
            query = "INSERT INTO persona (nombre_persona, edad_persona, sexo_persona, id_ocupacion, fecha_nac) VALUES (?,?,?,?,?)";
            st = conexion.prepareStatement(query);
            //pasando los datos parametrizados desde el objeto persona
            st.setString(1, persona.getNombre_persona());
            st.setInt(2, persona.getEdad_persona());
            st.setString(3, persona.getSexo_persona());
            st.setInt(4, persona.getOcupacion().getId_ocupacion());
            st.setDate(5, persona.getFecha_nac());

            //Ejecutando la consulta SQL y retornando el resultado
            return st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            desconectar();//cerrando la conexion con la base de datos
        }

        return 0;
    }

    //Metodo utilizado para eliminar un registro de la base de datos,
    // recibe como parametro el identificador el registro a eliminar
    public int eliminarRegistro(int id) throws SQLException {
        try {
            conectar();//estableciendo conexion con la base de datos
            //Creando la sentencia SQL con datos parametrizados
            query = "DELETE FROM persona WHERE id_persona=?";
            st = conexion.prepareStatement(query);
            st.setInt(1, id);//colocando el identificador del registro en la consulta parametrizada
            return st.executeUpdate();//Ejecutando la consulta SQL y retornando el resultado
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            desconectar();//cerrando la conexion con la base de datos
        }

        return 0;
    }


    //Metodo utlizado para recuperar un registro de la base de datos,
    // recibe como parametro el identificador el registro a consultar
    public Persona obtenerPersona(int id) throws SQLException {
        Persona persona = new Persona();//instancia de la clase persona
        Ocupacion ocupacion = new Ocupacion();//instancia de la clase ocupacion
        try {
            conectar();//estableciendo conexion con la base de datos
            //Creando la sentencia SQL con datos parametrizados
            query = "SELECT p.id_persona, p.nombre_persona, p.edad_persona, p.sexo_persona, o.id_ocupacion, o.ocupacion, p.fecha_nac FROM persona as p JOIN ocupaciones as o ON p.id_ocupacion = o.id_ocupacion WHERE p.id_persona = ?";
            st = conexion.prepareStatement(query);
            st.setInt(1, id);
            rs = st.executeQuery();//Ejecutando la consulta SQL

            //recorriendo el resultado obtenido
            if (rs.next()) {
                //guardando los resultados obtenidos de la consulta
                persona.setId_persona(rs.getInt("id_persona"));
                persona.setNombre_persona(rs.getString("nombre_persona"));
                persona.setEdad_persona(rs.getInt("edad_persona"));
                persona.setSexo_persona(rs.getString("sexo_persona"));
                persona.setFecha_nac(rs.getDate("fecha_nac"));
                ocupacion.setId_ocupacion(rs.getInt("id_ocupacion"));
                ocupacion.setOcupacion(rs.getString("ocupacion"));
                persona.setOcupacion(ocupacion);
            }else {
                persona = null;//no se obtuvo ningun registro
            }

        }catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            desconectar();//cerrando la conexion con la base de datos
        }
        return persona;//Retornando el objeto

    }


    //Metodo utilizado para actualizar los datos de un registro de la base de datos,
    // recibe como parametro un objeto de tipo persona
    public int actualizarRegistro(Persona persona) throws SQLException {
        try {
            conectar();//estableciendo conexion con la base de datos
            //Creando la sentencia SQL con datos parametrizados
            String query2 = "UPDATE persona SET nombre_persona=?, edad_persona=?, sexo_persona=?, id_ocupacion=?, fecha_nac=? WHERE id_persona=?;";
            st = conexion.prepareStatement(query2);
            st.setString(1, persona.getNombre_persona());
            st.setInt(2, persona.getEdad_persona());
            st.setString(3, persona.getSexo_persona());
            st.setInt(4, persona.getOcupacion().getId_ocupacion());
            st.setDate(5, persona.getFecha_nac());
            st.setInt(6, persona.getId_persona());
            return st.executeUpdate();//Ejecutando la consulta SQL y retornando el resultado
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            desconectar();//cerrando la conexion con la base de datos
        }
        return 0;
    }


}
