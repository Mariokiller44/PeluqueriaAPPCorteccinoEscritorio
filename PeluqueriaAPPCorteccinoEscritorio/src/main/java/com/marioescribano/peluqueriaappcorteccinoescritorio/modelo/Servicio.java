package com.marioescribano.peluqueriaappcorteccinoescritorio.modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un servicio ofrecido por la peluquería.
 *
 * Un servicio puede utilizar un producto asociado y dispone de un precio
 * configurado.
 *
 * La información se almacena en la tabla Servicios.
 *
 * @author Mario
 * @version 1.0.3
 */
public class Servicio {

	private int id;
	private String descripcion;
	private int productoId;
	private double precio;

	public Servicio() {
	}

	public Servicio(int id, String descripcion, int productoId, double precio) {
		this.id = id;
		this.descripcion = descripcion;
		this.productoId = productoId;
		this.precio = precio;
	}

	public Servicio(int id, String descripcion) {
		this.id = id;
		this.descripcion = descripcion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getProductoId() {
		return productoId;
	}

	public void setProductoId(int productoId) {
		this.productoId = productoId;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	/**
	 * Recupera un servicio a partir de su identificador.
	 *
	 * @param idServicio identificador del servicio.
	 * @param conexionBD conexión activa contra la base de datos.
	 *
	 * @return el servicio encontrado o null si no existe.
	 */
	public static Servicio buscarServicioPorId(int idServicio, Connection conexionBD) {
		String sql = """
				SELECT id, descripcion, producto_id, precio
				FROM servicios
				WHERE id = ?
				""";

		try (PreparedStatement ps = conexionBD.prepareStatement(sql)) {
			ps.setInt(1, idServicio);

			try (ResultSet rs = ps.executeQuery()) {
				if (!rs.next()) {
					return null;
				}

				return mapearServicio(rs);
			}

		} catch (SQLException e) {
			System.err.println("Error al buscar servicio por ID: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Obtiene todos los servicios disponibles.
	 *
	 * Los resultados se devuelven ordenados por descripción.
	 *
	 * @param conexionBD conexión activa contra la base de datos.
	 *
	 * @return lista de servicios registrados.
	 */
	public static List<Servicio> buscarTodosServicios(Connection conexionBD) {
		List<Servicio> servicios = new ArrayList<>();

		String sql = """
				SELECT id, descripcion, producto_id, precio
				FROM servicios
				ORDER BY descripcion
				""";

		try (PreparedStatement ps = conexionBD.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				servicios.add(mapearServicio(rs));
			}

		} catch (SQLException e) {
			System.err.println("Error al buscar servicios: " + e.getMessage());
		}

		return servicios;
	}

	/**
	 * Busca el identificador de un servicio utilizando su descripción y precio.
	 *
	 * @param descripcion descripción del servicio.
	 * @param precio      precio del servicio.
	 * @param conexionBD  conexión activa contra la base de datos.
	 *
	 * @return identificador del servicio o -1 si no existe coincidencia.
	 */
	public static int buscarIdServicio(String descripcion, double precio, Connection conexionBD) {
		String sql = """
				SELECT id
				FROM servicios
				WHERE descripcion = ?
				  AND precio = ?
				""";

		try (PreparedStatement ps = conexionBD.prepareStatement(sql)) {
			ps.setString(1, descripcion);
			ps.setDouble(2, precio);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("id");
				}
			}

		} catch (SQLException e) {
			System.err.println("Error al buscar ID de servicio: " + e.getMessage());
		}

		return -1;
	}

	/**
	 * Actualiza la información de un servicio existente.
	 *
	 * Permite modificar la descripción, el producto asociado y el precio del
	 * servicio.
	 *
	 * @param idServicio  identificador del servicio.
	 * @param descripcion nueva descripción.
	 * @param productoId  identificador del producto asociado.
	 * @param precio      nuevo precio.
	 * @param conexionBD  conexión activa contra la base de datos.
	 *
	 * @return true si la actualización se realizó correctamente;
	 *         false en caso contrario.
	 */
	public static boolean actualizarServicio(int idServicio, String descripcion, int productoId, double precio,
			Connection conexionBD) {

		String sql = """
				UPDATE servicios
				SET descripcion = ?,
				    producto_id = ?,
				    precio = ?
				WHERE id = ?
				""";

		try (PreparedStatement ps = conexionBD.prepareStatement(sql)) {
			ps.setString(1, descripcion);
			ps.setInt(2, productoId);
			ps.setDouble(3, precio);
			ps.setInt(4, idServicio);

			return ps.executeUpdate() > 0;

		} catch (SQLException e) {
			System.err.println("Error al actualizar servicio: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Convierte la fila actual del ResultSet en un objeto Servicio.
	 *
	 * @param rs resultado posicionado sobre una fila válida.
	 *
	 * @return instancia de Servicio completamente inicializada.
	 *
	 * @throws SQLException si ocurre un error durante la lectura de los datos.
	 */
	private static Servicio mapearServicio(ResultSet rs) throws SQLException {
		Servicio servicio = new Servicio();

		servicio.setId(rs.getInt("id"));
		servicio.setDescripcion(rs.getString("descripcion"));
		servicio.setProductoId(rs.getInt("producto_id"));
		servicio.setPrecio(rs.getDouble("precio"));

		return servicio;
	}

	@Override
	public String toString() {
		return "Servicio [id=" + id + ", descripcion=" + descripcion + ", productoId=" + productoId + ", precio="
				+ precio + "]";
	}
}