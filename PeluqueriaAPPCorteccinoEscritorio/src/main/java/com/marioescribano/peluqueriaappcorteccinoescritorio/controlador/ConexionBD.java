/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.marioescribano.peluqueriaappcorteccinoescritorio.controlador;

import java.sql.*;

/**
 * Clase para conectarse a la base de datos
 * 
 * @author Mario
 * @version 1.0.1
 */
public class ConexionBD implements Configuracion {
	/*
	 * Atributos de la clase ConexionBD:
	 */
	private static String usuario;
	private static String password;
	private static Connection conexion;
	private static String URL = Configuracion.URL; // Establecemos la URL de la base de datos a la predeterminada en la
													// interfaz Configuracion.

	/*
	 * Constructor parametrizado la clase ConexionBD:
	 */
	public ConexionBD(String usu, String passwd) {
		usuario = usu;
		password = passwd;
		conectar(usuario, passwd);
	}

	/*
	 * Método para conectar a la base de datos
	 */
	private static Connection conectar(String usu, String pass) {
		try {
			conexion = DriverManager.getConnection(URL, usuario, password);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error en la conexion: " + e.getMessage());
			conexion = null;
		}
		return conexion;
	}

	public static Connection getConexion() {
		return conexion;
	}
	/*
	 * Método para cerrar la conexión a la base de datos
	 */
	@Override
	public void cerrarConnection() {
		try {
			conexion.close();
		} catch (SQLException e) {
			System.err.println("Error al cerrar la conexión a la base de datos: " + e.getMessage());
		}
	}

	/**
	 * Metodo de conexion usando el usuario y la contraseña por defecto para acceder
	 * a la BD
	 * 
	 * @return la conexion a la base de datos, en caso contrario devuelve null.
	 */
	public static Connection conectarSinLogin() {
		// TODO Auto-generated method stub
		Connection conn = null;
		try {
			conn=conectar("devmario", "marioDev26");
			conn = conexion;
			if (conn != null) {
				System.out.println("Conexión exitosa a la base de datos.");
			} else {
				System.out.println("No se pudo establecer la conexión a la base de datos.");
			}
		} catch (Exception e) {
			System.err.println("Error al conectar a la base de datos: " + e.getMessage());
		}
		return conn;
	}

}
