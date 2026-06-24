package modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un producto utilizado durante la prestación de servicios de
 * peluquería.
 *
 * Cada producto dispone de un nombre y una cantidad disponible en stock.
 *
 * La información se almacena en la tabla productos.
 *
 * @author Mario
 * @version 1.0.3
 */
public class Producto {

	private int id;
	private String nombre;
	private int stock;

	public Producto() {
	}

	public Producto(int id, String nombre, int stock) {
		this.id = id;
		this.nombre = nombre;
		this.stock = stock;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	/**
	 * Recupera un producto a partir de su identificador.
	 *
	 * @param idProducto identificador del producto.
	 * @param conexionBD conexión activa contra la base de datos.
	 *
	 * @return el producto encontrado o null si no existe.
	 */
	public static Producto buscarProductoPorId(int idProducto, Connection conexionBD) {
		String sql = """
				SELECT ID, NOMBRE, STOCK
				FROM productos
				WHERE ID = ?
				""";

		try (PreparedStatement ps = conexionBD.prepareStatement(sql)) {
			ps.setInt(1, idProducto);

			try (ResultSet rs = ps.executeQuery()) {
				if (!rs.next()) {
					return null;
				}

				return mapearProducto(rs);
			}

		} catch (SQLException e) {
			System.err.println("Error al buscar producto por ID: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Obtiene todos los productos registrados.
	 *
	 * @param conexionBD conexión activa contra la base de datos.
	 *
	 * @return lista de productos disponibles.
	 */
	public static List<Producto> buscarTodosProductos(Connection conexionBD) {
		List<Producto> productos = new ArrayList<>();

		String sql = """
				SELECT ID, NOMBRE, STOCK
				FROM productos
				ORDER BY NOMBRE
				""";

		try (PreparedStatement ps = conexionBD.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				productos.add(mapearProducto(rs));
			}

		} catch (SQLException e) {
			System.err.println("Error al buscar productos: " + e.getMessage());
		}

		return productos;
	}

	/**
	 * Actualiza el stock disponible de un producto.
	 *
	 * Este método sobrescribe la cantidad existente por la indicada.
	 *
	 * @param idProducto identificador del producto.
	 * @param nuevoStock nueva cantidad disponible.
	 * @param conexionBD conexión activa contra la base de datos.
	 *
	 * @return true si la actualización tuvo éxito; false en caso
	 *         contrario.
	 */
	public static boolean actualizarStock(int idProducto, int nuevoStock, Connection conexionBD) {
		String sql = """
				UPDATE productos
				SET STOCK = ?
				WHERE ID = ?
				""";

		try (PreparedStatement ps = conexionBD.prepareStatement(sql)) {
			ps.setInt(1, nuevoStock);
			ps.setInt(2, idProducto);

			return ps.executeUpdate() > 0;

		} catch (SQLException e) {
			System.err.println("Error al actualizar stock: " + e.getMessage());
			return false;
		}
	}

	private static Producto mapearProducto(ResultSet rs) throws SQLException {
		Producto producto = new Producto();

		producto.setId(rs.getInt("ID"));
		producto.setNombre(rs.getString("NOMBRE"));
		producto.setStock(rs.getInt("STOCK"));

		return producto;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", stock=" + stock + "]";
	}
}