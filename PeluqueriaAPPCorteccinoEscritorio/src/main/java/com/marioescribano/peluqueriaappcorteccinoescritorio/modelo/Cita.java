package com.marioescribano.peluqueriaappcorteccinoescritorio.modelo;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Cita {

    private int id;
    private Cliente cliente;
    private Personal empleado;
    private Horario horario;
    private String estado;
    private LocalDateTime fechaCreacion;
    private String observaciones;

    public Cita() {
    }

    public Cita(int id, Cliente cliente, Personal empleado, Horario horario,
                String estado, LocalDateTime fechaCreacion, String observaciones) {
        this.id = id;
        this.cliente = cliente;
        this.empleado = empleado;
        this.horario = horario;
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


    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }


    public Personal getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Personal empleado) {
        this.empleado = empleado;
    }


    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
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

    public static Cita buscarCitaPorId(int idCita, Connection conexionBD) {
        String sql = """
            SELECT id, cliente_id, empleado_id, horario_id, estado, fecha_creacion, observaciones
            FROM cita
            WHERE id = ?
            """;

        try (PreparedStatement ps = conexionBD.prepareStatement(sql)) {
            ps.setInt(1, idCita);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                return mapearCita(rs, conexionBD);
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar cita por ID: " + e.getMessage());
            return null;
        }
    }

    public static ArrayList<Cita> buscarCitasPorCliente(int clienteId, Connection conexionBD) {
        ArrayList<Cita> citas = new ArrayList<>();

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
                    citas.add(mapearCita(rs, conexionBD));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar citas por cliente: " + e.getMessage());
        }

        return citas;
    }

    public static ArrayList<Cita> buscarCitasPorEmpleado(int empleadoId, Connection conexionBD) {
        ArrayList<Cita> citas = new ArrayList<>();

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
                    citas.add(mapearCita(rs, conexionBD));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar citas por empleado: " + e.getMessage());
        }

        return citas;
    }

    public static boolean crearCita(Cliente cliente, Personal empleado, Horario horario,
                                   String observaciones, Connection conexionBD) {

        if (cliente == null || empleado == null || horario == null) {
            return false;
        }

        String sqlInsert = """
            INSERT INTO cita
            (cliente_id, empleado_id, horario_id, estado, fecha_creacion, observaciones)
            VALUES (?, ?, ?, 'PENDIENTE', NOW(), ?)
            """;

        try (PreparedStatement ps = conexionBD.prepareStatement(sqlInsert)) {
            ps.setInt(1, cliente.getId());
            ps.setInt(2, empleado.getId());
            ps.setInt(3, horario.getId());
            ps.setString(4, observaciones);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al crear cita: " + e.getMessage());
            return false;
        }
    }

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

    public static boolean cancelarCita(int idCita, Connection conexionBD) {
        return actualizarEstado(idCita, "CANCELADA", conexionBD);
    }

    private static Cita mapearCita(ResultSet rs, Connection conexionBD) throws SQLException {
        int clienteId = rs.getInt("cliente_id");
        int empleadoId = rs.getInt("empleado_id");
        int horarioId = rs.getInt("horario_id");

        Cliente cliente = Cliente.obtenerFiltradosClientes(clienteId, conexionBD);
        Personal empleado = Personal.obtenerPersonalPorId(empleadoId, conexionBD);
        Horario horario = Horario.buscarHorarioPorId(horarioId, conexionBD);

        return new Cita(
                rs.getInt("id"),
                cliente,
                empleado,
                horario,
                rs.getString("estado"),
                rs.getTimestamp("fecha_creacion").toLocalDateTime(),
                rs.getString("observaciones")
        );
    }

    @Override
    public String toString() {
        return "Cita [id=" + id +
                ", cliente=" + cliente +
                ", empleado=" + empleado +
                ", horario=" + horario +
                ", estado=" + estado +
                ", fechaCreacion=" + fechaCreacion +
                ", observaciones=" + observaciones +
                "]";
    }
}