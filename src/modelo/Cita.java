/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author mescr
 */
public class Cita {
    private int id;
    private String fecha,hora,servicio,precio,empleado;

    public Cita(int id, String fecha, String hora, String servicio, String precio, String empleado) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.servicio = servicio;
        this.precio = precio;
        this.empleado = empleado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
    

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getDescripcion() {
        return precio;
    }

    public void setDescripcion(String descripcion) {
        this.precio = descripcion;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    @Override
    public String toString() {
        return "fecha: " + fecha + ", hora: " + hora + ", servicio: " + servicio + ", precio: " + precio + ", empleado: " + empleado;
    }

    
    
    
}
