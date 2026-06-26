package com.alcorteccino.peluqueria.service;

import java.sql.*;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;

import com.alcorteccino.peluqueria.model.Personal;

@Service
public class PersonalService {

    private final DataSource dataSource;
    private final UsuarioService usuarioService;

    public PersonalService(DataSource dataSource, UsuarioService usuarioService) {
        this.dataSource = dataSource;
        this.usuarioService = usuarioService;
    }

    public ArrayList<Personal> buscarTodoElPersonal() {
        ArrayList<Personal> personalLista = new ArrayList<>();

        String sql = """
            SELECT u.id, u.nombre, u.apellidos, u.email, u.telefono, u.cuenta, u.contrasenia,
                   p.tipo, p.salario_personal
            FROM Usuario u
            INNER JOIN Perfiles_Usuario p ON u.id = p.usuario_id
            WHERE p.tipo <> 'CLIENTE'
            ORDER BY p.salario_personal DESC
            """;

        try (Connection conexionBD = dataSource.getConnection();
             PreparedStatement ps = conexionBD.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Personal personal = new Personal();

                usuarioService.mapearDatosUsuario(rs, personal);

                personal.setTipo(rs.getString("tipo"));
                personal.setSalario(rs.getDouble("salario_personal"));

                personalLista.add(personal);
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar personal: " + e.getMessage());
        }

        return personalLista;
    }

    public Personal obtenerPersonalPorId(int idPersonal) {
        String sql = """
            SELECT u.id, u.nombre, u.apellidos, u.email, u.telefono, u.cuenta, u.contrasenia,
                   p.tipo, p.salario_personal
            FROM Usuario u
            INNER JOIN Perfiles_Usuario p ON u.id = p.usuario_id
            WHERE u.id = ?
              AND p.tipo <> 'CLIENTE'
            """;

        try (Connection conexionBD = dataSource.getConnection();
             PreparedStatement ps = conexionBD.prepareStatement(sql)) {

            ps.setInt(1, idPersonal);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                Personal personal = new Personal();

                usuarioService.mapearDatosUsuario(rs, personal);

                personal.setTipo(rs.getString("tipo"));
                personal.setSalario(rs.getDouble("salario_personal"));

                return personal;
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener personal por ID: " + e.getMessage());
            return null;
        }
    }

    public boolean actualizarPersonal(Personal personal) {
        String sqlPerfil = """
            UPDATE Perfiles_Usuario
            SET tipo = ?,
                salario_personal = ?
            WHERE usuario_id = ?
              AND tipo <> 'CLIENTE'
            """;

        try (Connection conexionBD = dataSource.getConnection()) {
            conexionBD.setAutoCommit(false);

            boolean usuarioActualizado = usuarioService.actualizarUsuario(personal, conexionBD);

            try (PreparedStatement ps = conexionBD.prepareStatement(sqlPerfil)) {
                ps.setString(1, personal.getTipo());
                ps.setDouble(2, personal.getSalario());
                ps.setInt(3, personal.getId());

                boolean perfilActualizado = ps.executeUpdate() > 0;

                if (usuarioActualizado && perfilActualizado) {
                    conexionBD.commit();
                    return true;
                }

                conexionBD.rollback();
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Error al actualizar personal: " + e.getMessage());
            return false;
        }
    }
}