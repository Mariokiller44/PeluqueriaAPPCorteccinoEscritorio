/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.sql.*;
/**
 *  Clase para conectarse a la base de datos usando Sockets
 * @author Mario
 */
public class ConexionBD implements Configuracion{
    private String usuario;
    private String password;
    private Connection conexion;
    private String URL;

    public ConexionBD(String usuario, String password) {
        this.URL=Configuracion.URL;
        usuario="admin";
        password="123pelu";
        try {
            conexion=DriverManager.getConnection(this.URL,usuario,password);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public Connection getConnection() {
        return conexion;
    }

    public void cerrarConnection() {
        try {
            conexion.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión a la base de datos: " + e.getMessage());
        }
    }
    
    
}
