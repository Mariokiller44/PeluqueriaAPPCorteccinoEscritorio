/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.marioescribano.peluqueriaappcorteccinoescritorio.modelo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

/*import com.mysql.cj.protocol.Resultset;*/

/**
 * Representa a cualquier usuario registrado en el sistema.
 *
 * Esta clase contiene la información común compartida por clientes y personal
 * de la peluquería.
 *
 * La información se almacena en la tabla {@code Usuario}.
 *
 * Las clases Cliente y Personal extienden esta clase añadiendo sus
 * características específicas.
 *
 * @author Mario
 * @version 1.0.3
 */
public class Usuario {
	private int id; // ID del usuario
	private String telefono; // Número de teléfono del usuario
	private String nombre; // Nombre del usuario
	private String apellidos; // Apellidos del usuario
	private String email; // Email del usuario
	private String cuenta; // Cuenta del usuario
	private String contrasenia; // Contraseña del usuario
	private String tipo_de_usuario; // Tipo de usuario
	private Connection conexionBaseDatos; // Conexion a la base de datos

	/*
	 * Constructor vacío de la clase Usuario.
	 */
	public Usuario() {
	}

	/*
	 * Constructor de la clase Usuario.
	 *
	 * @param id ID del usuario
	 * 
	 * @param conexionBD Conexion a la Base de Datos
	 */
	public Usuario(int id, Connection conexion) {
		inicializarUsuario(id, conexion);
	}

	/**
	 * Método para inicializar el usuario a través de su id
	 * 
	 * @param id
	 * @return true si se consiguió, false en caso contrario.
	 */
	protected boolean inicializarUsuario(int id, Connection conexionBD) {
		boolean flag = false;
		Usuario devo;
		try {
			devo = buscarUsuarioPorId(id, conexionBD);
			if (devo == null) {
				throw new Exception("El usuario no existe");
			}
		} catch (Exception e) {
			System.out.println("Hubo un error al inicializar usuario. " + e.getMessage());
		}
		return flag;

	}

	/**
	 * Constructor parametrizado del usuario
	 * 
	 * @param id
	 * @param nombre
	 * @param apellidos
	 * @param email
	 * @param telefono
	 * @param cuenta
	 * @param contrasenia codificada en MD5
	 * @param conexionBD
	 */
	public Usuario(int id, String nombre, String apellidos, String email, String telefono, String cuenta,
			String contrasenia, Connection conexionBD) {
		setId(id);
		setNombre(nombre);
		setApellidos(apellidos);
		setEmail(email);
		setTelefono(telefono);
		setCuenta(cuenta);
		setContrasenia(contrasenia);
		this.conexionBaseDatos = conexionBD;
	}

	/*
	 * Constructor de la clase Usuario que recibe ID, nombre y apellidos.
	 *
	 * @param id ID del usuario
	 * 
	 * @param nombre Nombre del usuario
	 * 
	 * @param apellidos Apellidos del usuario
	 */
	public Usuario(int id, String nombre, String apellidos) {
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
	}

	/**
	 * Obtiene todos los empleados registrados en el sistema.
	 *
	 * @param conexionBD conexión activa contra la base de datos.
	 *
	 * @return lista de usuarios que son empleados. Si no existen registros,
	 *         devuelve una lista vacía.
	 */
	protected static ArrayList<Usuario> buscarTodosUsuarios(Connection conexionBD) {
		ArrayList<Usuario> usuarios = new ArrayList<>();

		String sql = "SELECT id, nombre, apellidos, email, telefono, cuenta, contrasenia FROM Usuario";

		try (PreparedStatement ps = conexionBD.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				Usuario usuario = new Usuario();
				mapearUsuario(rs,usuario);

				usuarios.add(usuario);
			}

		} catch (SQLException e) {
			System.err.println("Error al buscar usuarios: " + e.getMessage());
		}

