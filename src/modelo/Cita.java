package modelo;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Cita {

	private int id;
	private int clienteId;
	private int empleadoId;
	private int horarioId;
	private String estado;
	private LocalDateTime fechaCreacion;
	private String observaciones;

	/**
	 * Representa una reserva realizada por un cliente para un horario y empleado
	 * determinados.
	 *
	 * Una cita puede encontrarse en diferentes estados:
	 *
	 * <ul>
	 * <li>PENDIENTE</li>
	 * <li>CONFIRMADA</li>
	 * <li>COMPLETADA</li>
	 * <li>CANCELADA</li>
	 * </ul>
	 *
	 * La información se almacena en la tabla {@code cita}.
	 *
	 * @author Mario
	 * @version 1.0.3
	 */
	public Cita() {
	}

	public Cita(int id, int clienteId, int empleadoId, int horarioId, String estado, LocalDateTime fechaCreacion,
			String observaciones) {
		this.id = id;
		this.clienteId = clienteId;
		this.empleadoId = empleadoId;
		this.horarioId = horarioId;
		this.estado = estado;
		this.fechaCreacion = fechaCreacion;
		this.observaciones = observaciones;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getClienteId() {
		return clienteId;
	}

	public void setClienteId(int clienteId) {
		this.clienteId = clienteId;
	}

	public int getEmpleadoId() {
		return empleadoId;
	}

	public void setEmpleadoId(int empleadoId) {
		this.empleadoId = empleadoId;
	}

	public int getHorarioId() {
		return horarioId;
	}

	public void setHorarioId(int horarioId) {
		this.horarioId = horarioId;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	/**
	 * Busca una cita mediante su identificador único.
	 *
	 * @param idCita     identificador de la cita.
	 * @param conexionBD conexión activa contra la base de datos.
	 *
	 * @return la cita encontrada o {@code null} si no existe.
	 */
	public static Cita buscarCitaPorId(int idCita, Connection conexionBD) {
		String sql = """
				SELECT id, cliente_id, empleado_id, horario_id, estado, fecha_creacion, observaciones
				FROM cita
				WHERE id = ?
				""";

		try (PreparedStatement ps = conexionBD.prepareStatement(sql)) {
			ps.setInt(1, idCita);

			try (ResultSet rs = ps.executeQuery()) {
				if (!rs.next())
					return null;
				return mapearCita(rs);
			}

		} catch (SQLException e) {
			System.err.println("Error al buscar cita por ID: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Recupera todas las citas asociadas a un cliente.
	 *
	 * Los resultados se devuelven ordenados por fecha de creación en orden
	 * descendente.
	 *
	 * @param clienteId  identificador del cliente.
	 * @param conexionBD conexión activa contra la base de datos.
	 *
	 * @return lista de citas asociadas al cliente.
	 */
	public static List<Cita> buscarCitasPorCliente(int clienteId, Connection conexionBD) {
		List<Cita> citas = new ArrayList<>();

		String sql = """
				SELECT id, cliente_id, empleado_id, horario_id, estado, fecha_creacion, observaciones
				FROM cita
				WHERE cliente_id = ?
				ORDER BY fecha_creacion DESC
				""";

		try (PreparedStatement ps = conexionBD.prepareStatement(sql)) {
			ps.setInt(1, clienteId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					citas.add(mapearCita(rs));
				}
			}

		} catch (SQLException e) {
			System.err.println("Error al buscar citas por cliente: " + e.getMessage());
		}

		return citas;
	}

	/**
	 * Recupera todas las citas asignadas a un empleado.
	 *
	 * Los resultados se devuelven ordenados por fecha de creación en orden
	 * descendente.
	 *
	 * @param empleadoId identificador del empleado.
	 * @param conexionBD conexión activa contra la base de datos.
	 *
	 * @return lista de citas asociadas al empleado.
	 */
	public static List<Cita> buscarCitasPorEmpleado(int empleadoId, Connection conexionBD) {
		List<Cita> citas = new ArrayList<>();

		String sql = """
				SELECT id, cliente_id, empleado_id, horario_id, estado, fecha_creacion, observaciones
				FROM cita
				WHERE empleado_id = ?
				ORDER BY fecha_creacion DESC
				""";

		try (PreparedStatement ps = conexionBD.prepareStatement(sql)) {
			ps.setInt(1, empleadoId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					citas.add(mapearCita(rs));
				}
			}

		} catch (SQLException e) {
			System.err.println("Error al buscar citas por empleado: " + e.getMessage());
		}

		return citas;
	}

	/**
	 * Registra una nueva cita en el sistema.
	 *
	 * La cita se crea inicialmente con estado {@code PENDIENTE}.
	 *
	 * Este método únicamente crea el registro de la cita. La actualización de
	 * disponibilidad del horario debe gestionarse mediante una transacción
	 * independiente.
	 *
	 * @param clienteId     identificador del cliente.
	 * @param empleadoId    identificador del empleado.
	 * @param horarioId     identificador del horario reservado.
	 * @param observaciones observaciones adicionales.
	 * @param conexionBD    conexión activa contra la base de datos.
	 *
	 * @return {@code true} si la inserción fue realizada correctamente;
	 *         {@code false} en caso contrario.
	 */
	public static boolean crearCita(int clienteId, int empleadoId, int horarioId, String observaciones,
			Connection conexionBD) {

		String sqlInsert = """
				INSERT INTO cita
				(cliente_id, empleado_id, horario_id, estado, fecha_creacion, observaciones)
				VALUES (?, ?, ?, 'PENDIENTE', NOW(), ?)
				""";

		try (PreparedStatement ps = conexionBD.prepareStatement(sqlInsert)) {
			ps.setInt(1, clienteId);
			ps.setInt(2, empleadoId);
			ps.setInt(3, horarioId);
			ps.setString(4, observaciones);

			return ps.executeUpdate() > 0;

		} catch (SQLException e) {
			System.err.println("Error al crear cita: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Modifica el estado de una cita existente.
	 *
	 * Los estados válidos son los definidos por el campo ENUM de la tabla
	 * {@code cita}.
	 *
	 * @param idCita      identificador de la cita.
	 * @param nuevoEstado nuevo estado a asignar.
	 * @param conexionBD  conexión activa contra la base de datos.
	 *
	 * @return {@code true} si la actualización tuvo éxito; {@code false} en caso
	 *         contrario.
	 */
	public static boolean actualizarEstado(int idCita, String nuevoEstado, Connection conexionBD) {
		String sql = """
				UPDATE cita
				SET estado = ?
				WHERE id = ?
				""";

		try (PreparedStatement ps = conexionBD.prepareStatement(sql)) {
			ps.setString(1, nuevoEstado);
			ps.setInt(2, idCita);

			return ps.executeUpdate() > 0;

		} catch (SQLException e) {
			System.err.println("Error al actualizar estado de cita: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Cancela una cita existente.
	 *
	 * Internamente establece el estado de la cita a {@code CANCELADA}.
	 *
	 * @param idCita     identificador de la cita.
	 * @param conexionBD conexión activa contra la base de datos.
	 *
	 * @return {@code true} si la operación se realizó correctamente; {@code false}
	 *         en caso contrario.
	 */
	public static boolean cancelarCita(int idCita, Connection conexionBD) {
		return actualizarEstado(idCita, "CANCELADA", conexionBD);
	}

	private static Cita mapearCita(ResultSet rs) throws SQLException {
		return new Cita(rs.getInt("id"), rs.getInt("cliente_id"), rs.getInt("empleado_id"), rs.getInt("horario_id"),
				rs.getString("estado"), rs.getTimestamp("fecha_creacion").toLocalDateTime(),
				rs.getString("observaciones"));
	}

	@Override
	public String toString() {
		return "Cita [id=" + id + ", clienteId=" + clienteId + ", empleadoId=" + empleadoId + ", horarioId=" + horarioId
				+ ", estado=" + estado + ", fechaCreacion=" + fechaCreacion + ", observaciones=" + observaciones + "]";
	}
}