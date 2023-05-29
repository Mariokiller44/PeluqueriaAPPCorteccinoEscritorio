/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.*;
import java.util.*;

/**
 *
 * @author Administrador
 */

public class Horario {
    private int id;
    private String fecha;
    private String hora;
    private String descripcion;
    private String precio;
    private String empleado;

    public Horario(int id, String fecha, String hora, String descripcion, String precio, String empleado) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
        this.precio = precio;
        this.empleado = empleado;
    }
    
    public Horario(int id,String fecha,String hora,String descripcion){
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
    }
    public Horario(int id,String empleado){
        this.id=id;
        this.empleado=empleado;
    }
    public int getId() {
        return id;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public String getEmpleado() {
        return empleado;
    }

    @Override
    public String toString() {
        return "fecha: " + fecha + ", hora: " + hora + ", descripcion: " + descripcion + ", precio: " + precio + ", empleado:" + empleado;
    }
    
}