		return usuarios;
	}

	
	/**
	 * Método estático para buscar el usuario por su ID
	 * 
	 * @param id
	 * @param conexionBD
	 * @return el usuario si existe, si no existe devuelve null.
	 */
	public static Usuario buscarUsuarioPorId(int id, Connection conexionBD) {
		// TODO Auto-generated method stub
		Usuario usuario = new Usuario();
		String sql = "SELECT * FROM Usuario WHERE ID = " + id;
		PreparedStatement sentencia;
		ResultSet resultadoConsulta;
		try {
			sentencia = conexionBD.prepareStatement(sql);
			resultadoConsulta = sentencia.executeQuery();

			while (resultadoConsulta.next()) {
				mapearUsuario(resultadoConsulta,usuario);
			}
		} catch (SQLException sqle) {
			// TODO: handle exception
			System.out.println("Error al hacer la consulta.\n " + sqle.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Hubo un error al buscar el usuario.\n" + e.getMessage());
		}
		return usuario;
	}

	/**
	 * Método estático para buscar el usuario a traves de su cuenta.
	 * 
	 * @param cuenta
	 * @param conexionBD
	 */

	public static Usuario buscarIdPorCuenta(String cuenta, Connection conexionBD) {
		String sql = "SELECT id FROM Usuario WHERE cuenta = ?";
		Usuario devo = null;
		try (PreparedStatement sentencia = conexionBD.prepareStatement(sql)) {
			sentencia.setString(1, cuenta);
			ResultSet rs = sentencia.executeQuery();
			if (rs.next()) {
				int idUsuario = rs.getInt("id");
				devo = Usuario.buscarUsuarioPorId(idUsuario, conexionBD);
			} else {
				throw new SQLException("No existe la cuenta " + cuenta);
			}
		} catch (SQLException sqle) {
			// TODO: handle exception
			System.out.println("Hubo un error en la consulta.\n " + sqle.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Hubo un error al buscar al usuario.\n " + e.getMessage());
		}
		return devo;
	}

	/**
	 * Actualiza los datos generales de un usuario.
	 *
	 * Este método permite modificar toda la información editable asociada a un
	 * usuario existente.
	 *
	 * @param id          identificador del usuario.
	 * @param nombre      nuevo nombre.
	 * @param apellidos   nuevos apellidos.
	 * @param email       nuevo correo electrónico.
	 * @param telefono    nuevo teléfono.
	 * @param cuenta      nuevo nombre de cuenta.
	 * @param contrasenia nueva contraseña.
	 * @param conexionBD  conexión activa contra la base de datos.
	 *
	 * @return true si se actualizó correctamente; false en caso contrario.
	 */
	public static boolean actualizarUsuario(int id, String nombre, String apellidos, String email, String telefono,
			String cuenta, String contrasenia, Connection conexionBD) {

		String sql = """
				UPDATE Usuario
				SET nombre = ?,
				    apellidos = ?,
				    email = ?,
				    telefono = ?,
				    cuenta = ?,
				    contrasenia = ?
				WHERE id = ?
				""";

		try (PreparedStatement ps = conexionBD.prepareStatement(sql)) {

			ps.setString(1, nombre);
			ps.setString(2, apellidos);
			ps.setString(3, email);
			ps.setString(4, telefono);
			ps.setString(5, cuenta);
			ps.setString(6, generarMD5(contrasenia));
			ps.setInt(7, id);

			return ps.executeUpdate() > 0;

		} catch (SQLException e) {
			System.err.println("Error al actualizar usuario: " + e.getMessage());
			return false;
		}
	}

	public static boolean eliminarUsuarioPorId(int id, Connection conexionBD) {
		String sql = "DELETE FROM Usuario WHERE id = ?";

		try (PreparedStatement ps = conexionBD.prepareStatement(sql)) {
			ps.setInt(1, id);
			return ps.executeUpdate() > 0;

		} catch (SQLException e) {
			System.err.println("Error al eliminar usuario: " + e.getMessage());
			return false;
		}
	}
	// </editor-fold>

	/*
	 * Obtener el ID del usuario.
	 *
	 * @return El ID del usuario.
	 */
	public int getId() {
		return id;
	}

	/*
	 * Establecer el ID del usuario.
	 *
	 * @param id El ID del usuario.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/*
	 * Obtener el número de teléfono del usuario.
	 *
	 * @return El número de teléfono del usuario.
	 */
	public String getTelefono() {
		return telefono;
	}

	/*
	 * Establecer el número de teléfono del usuario.
	 *
	 * @param telefono El número de teléfono del usuario.
	 */
	public boolean setTelefono(String telefono) {
		boolean flag = false;
		try {
			this.telefono = telefono;
			flag = true;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error a la hora de establecer el telefono: " + e.getMessage());
		}
		return flag;
	}

	/*
	 * Obtener el nombre del usuario.
	 *
	 * @return El nombre del usuario.
	 */
	public String getNombre() {
		return nombre;
	}

	/*
	 * Establecer el nombre del usuario.
	 *
	 * @param nombre El nombre del usuario.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/*
	 * Obtener los apellidos del usuario.
	 *
	 * @return Los apellidos del usuario.
	 */
	public String getApellidos() {
		return apellidos;
	}

	/*
	 * Establecer los apellidos del usuario.
	 *
	 * @param apellidos Los apellidos del usuario.
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	/*
	 * Obtener el email del usuario.
	 *
	 * @return El email del usuario.
	 */
	public String getEmail() {
		return email;
	}

	/*
	 * Establecer el email del usuario.
	 *
	 * @param email El email del usuario.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/*
	 * Obtener la cuenta del usuario.
	 *
	 * @return La cuenta del usuario.
	 */
	public String getCuenta() {
		return cuenta;
	}

	/*
	 * Establecer la cuenta del usuario.
	 *
	 * @param cuenta La cuenta del usuario.
	 */
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	/*
	 * Obtener la contraseña del usuario.
	 *
	 * @return La contraseña del usuario.
	 */
	public String getContrasenia() {
		return contrasenia;
	}

	/*
	 * Establecer la contraseña del usuario.
	 *
	 * @param contrasenia La contraseña del usuario.
	 */
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	// Metodos auxiliares
	/**
	 * Metodo que devuelve la inicializacion del usuario
	 * 
	 * @param Resultado de la consulta
	 * @return Usuario si se mapea bien, en caso contrario devuelve nulo.
	 * @throws SQLException si ocurre una excepcion a la hora del result set
	 */
	protected static void mapearUsuario(ResultSet rs, Usuario usuario) throws SQLException {
		try {
			usuario.setId(rs.getInt("id"));
			usuario.setNombre(rs.getString("nombre"));
			usuario.setApellidos(rs.getString("apellidos"));
			usuario.setEmail(rs.getString("email"));
			usuario.setTelefono(rs.getString("telefono"));
			usuario.setCuenta(rs.getString("cuenta"));
			usuario.setContrasenia(rs.getString("contrasenia"));
		} catch (Exception e) {
			System.out.println("Error al mapear usuario. " + e.getMessage());
		}
	}

	private static String generarMD5(String texto) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hash = md.digest(texto.getBytes());

			StringBuilder sb = new StringBuilder();

			for (byte b : hash) {
				sb.append(String.format("%02x", b));
			}

			return sb.toString();

		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean validarEmail(String email) {
		if (email == null) {
			return false;
		}

		String patron = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
		return Pattern.matches(patron, email);
	}

	/*
	 * Sobrescritura del método toString() para representar el objeto como una
	 * cadena de texto.
	 *
	 * @return La representación en cadena de texto del objeto Usuario.
	 */
	@Override
	public String toString() {
		return "Usuario [id=" + getId() + ", nombre=" + getNombre()+ ", apellidos=" + getApellidos() + ", email=" + getEmail()
				+ ", telefono=" + getTelefono() + ", cuenta=" + getCuenta() + "]";
	}
}
