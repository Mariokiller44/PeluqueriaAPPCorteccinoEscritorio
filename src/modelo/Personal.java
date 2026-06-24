package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Representa a un miembro del personal de la peluquería.
 *
 * Incluye la información específica relacionada con el puesto desempeñado y el
 * salario asociado.
 *
 * Los datos específicos del personal se almacenan en la tabla Perfiles_Usuario.
 *
 * @author Mario
 * @version 1.0.3
 */
public class Personal extends Usuario {

	private double salario;
	private String tipo;

	public Personal() {
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Metodo que busca todos los empleados en el sistema.
	 * @param conexionBD
	 * @return el listado con todos los empleados, en caso contrario o de un fallo devolvera null
	 */
	public static ArrayList<Personal> buscarTodoElPersonal(Connection conexionBD) {
		ArrayList<Personal> personalLista = new ArrayList<>();

		String sql = """
				SELECT u.id, u.nombre, u.apellidos, u.email, u.telefono, u.cuenta, u.contrasenia,
				       p.tipo, p.salario_personal
				FROM Usuario u
				INNER JOIN Perfiles_Usuario p ON u.id = p.usuario_id
				WHERE p.tipo <> 'CLIENTE'
				""";

		try (PreparedStatement ps = conexionBD.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				Personal personal = new Personal();

				mapearUsuario(rs, personal);
				personal.setTipo(rs.getString("tipo"));
				personal.setSalario(rs.getDouble("salario_personal"));

				personalLista.add(personal);
			}

		} catch (SQLException e) {
			System.err.println("Error al buscar personal: " + e.getMessage());
		}

		return personalLista;
	}

	/**
	 * Recupera un empleado a partir de su identificador.
	 *
	 * Este método obtiene los datos generales del usuario y la información
	 * específica del personal, incluyendo tipo de puesto y salario.
	 *
	 * @param idPersonal identificador del empleado.
	 * @param conexionBD conexión activa contra la base de datos.
	 *
	 * @return el empleado encontrado o null si no existe.
	 */
	public static Personal obtenerPersonalPorId(int idPersonal, Connection conexionBD) {
		Usuario usuario = Usuario.buscarUsuarioPorId(idPersonal, conexionBD);

		if (usuario == null) {
			return null;
		}

		String sql = "SELECT u.id, u.nombre, u.apellidos, u.email, u.telefono, u.cuenta, u.contrasenia, p.tipo, p.salario_personal FROM Usuario u INNER JOIN Perfiles_Usuario p ON u.id = p.usuario_id WHERE  usuario_id = ? AND tipo <> 'CLIENTE'";

		try (PreparedStatement ps = conexionBD.prepareStatement(sql)) {
			ps.setInt(1, idPersonal);

			try (ResultSet rs = ps.executeQuery()) {
				if (!rs.next()) {
					return null;
				}

				Personal personal = new Personal();
				mapearUsuario(rs, personal);
				personal.setTipo(rs.getString("tipo"));
				personal.setSalario(rs.getDouble("salario_personal"));

				return personal;
			}

		} catch (SQLException e) {
			System.err.println("Error al obtener personal por ID: " + e.getMessage());
			return null;
		}
	}

	@Override
	public String toString() {
		return super.toString() + ", tipo: " + tipo + ", salario: " + salario;
	}
}