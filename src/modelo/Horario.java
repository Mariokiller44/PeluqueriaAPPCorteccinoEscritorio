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
    private int id,id_personal,id_servicio;
    private LocalDate fecha;
    private LocalTime hora;

    public Horario() {
    }

    public Horario(int id, int id_personal, int id_servicio, LocalDate fecha, LocalTime hora) {
        this.id = id;
        this.id_personal = id_personal;
        this.id_servicio = id_servicio;
        this.fecha = fecha;
        this.hora = hora;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_personal() {
        return id_personal;
    }

    public void setId_personal(int id_personal) {
        this.id_personal = id_personal;
    }

    public int getId_servicio() {
        return id_servicio;
    }

    public void setId_servicio(int id_servicio) {
        this.id_servicio = id_servicio;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "Horario " + id + ", personal:" + id_personal + ", servicio=" + id_servicio + ", fecha:" + fecha + ", hora:" + hora;
    }
    
    
}
