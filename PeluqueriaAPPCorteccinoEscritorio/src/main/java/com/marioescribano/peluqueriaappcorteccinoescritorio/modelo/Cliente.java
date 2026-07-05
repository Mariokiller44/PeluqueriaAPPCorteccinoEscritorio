/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.marioescribano.peluqueriaappcorteccinoescritorio.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Representa a un cliente registrado en la peluquería.
 *
 * Un cliente es un usuario que posee una categoría asociada para segmentar su
 * relación comercial con el negocio.
 *
 * Las categorías disponibles se almacenan en la tabla
 *
 * @author Mario
 * @version 1.0.3
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

	/**
	 * Obtiene todos los clientes registrados en el sistema.
	 *
	 * @param conexionBD conexión activa contra la base de datos.
	 *
	 * @return lista de usuarios que son clientes. Si no existen registros, devuelve
	 *         una lista vacía.
	 */
	public static ArrayList<Cliente> buscarTodosClientes(Connection conexionBD) {
		ArrayList<Cliente> clientes = new ArrayList<>();

		ArrayList<Usuario> usuarios = buscarTodosUsuarios(conexionBD);

		for (Usuario usuario : usuarios) {

			Cliente cliente = obtenerFiltradosClientes(usuario.getId(), conexionBD);

			if (cliente != null) {
				clientes.add(cliente);
			}
		}

		return clientes;
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
	public static Cliente obtenerFiltradosClientes(int idCliente, Connection conexionBD) {

		String sql = "SELECT u.id, u.nombre, u.apellidos, u.email, u.telefono, u.cuenta, u.contrasenia, p.categoria_cliente FROM Usuario u INNER JOIN Perfiles_Usuario p ON u.id = p.usuario_id WHERE p.usuario_id = ? AND p.tipo = 'CLIENTE'";

		try (PreparedStatement ps = conexionBD.prepareStatement(sql)) {
			ps.setInt(1, idCliente);

			try (ResultSet rs = ps.executeQuery()) {
				if (!rs.next()) {
					return null;
				}

				Cliente cliente = new Cliente();
				mapearUsuario(rs,cliente);
				cliente.setCategoria(rs.getString("categoria_cliente"));

				return cliente;
			}

		} catch (SQLException e) {
			System.err.println("Error al obtener cliente por ID: " + e.getMessage());
			return null;
		}
	}

}
