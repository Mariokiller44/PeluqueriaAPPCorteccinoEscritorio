/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.Horario;
import modelo.Personal;
import modelo.Servicio;
import modelo.Usuario;
import modelo.Cita;

/**
 *
 * @author Administrador
 */
public class ConsultasPersonal {

    private Connection con;

    public ArrayList<String> obtenerHorariosConCita(int id) {
        ArrayList<String> horariosConCita = new ArrayList<>();
        try {
            realizarConexion();
            String mostrarCitas = "SELECT cita.id, horario.fecha_es AS fecha, horario.hora_es AS hora, servicios.descripcion, servicios.precio, CONCAT(usuario.nombre, ' ', usuario.apellidos) AS cliente FROM horario JOIN personal ON horario.id_personal = personal.id JOIN servicios ON horario.id_servicio = servicios.id LEFT JOIN cita ON horario.id = cita.ID_HORARIO LEFT JOIN usuario ON cita.ID_CLIENTE = usuario.ID WHERE personal.id = ? AND cita.id IS NOT NULL;";
            PreparedStatement stmt = con.prepareStatement(mostrarCitas);
            stmt.setInt(1, id);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                String fecha = resultado.getString("fecha");
                String hora = resultado.getString("hora");
                String descripcion = resultado.getString("descripcion");
                String precio = resultado.getString("precio");
                String cliente = resultado.getString("cliente");
                String horarioConCita = "Fecha: " + fecha + ", Hora: " + hora + ", Descripción: " + descripcion + ", Precio: " + precio + ", Cliente: " + cliente;
                horariosConCita.add(horarioConCita);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo hacer la consulta");
        }
        return horariosConCita;
    }

    public ArrayList<Horario> consultarHorarios(int idCliente) {
        ArrayList<Horario> horarios = new ArrayList<>();
        try {
            realizarConexion();
            String sql = "SELECT horario.id, horario.fecha_es AS fecha, horario.hora_es AS hora, servicios.descripcion, servicios.precio, CONCAT(usuario.nombre, ' ', usuario.apellidos) AS empleado\n"
                    + "FROM horario\n"
                    + "LEFT JOIN cita ON horario.id = cita.id_horario\n"
                    + "LEFT JOIN usuario ON cita.id_cliente = usuario.id\n"
                    + "JOIN servicios ON horario.id_servicio = servicios.id\n"
                    + "WHERE cita.id_cliente = ? OR cita.id_cliente IS NULL;";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String fecha = rs.getString("fecha");
                String hora = rs.getString("hora");
                String descripcion = rs.getString("descripcion");
                String precio = rs.getString("precio");
                String empleado = rs.getString("empleado");
                Horario horario = new Horario(id, fecha, hora, descripcion, precio, empleado);
                horarios.add(horario);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo hacer la consulta de horarios");
        }
        return horarios;
    }

    public Cita actualizarCita(Cita citaAnt, Cita citaNueva) {
        realizarConexion();
        String consultarIdCita = "UPDATE ";

        return citaNueva;
    }

    public String[] consultarServicios(Cita cita) {
        return null;
    }

    public ArrayList<String> serviciosBD() {
        ArrayList<String> resul = new ArrayList<>();
        try {
            realizarConexion();

            String sql = "SELECT DESCRIPCION, PRECIO FROM SERVICIOS";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                String descripcion = res.getString("DESCRIPCION");
                String precio = String.valueOf(res.getFloat("PRECIO"));
                String servicio = "Descripcion: " + descripcion + ", precio: " + precio;
                resul.add(servicio);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al coger los servicios");
        }
        return resul;
    }

