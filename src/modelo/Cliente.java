/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Clase que representa a un Cliente
 * 
 * @author Mario
 */
public class Cliente extends Usuario {
	private String categoria;

	public Cliente() {
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	@Override
	public String toString() {
		return super.toString() + ", categoría del cliente: " + categoria;
	}

	/*
	 * Método estático para obtener un cliente por su ID.
	 *
	 * @param idCliente ID del cliente a buscar
	 * 
	 * @param conexionBD Conexión a la base de datos
	 * 
	 * @return Cliente encontrado o null si no se encuentra
	 */
	public static Cliente obtenerClientePorId(int idCliente, Connection conexionBD) {
	    Usuario usuario = Usuario.buscarUsuarioPorId(idCliente, conexionBD);

	    if (usuario == null) {
	        return null;
	    }

	    String sql = """
	        SELECT categoria_cliente
	        FROM Perfiles_Usuario
	        WHERE usuario_id = ?
	          AND tipo = 'CLIENTE'
	        """;

	    try (PreparedStatement ps = conexionBD.prepareStatement(sql)) {
	        ps.setInt(1, idCliente);

	        try (ResultSet rs = ps.executeQuery()) {
	            if (!rs.next()) {
	                return null;
	            }

	            Cliente cliente = new Cliente();

	            cliente.setId(usuario.getId());
	            cliente.setNombre(usuario.getNombre());
	            cliente.setApellidos(usuario.getApellidos());
	            cliente.setEmail(usuario.getEmail());
	            cliente.setTelefono(usuario.getTelefono());
	            cliente.setCuenta(usuario.getCuenta());
	            cliente.setContrasenia(usuario.getContrasenia());

	            cliente.setCategoria(rs.getString("categoria_cliente"));

	            return cliente;
	        }

	    } catch (SQLException e) {
	        System.err.println("Error al obtener cliente por ID: " + e.getMessage());
	        return null;
	    }
	}
}
