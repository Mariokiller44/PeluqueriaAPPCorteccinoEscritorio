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
import modelo.Cita;
import modelo.Usuario;

/**
 *
 * @author Administrador
 */
public class ConsultasCliente {

    private Connection con;

    public ArrayList<Cita> consultarCitas(int id) {
        ArrayList<Cita> result = new ArrayList<>();
        Cita cita = null;
        try {
            realizarConexion();
            String mostrarCitas = "SELECT horario.id,horario.fecha_es as fecha, horario.hora_es as hora, servicios.descripcion, servicios.precio,CONCAT(usuario.nombre, ' ', usuario.apellidos) AS empleado\n"
                    + "FROM cita JOIN horario ON cita.id_horario = horario.ID JOIN personal ON horario.id_personal = personal.id JOIN usuario ON personal.id = usuario.id JOIN servicios ON horario.id_servicio = servicios.id\n"
                    + "WHERE cita.id_cliente = ?;";
            PreparedStatement stmt = con.prepareStatement(mostrarCitas);
            stmt.setInt(1, id);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                int idCita = resultado.getInt("id");
                String fecha = resultado.getString("fecha");
                String hora = resultado.getString("hora");
                String descripcion = resultado.getString("descripcion");
                String precio = resultado.getString("precio");
                String empleado = resultado.getString("empleado");
                cita = new Cita(idCita, fecha, hora, descripcion, precio, empleado);
                result.add(cita);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo hacer la consulta");
        }
        return result;
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

    public void modificarServicio(int id, String fecha) {
        try {
            realizarConexion();
            String actualizacion = "UPDATE HORARIO SET ID_SERVICIO=? WHERE fecha LIKE ?";
            PreparedStatement ps = con.prepareStatement(actualizacion);
            ps.setInt(1, id);
            ps.setString(2, fecha);
            int result = ps.executeUpdate();
            if (result == 0) {
                JOptionPane.showMessageDialog(null, "No se ha podido modificar el servicio, vuelva a intentarlo");
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

    public void borrarCita(Cita citaSeleccionada) {
        try {
            realizarConexion();
            String borrado = "DELETE FROM HORARIO WHERE ID=?";
            PreparedStatement psBorrado = con.prepareStatement(borrado);
            psBorrado.setInt(1, citaSeleccionada.getId());
            int resultado = psBorrado.executeUpdate();
            if (resultado == 0) {
                JOptionPane.showMessageDialog(null, "No se ha podido borrar la cita, vuelva a intentarlo");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Problemas al borrar la cita. Intentelo de nuevo");
        }

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
                        JOptionPane.showMessageDialog(null,"Selección inválida");
                        break;
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al modificar. Compruebe bien los datos requeridos");
            }

        }

    }

}
