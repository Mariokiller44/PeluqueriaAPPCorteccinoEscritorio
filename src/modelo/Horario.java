/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.*;
import java.util.*;

/**
 *
 * @author Mario
 * Clase Horario: representa un horario con sus atributos.
 */
public class Horario {
    private int id;             // ID del horario
    private String fecha;       // Fecha del horario
    private String hora;        // Hora del horario
    private String descripcion; // Descripción del horario
    private String precio;      // Precio del horario
    private String empleado;    // Empleado asignado al horario

    /*
     * Constructor de la clase Horario.
     *
     * @param id          ID del horario
     * @param fecha       Fecha del horario
     * @param hora        Hora del horario
     * @param descripcion Descripción del horario
     * @param precio      Precio del horario
     * @param empleado    Empleado asignado al horario
     */
    public Horario(int id, String fecha, String hora, String descripcion, String precio, String empleado) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
        this.precio = precio;
        this.empleado = empleado;
    }

    /*
     * Constructor de la clase Horario que recibe solo ID, fecha, hora y descripción.
     *
     * @param id          ID del horario
     * @param fecha       Fecha del horario
     * @param hora        Hora del horario
     * @param descripcion Descripción del horario
     */
    public Horario(int id, String fecha, String hora, String descripcion) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
    }

    /*
     * Constructor de la clase Horario que recibe solo ID y empleado.
     *
     * @param id       ID del horario
     * @param empleado Empleado asignado al horario
     */
    public Horario(int id, String empleado) {
        this.id = id;
        this.empleado = empleado;
    }

    // Métodos de acceso y modificación de los atributos

    /*
     * Obtener el ID del horario.
     *
     * @return El ID del horario.
     */
    public int getId() {
        return id;
    }

    /*
     * Obtener la fecha del horario.
     *
     * @return La fecha del horario.
     */
    public String getFecha() {
        return fecha;
    }

    /*
     * Obtener la hora del horario.
     *
     * @return La hora del horario.
     */
    public String getHora() {
        return hora;
    }

    /*
     * Obtener la descripción del horario.
     *
     * @return La descripción del horario.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /*
     * Obtener el precio del horario.
     *
     * @return El precio del horario.
     */
    public String getPrecio() {
        return precio;
    }

    /*
     * Obtener el empleado asignado al horario.
     *
     * @return El empleado asignado al horario.
     */
    public String getEmpleado() {
        return empleado;
    }

    /*
     * Devolver una representación en cadena del horario.
     *
     * @return La representación en cadena del horario.
     */
    @Override
    public String toString() {
        return "fecha: " + fecha + ", hora: " + hora + ", descripcion: " + descripcion + ", precio: " + precio + ", empleado: " + empleado;
    }
}
