package com.berenjeneitor.facturacion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    public void conectar() {
        String url = "jdbc:mysql://localhost:3306/facturacion";
        String user = "root";
        String password = "9871";
        try{
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión exitosa");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void insert (String query) {
        String url = "jdbc:mysql://localhost:3306/facturacion";
    }
}
