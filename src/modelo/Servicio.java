/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author mescr
 */
public class Servicio {
    private int id;
    private String descripcion;
    
    public Servicio(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }
    
    public int getId() {
        return id;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    // Otros métodos si los necesitas

    @Override
    public String toString() {
        return "Servicio:"+descripcion; 
    }
    
}

