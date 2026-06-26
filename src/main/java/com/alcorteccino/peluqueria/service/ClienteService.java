package com.alcorteccino.peluqueria.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;

import com.alcorteccino.peluqueria.model.Cliente;

@Service
public class ClienteService {

    private final DataSource dataSource;
    private final UsuarioService usuarioService;

    public ClienteService(DataSource dataSource, UsuarioService usuarioService) {
        this.dataSource = dataSource;
        this.usuarioService = usuarioService;
    }

    /**
     * Obtiene todos los clientes registrados en el sistema.
     *
     * @return lista de clientes. Si no existen registros, devuelve una lista vacía.
     */
    public ArrayList<Cliente> buscarTodosClientes() {
        ArrayList<Cliente> clientes = new ArrayList<>();

        String sql = """
            SELECT u.id, u.nombre, u.apellidos, u.email, u.telefono, u.cuenta, u.contrasenia,
                   p.categoria_cliente
            FROM Usuario u
            INNER JOIN Perfiles_Usuario p ON u.id = p.usuario_id
            WHERE p.tipo = 'CLIENTE'
            ORDER BY u.nombre, u.apellidos
            """;

        try (Connection conexionBD = dataSource.getConnection();
             PreparedStatement ps = conexionBD.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente();

                usuarioService.mapearDatosUsuario(rs, cliente);
                cliente.setCategoria(rs.getString("categoria_cliente"));

                clientes.add(cliente);
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar clientes: " + e.getMessage());
        }

        return clientes;
    }

    /**
     * Obtiene un cliente a partir de su identificador.
     *
     * @param idCliente identificador del cliente.
     *
     * @return cliente encontrado o null si no existe o no tiene perfil CLIENTE.
     */
    public Cliente obtenerClientePorId(int idCliente) {
        String sql = """
            SELECT u.id, u.nombre, u.apellidos, u.email, u.telefono, u.cuenta, u.contrasenia,
                   p.categoria_cliente
            FROM Usuario u
            INNER JOIN Perfiles_Usuario p ON u.id = p.usuario_id
            WHERE u.id = ?
              AND p.tipo = 'CLIENTE'
            """;

        try (Connection conexionBD = dataSource.getConnection();
             PreparedStatement ps = conexionBD.prepareStatement(sql)) {

            ps.setInt(1, idCliente);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                Cliente cliente = new Cliente();

                usuarioService.mapearDatosUsuario(rs, cliente);
                cliente.setCategoria(rs.getString("categoria_cliente"));

                return cliente;
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener cliente por ID: " + e.getMessage());
            return null;
        }
    }

    /**
     * Actualiza los datos generales del usuario y la categoría específica del cliente.
     *
     * @param cliente cliente con los datos actualizados.
     *
     * @return true si se actualizaron correctamente los datos.
     */
    public boolean actualizarCliente(Cliente cliente) {
        String sqlPerfil = """
            UPDATE Perfiles_Usuario
            SET categoria_cliente = ?
            WHERE usuario_id = ?
              AND tipo = 'CLIENTE'
            """;

        try (Connection conexionBD = dataSource.getConnection()) {
            conexionBD.setAutoCommit(false);

            boolean usuarioActualizado = usuarioService.actualizarUsuario(cliente, conexionBD);

            try (PreparedStatement ps = conexionBD.prepareStatement(sqlPerfil)) {
                ps.setString(1, cliente.getCategoria());
                ps.setInt(2, cliente.getId());

                boolean perfilActualizado = ps.executeUpdate() > 0;

                if (usuarioActualizado && perfilActualizado) {
                    conexionBD.commit();
                    return true;
                }

                conexionBD.rollback();
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }
}