package modelo;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa una franja horaria asignada a un empleado para la realización de
 * un servicio determinado.
 *
 * Un horario puede estar disponible o reservado mediante una cita.
 *
 * La información se almacena en la tabla {@code horario}.
 *
 * @author Mario
 * @version 1.0.3
 */
public class Horario {

	private int id;
	private LocalDateTime fechaHora;
	private int usuarioId;
	private int servicioId;
	private int duracionMinutos;
	private boolean disponible;

	public Horario() {
	}

	public Horario(int id, LocalDateTime fechaHora, int usuarioId, int servicioId, int duracionMinutos,
			boolean disponible) {
		this.id = id;
		this.fechaHora = fechaHora;
		this.usuarioId = usuarioId;
		this.servicioId = servicioId;
		this.duracionMinutos = duracionMinutos;
		this.disponible = disponible;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public int getServicioId() {
		return servicioId;
	}

	public void setServicioId(int servicioId) {
		this.servicioId = servicioId;
	}

	public int getDuracionMinutos() {
		return duracionMinutos;
	}

	public void setDuracionMinutos(int duracionMinutos) {
		this.duracionMinutos = duracionMinutos;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	/**
	 * Recupera un horario concreto a partir de su identificador.
	 *
	 * @param idHorario  identificador único del horario.
	 * @param conexionBD conexión activa contra la base de datos.
	 *
	 * @return el horario encontrado o {@code null} si no existe.
	 */
	public static Horario buscarHorarioPorId(int idHorario, Connection conexionBD) {
		String sql = """
				SELECT id, fecha_hora, usuario_id, servicio_id, duracion_minutos, disponible
				FROM horario
				WHERE id = ?
				""";

		try (PreparedStatement ps = conexionBD.prepareStatement(sql)) {
			ps.setInt(1, idHorario);

			try (ResultSet rs = ps.executeQuery()) {
				if (!rs.next())
					return null;
				return mapearHorario(rs);
			}

		} catch (SQLException e) {
			System.err.println("Error al buscar horario por ID: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Obtiene todos los horarios que actualmente pueden ser reservados.
	 *
	 * Un horario disponible es aquel cuyo campo {@code disponible} tiene valor
	 * verdadero en la base de datos.
	 *
	 * @param conexionBD conexión activa contra la base de datos.
	 *
	 * @return lista de horarios disponibles. Si no existen resultados, devuelve una
	 *         lista vacía.
	 */
	public static List<Horario> buscarHorariosDisponibles(Connection conexionBD) {
		List<Horario> horarios = new ArrayList<>();

		String sql = """
				SELECT id, fecha_hora, usuario_id, servicio_id, duracion_minutos, disponible
				FROM horario
				WHERE disponible = 1
				ORDER BY fecha_hora
				""";

		try (PreparedStatement ps = conexionBD.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				horarios.add(mapearHorario(rs));
			}

		} catch (SQLException e) {
			System.err.println("Error al buscar horarios disponibles: " + e.getMessage());
		}

		return horarios;
	}

	/**
	 * Obtiene todos los horarios asociados a un empleado concreto.
	 *
	 * Los resultados se devuelven ordenados cronológicamente.
	 *
	 * @param usuarioId  identificador del empleado.
	 * @param conexionBD conexión activa contra la base de datos.
	 *
	 * @return lista de horarios pertenecientes al empleado.
	 */
	public static ArrayList<Horario> buscarHorariosPorPersonal(int usuarioId, Connection conexionBD) {
		ArrayList<Horario> horarios = new ArrayList<>();

		String sql = """
				SELECT id, fecha_hora, usuario_id, servicio_id, duracion_minutos, disponible
				FROM horario
				WHERE usuario_id = ?
				ORDER BY fecha_hora
				""";

		try (PreparedStatement ps = conexionBD.prepareStatement(sql)) {
			ps.setInt(1, usuarioId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					horarios.add(mapearHorario(rs));
				}
			}

		} catch (SQLException e) {
			System.err.println("Error al buscar horarios por personal: " + e.getMessage());
		}

		return horarios;
	}

	/**
	 * Actualiza completamente la información de un horario existente.
	 *
	 * Este método sobrescribe todos los atributos editables del horario.
	 *
	 * @param idHorario       identificador del horario a modificar.
	 * @param fechaHora       nueva fecha y hora.
	 * @param usuarioId       nuevo empleado asignado.
	 * @param servicioId      nuevo servicio asignado.
	 * @param duracionMinutos duración del servicio en minutos.
	 * @param disponible      estado de disponibilidad.
	 * @param conexionBD      conexión activa contra la base de datos.
	 *
	 * @return {@code true} si se modificó al menos un registro; {@code false} en
	 *         caso contrario.
	 */
	public static boolean actualizarHorario(int idHorario, LocalDateTime fechaHora, int usuarioId, int servicioId,
			int duracionMinutos, boolean disponible, Connection conexionBD) {

		String sql = """
				UPDATE horario
				SET fecha_hora = ?,
				    usuario_id = ?,
				    servicio_id = ?,
				    duracion_minutos = ?,
				    disponible = ?
				WHERE id = ?
				""";

		try (PreparedStatement ps = conexionBD.prepareStatement(sql)) {
			ps.setTimestamp(1, Timestamp.valueOf(fechaHora));
			ps.setInt(2, usuarioId);
			ps.setInt(3, servicioId);
			ps.setInt(4, duracionMinutos);
			ps.setBoolean(5, disponible);
			ps.setInt(6, idHorario);

			return ps.executeUpdate() > 0;

		} catch (SQLException e) {
			System.err.println("Error al actualizar horario: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Modifica únicamente el estado de disponibilidad de un horario.
	 *
	 * Es especialmente útil durante los procesos de reserva y cancelación de citas.
	 *
	 * @param idHorario  identificador del horario.
	 * @param disponible nuevo estado.
	 * @param conexionBD conexión activa contra la base de datos.
	 *
	 * @return {@code true} si la actualización tuvo éxito; {@code false} en caso
	 *         contrario.
	 */
	public static boolean marcarDisponibilidad(int idHorario, boolean disponible, Connection conexionBD) {
		String sql = """
				UPDATE horario
				SET disponible = ?
				WHERE id = ?
				""";

		try (PreparedStatement ps = conexionBD.prepareStatement(sql)) {
			ps.setBoolean(1, disponible);
			ps.setInt(2, idHorario);

			return ps.executeUpdate() > 0;

		} catch (SQLException e) {
			System.err.println("Error al modificar disponibilidad: " + e.getMessage());
			return false;
		}
	}

	private static Horario mapearHorario(ResultSet rs) throws SQLException {
		return new Horario(rs.getInt("id"), rs.getTimestamp("fecha_hora").toLocalDateTime(), rs.getInt("usuario_id"),
				rs.getInt("servicio_id"), rs.getInt("duracion_minutos"), rs.getBoolean("disponible"));
	}

	@Override
	public String toString() {
		return "Horario [id=" + id + ", fechaHora=" + fechaHora + ", usuarioId=" + usuarioId + ", servicioId="
				+ servicioId + ", duracionMinutos=" + duracionMinutos + ", disponible=" + disponible + "]";
	}
}