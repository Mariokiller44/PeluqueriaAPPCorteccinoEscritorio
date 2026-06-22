package vista;

import java.sql.Connection;

import controlador.ConexionBD;
import controlador.Configuracion;
import modelo.Usuario;

public class Principal {
	static ConexionBD conexionBD;
	static Configuracion config;
	
	
	
	public static void main(String[] args) {
		try {
			int id=20;
			Usuario usuario;
			System.out.println("Conectando...");
			Connection conectar=ConexionBD.getConnection("devmario", "marioDev26");
			System.out.println("Conexion exitosa.");
			System.out.println("Tratando de buscar al usuario con ID 20....");
			usuario=Usuario.buscarUsuarioPorId(id, conectar);
			System.out.println("Datos del usuario con ID 20:\n");
			System.out.println(usuario.toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Hubo un error en la conexion "+ e.getMessage());
		}
	}
}