    public int cogerIdServicio(String servicio) {
        int id = -1;
        try {
            realizarConexion();
            String descripcion = servicio.substring(13, servicio.indexOf(","));
            String precio = servicio.substring(servicio.indexOf(',') + 10, servicio.length());
            String sql = "SELECT ID FROM SERVICIOS WHERE DESCRIPCION=? AND PRECIO =?";
            PreparedStatement ps = con.prepareStatement(sql);
            float prize = Float.parseFloat(precio);
            ps.setString(1, descripcion);
            ps.setFloat(2, prize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt("ID");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error intentando cargar el servicio");
        }
        return id;
    }

    public void modificarServicio(int id, int fecha) {
        try {
            realizarConexion();
            String actualizacion = "UPDATE HORARIO SET ID_SERVICIO=? WHERE ID LIKE ?";
            PreparedStatement ps = con.prepareStatement(actualizacion);
            ps.setInt(1, id);
            ps.setInt(2, fecha);
            int result = ps.executeUpdate();
            if (result == 0) {
                JOptionPane.showMessageDialog(null, "No se ha podido modificar el servicio, vuelva a intentarlo");
            } else {
                JOptionPane.showMessageDialog(null, "Servicio modificado exitosamente");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error intentando modificar el servicio");
        }
    }

    public void modificarServicioPorFecha(String fecha, int idCita) {
        try {
            realizarConexion();
            String actualizacion = "UPDATE horario SET fecha=? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(actualizacion);
            ps.setString(1, fecha);
            ps.setInt(2, idCita);
            int result = ps.executeUpdate();
            if (result == 0) {
                JOptionPane.showMessageDialog(null, "No se ha podido modificar la fecha, vuelva a intentarlo");
            } else {
                JOptionPane.showMessageDialog(null, "Fecha modificada exitosamente");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error intentando modificar el servicio");
        }
    }

    public void modificarServicioPorPrecio(int idCita, double precio) {
        try {
            realizarConexion();
            String actualizacion = "UPDATE horario SET precio = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(actualizacion);
            ps.setDouble(1, precio);
            ps.setInt(2, idCita);
            int result = ps.executeUpdate();
            if (result == 0) {
                JOptionPane.showMessageDialog(null, "No se ha podido modificar el precio, vuelva a intentarlo");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error intentando modificar el servicio");
        }
    }

    public void modificarServicioPorHora(String hora, int idCita) {
        try {
            realizarConexion();
            String actualizacion = "UPDATE horario SET hora=? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(actualizacion);
            ps.setString(1, hora);
            ps.setInt(2, idCita);
            int result = ps.executeUpdate();
            if (result == 0) {
                JOptionPane.showMessageDialog(null, "No se ha podido modificar la fecha, vuelva a intentarlo");
                JOptionPane.showMessageDialog(null, "Hora modificada exitosamente");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error intentando modificar el servicio");
        }
    }

    public ConexionBD realizarConexion() {
        ConexionBD conexion = new ConexionBD("admin", "123pelu");
        con = conexion.getConnection();
        return conexion;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public void modificarUsuario(int id) {
        realizarConexion();
        String[] opciones = {"Nombre", "Apellidos", "Telefono", "Email", "Cuenta", "Contrasenia"};
        String nombre, apellidos, telefono, email, cuenta, contrasenia;
        String actualizacionSQL = "UPDATE usuario SET ";
        int seleccion = JOptionPane.showOptionDialog(null, "¿Que quieres modificar?", "Opciones", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
        if (seleccion >= 0) {
            try {
                String actualizar = opciones[seleccion].toString();
                switch (actualizar) {
                    case "Nombre":
                        nombre = JOptionPane.showInputDialog(null, "Por favor, ingrese su nombre:");
                        String actualizacionNombre = actualizacionSQL + "NOMBRE = ? WHERE ID=?";
                        PreparedStatement actNombre = con.prepareStatement(actualizacionNombre);
                        actNombre.setString(1, nombre);
                        actNombre.setInt(2, id);
                        int resultado = actNombre.executeUpdate();
                        if (resultado > 0) {
                            JOptionPane.showMessageDialog(null, "Actualizacion hecha satisfactoriamente");
                        }
                        break;
                    case "Apellidos":
                        apellidos = JOptionPane.showInputDialog(null, "Por favor, ingrese sus apellidos:");
                        String actualizacionApellidos = actualizacionSQL + "APELLIDOS = ? WHERE ID=?";
                        PreparedStatement actApellidos = con.prepareStatement(actualizacionApellidos);
                        actApellidos.setString(1, apellidos);
                        actApellidos.setInt(2, id);
                        int resultadoApe = actApellidos.executeUpdate();
                        if (resultadoApe > 0) {
                            JOptionPane.showMessageDialog(null, "Actualizacion hecha satisfactoriamente");
                        }
                        break;
                    case "Telefono":
                        telefono = JOptionPane.showInputDialog(null, "Por favor, ingrese su número de teléfono:");
                        String actualizacionTelefono = actualizacionSQL + "TELEFONO = ? WHERE ID=?";
                        if (actualizacionTelefono.length() != 9) {
                            JOptionPane.showMessageDialog(null, "Telefono no valido. Introduce 9 digitos");
                        } else {
                            PreparedStatement actTelf = con.prepareStatement(actualizacionTelefono);
                            actTelf.setString(1, telefono);
                            actTelf.setInt(2, id);
                            int resultadoTelf = actTelf.executeUpdate();
                            if (resultadoTelf > 0) {
                                JOptionPane.showMessageDialog(null, "Actualizacion hecha satisfactoriamente");
                            }
                        }
                        break;
                    case "Email":
                        email = JOptionPane.showInputDialog(null, "Por favor, ingrese su dirección de correo electrónico:");
                        String actualizacionEmail = actualizacionSQL + "EMAIL = ? WHERE ID=?";
                        PreparedStatement actEmail = con.prepareStatement(actualizacionEmail);
                        actEmail.setString(1, email);
                        actEmail.setInt(2, id);
                        int resultadoEmail = actEmail.executeUpdate();
                        if (resultadoEmail > 0) {
                            JOptionPane.showMessageDialog(null, "Actualizacion hecha satisfactoriamente");
                        }
                        break;
                    case "Cuenta":
                        cuenta = JOptionPane.showInputDialog(null, "Por favor, ingrese su cuenta:");
                        String actualizacionCuenta = actualizacionSQL + "CUENTA = ? WHERE ID=?";
                        PreparedStatement actCuenta = con.prepareStatement(actualizacionCuenta);
                        actCuenta.setString(1, cuenta);
                        actCuenta.setInt(2, id);
                        int resultadoCuenta = actCuenta.executeUpdate();
                        if (resultadoCuenta > 0) {
                            JOptionPane.showMessageDialog(null, "Actualizacion hecha satisfactoriamente");
                        }
                        break;
                    case "Contrasenia":
                        contrasenia = JOptionPane.showInputDialog(null, "Por favor, ingrese su contraseña:");
                        String actualizacionPasswd = actualizacionSQL + "CONTRASENIA = ? WHERE ID=?";
                        PreparedStatement actPasswd = con.prepareStatement(actualizacionPasswd);
                        actPasswd.setString(1, contrasenia);
                        actPasswd.setInt(2, id);
                        int resultadoPasswd = actPasswd.executeUpdate();
                        if (resultadoPasswd > 0) {
                            JOptionPane.showMessageDialog(null, "Actualizacion hecha satisfactoriamente");
                        }
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Selección inválida");
                        break;
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al modificar. Compruebe bien los datos requeridos");
            }

        }

    }

    public void modificarHorarioPorFechaHora(String fecha, String hora, int idHorario) {
        try {
            String sql = "UPDATE horario SET fecha = ?, hora = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, fecha);
            ps.setString(2, hora);
            ps.setInt(3, idHorario);
            ps.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al modificar el horario por fecha y hora");
        }
    }

    public void modificarHorarioPorServicio(int idHorario, int idServicio) {
        try {
            String sql = "UPDATE horario SET id_servicio = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idServicio);
            ps.setInt(2, idHorario);
            ps.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al modificar el horario por servicio");
        }
    }

    public ArrayList<Usuario> obtenerListaEmpleados() {
        ArrayList<Usuario> resul = new ArrayList<>();
        try {
            realizarConexion();

            String sql = "SELECT ID,NOMBRE,APELLIDOS FROM USUARIO WHERE NOMBRE!='ADMINISTRADOR' AND TIPO_DE_USUARIO LIKE 'PERSONAL'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                int id = res.getInt("id");
                String nombre = res.getString("nombre");
                String apellidos = res.getString("apellidos");
                Usuario usu = new Usuario(id, nombre, apellidos);
                resul.add(usu);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al coger los servicios");
        }
        return resul;
    }

    public ArrayList<Servicio> obtenerListaServicios() {
        ArrayList<Servicio> servicios = new ArrayList<>();

        try {
            // Consulta para obtener la lista de servicios
            realizarConexion();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM servicios");

            // Recorrer el resultado y crear objetos Servicio
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String descripcion = resultSet.getString("descripcion");

                Servicio servicio = new Servicio(id, descripcion);
                servicios.add(servicio);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return servicios;
    }

    public Usuario obtenerEmpleadoPorNombre(String nombre, String apellidos) {
        Usuario empleado = null;

        try {
            // Consultar la base de datos para obtener el empleado por nombre y apellidos
            realizarConexion();
            PreparedStatement statement = con.prepareStatement("SELECT id, nombre, apellidos FROM usuario WHERE nombre = ? AND apellidos = ?");
            statement.setString(1, nombre);
            statement.setString(2, apellidos);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombreUsuario = resultSet.getString("nombre");
                String apellidosUsuario = resultSet.getString("apellidos");

                // Crear el objeto Usuario con los datos obtenidos de la base de datos
                empleado = new Usuario(id, nombreUsuario, apellidosUsuario);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return empleado;
    }

    public int obtenerIdUsuario(String nombreC, String apellidoC) {
        int id = -1;
        realizarConexion();
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT id FROM usuario WHERE nombre=? AND apellidos=?");
            pstmt.setString(1, nombreC);
            pstmt.setString(2, apellidoC);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    id = rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la búsqueda");
        }
        return id;
    }
}
