package com.crudjava.Model;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

//Clase utilizada para establecer la conexion hacia la base de datos
public class Conexion {

    // nombre del driver JDBC
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    //URL para la conexión a la base de datos
    static final String DB_URL = "jdbc:mysql://localhost:3306/personabdd";

    // credenciales de la base de datos (solo para trabajo local y pruebas de bajo riesgo)
    static final String USER = "root";
    static final String PASS = "";

    Connection conexion =null; //variable que almacena la conexion

    Statement stmt = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    public String query="";

    //Metodo constructor de la clase
    public Conexion() {
        this.conexion = null;
        this.st = null;
        this.rs = null;
        this.query = "";
    }


    //Metodo para establecer la conexion con la base de datos
    public void conectar(){
        try
        {
            //Driver para mysql
            Class.forName(JDBC_DRIVER);
            // Conexion con la base de datos
            conexion = DriverManager.getConnection(DB_URL, USER, PASS);
        }
        catch (ClassNotFoundException e1) {
            System.out.println("ERROR:No se encontro el driver de la BD: "+e1.getMessage());
        }catch(SQLException e2){
            System.out.println("ERROR:No se puede conectar con la BD: "+e2.getMessage());
        }
    }


    //Metodo para cerra la sesion con la base de datos
    public void desconectar() throws SQLException {

        if (rs != null) {
            rs.close();
        }
        if (st != null) {
            st.close();
        }

        if (conexion != null) {
            conexion.close();
        }
    }

}
