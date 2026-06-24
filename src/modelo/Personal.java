package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

		String sql = """
				SELECT tipo, salario_personal
				FROM Perfiles_Usuario
				WHERE usuario_id = ?
				  AND tipo <> 'CLIENTE'
				""";

		try (PreparedStatement ps = conexionBD.prepareStatement(sql)) {
			ps.setInt(1, idPersonal);

			try (ResultSet rs = ps.executeQuery()) {
				if (!rs.next()) {
					return null;
				}

				Personal personal = new Personal();

				personal.setId(usuario.getId());
				personal.setNombre(usuario.getNombre());
				personal.setApellidos(usuario.getApellidos());
				personal.setEmail(usuario.getEmail());
				personal.setTelefono(usuario.getTelefono());
				personal.setCuenta(usuario.getCuenta());
				personal.setContrasenia(usuario.getContrasenia());

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