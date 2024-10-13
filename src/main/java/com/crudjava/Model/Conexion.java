package com.crudjava.Model;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/personabdd";
    // Database credentials
    static final String USER = "root";
    static final String PASS = "";

    Connection conexion =null;

    Statement stmt = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    public String query="";

    public Conexion() {
        this.conexion = null;
        this.st = null;
        this.rs = null;
        this.query = "";
    }


    public void conectar(){
        try
        {
            //Driver de para mysql
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
