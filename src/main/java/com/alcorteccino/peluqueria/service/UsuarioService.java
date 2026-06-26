package com.alcorteccino.peluqueria.service;

import com.alcorteccino.peluqueria.model.Usuario;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.regex.Pattern;
import javax.sql.DataSource;

@Service
public class UsuarioService {

	private final DataSource dataSource;

	public UsuarioService(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Usuario buscarUsuarioPorId(int id) {
		String sql = """
				SELECT id, nombre, apellidos, email, telefono, cuenta, contrasenia
				FROM Usuario
				WHERE id = ?
				""";

		try (Connection conexionBD = dataSource.getConnection();
				PreparedStatement ps = conexionBD.prepareStatement(sql)) {

			ps.setInt(1, id);

			try (ResultSet rs = ps.executeQuery()) {
				if (!rs.next()) {
					return null;
				}

				return mapearUsuario(rs);
			}

		} catch (SQLException e) {
			System.err.println("Error al buscar usuario por ID: " + e.getMessage());
			return null;
		}
	}

	public Usuario buscarUsuarioPorCuenta(String cuenta) {
		String sql = """
				SELECT id, nombre, apellidos, email, telefono, cuenta, contrasenia
				FROM Usuario
				WHERE cuenta = ?
				""";

		try (Connection conexionBD = dataSource.getConnection();
				PreparedStatement ps = conexionBD.prepareStatement(sql)) {

			ps.setString(1, cuenta);

			try (ResultSet rs = ps.executeQuery()) {
				if (!rs.next()) {
					return null;
				}

				return mapearUsuario(rs);
			}

		} catch (SQLException e) {
			System.err.println("Error al buscar usuario por cuenta: " + e.getMessage());
			return null;
		}
	}

	public ArrayList<Usuario> buscarTodosUsuarios() {
		ArrayList<Usuario> usuarios = new ArrayList<>();

		String sql = """
				SELECT id, nombre, apellidos, email, telefono, cuenta, contrasenia
				FROM Usuario
				ORDER BY nombre, apellidos
				""";

		try (Connection conexionBD = dataSource.getConnection();
				PreparedStatement ps = conexionBD.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				usuarios.add(mapearUsuario(rs));
			}

		} catch (SQLException e) {
			System.err.println("Error al buscar usuarios: " + e.getMessage());
		}

		return usuarios;
	}

	public Usuario autenticar(String cuenta, String contrasenia) {
		Usuario usuario = buscarUsuarioPorCuenta(cuenta);

		if (usuario == null) {
			return null;
		}

		String contraseniaMD5 = generarMD5(contrasenia);

		if (!contraseniaMD5.equals(usuario.getContrasenia())) {
			return null;
		}

		return usuario;
	}

	public boolean actualizarUsuario(Usuario usuario) {
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

		try (Connection conexionBD = dataSource.getConnection();
				PreparedStatement ps = conexionBD.prepareStatement(sql)) {

			ps.setString(1, usuario.getNombre());
			ps.setString(2, usuario.getApellidos());
			ps.setString(3, usuario.getEmail());
			ps.setString(4, usuario.getTelefono());
			ps.setString(5, usuario.getCuenta());
			ps.setString(6, generarMD5(usuario.getContrasenia()));
			ps.setInt(7, usuario.getId());

			return ps.executeUpdate() > 0;

		} catch (SQLException e) {
			System.err.println("Error al actualizar usuario: " + e.getMessage());
			return false;
		}
	}

	public boolean actualizarUsuario(Usuario usuario, Connection conexionBD) {
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
			ps.setString(1, usuario.getNombre());
			ps.setString(2, usuario.getApellidos());
			ps.setString(3, usuario.getEmail());
			ps.setString(4, usuario.getTelefono());
			ps.setString(5, usuario.getCuenta());
			ps.setString(6, usuario.getContrasenia());
			ps.setInt(7, usuario.getId());

			return ps.executeUpdate() > 0;

		} catch (SQLException e) {
			System.err.println("Error al actualizar usuario: " + e.getMessage());
			return false;
		}
	}

	public boolean actualizarDatosSinContrasenia(Usuario usuario) {
		String sql = """
				UPDATE Usuario
				SET nombre = ?,
				    apellidos = ?,
				    email = ?,
				    telefono = ?,
				    cuenta = ?
				WHERE id = ?
				""";

		try (Connection conexionBD = dataSource.getConnection();
				PreparedStatement ps = conexionBD.prepareStatement(sql)) {

			ps.setString(1, usuario.getNombre());
			ps.setString(2, usuario.getApellidos());
			ps.setString(3, usuario.getEmail());
			ps.setString(4, usuario.getTelefono());
			ps.setString(5, usuario.getCuenta());
			ps.setInt(6, usuario.getId());

			return ps.executeUpdate() > 0;

		} catch (SQLException e) {
			System.err.println("Error al actualizar datos del usuario: " + e.getMessage());
			return false;
		}
	}

	public boolean eliminarUsuarioPorId(int id) {
		String sql = """
				DELETE FROM Usuario
				WHERE id = ?
				""";

		try (Connection conexionBD = dataSource.getConnection();
				PreparedStatement ps = conexionBD.prepareStatement(sql)) {

			ps.setInt(1, id);
			return ps.executeUpdate() > 0;

		} catch (SQLException e) {
			System.err.println("Error al eliminar usuario: " + e.getMessage());
			return false;
		}
	}

	public boolean validarEmail(String email) {
		if (email == null) {
			return false;
		}

		String patron = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
		return Pattern.matches(patron, email);
	}

	private Usuario mapearUsuario(ResultSet rs) throws SQLException {
	    Usuario usuario = new Usuario();
	    mapearDatosUsuario(rs, usuario);
	    return usuario;
	}

	public void mapearDatosUsuario(ResultSet rs, Usuario usuario) throws SQLException {
	    usuario.setId(rs.getInt("id"));
	    usuario.setNombre(rs.getString("nombre"));
	    usuario.setApellidos(rs.getString("apellidos"));
	    usuario.setEmail(rs.getString("email"));
	    usuario.setTelefono(rs.getString("telefono"));
	    usuario.setCuenta(rs.getString("cuenta"));
	    usuario.setContrasenia(rs.getString("contrasenia"));
	}

	private String generarMD5(String texto) {
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
}