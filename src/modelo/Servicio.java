/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Mario
 * Clase Servicio: representa un servicio con sus atributos.
 */
public class Servicio {
    private int id;             // ID del servicio
    private String descripcion; // Descripción del servicio

    /*
     * Constructor de la clase Servicio.
     *
     * @param id          ID del servicio
     * @param descripcion Descripción del servicio
     */
    public Servicio(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    /*
     * Obtener el ID del servicio.
     *
     * @return El ID del servicio.
     */
    public int getId() {
        return id;
    }

    /*
     * Obtener la descripción del servicio.
     *
     * @return La descripción del servicio.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /*
     * Sobrescritura del método toString() para representar el objeto como una cadena de texto.
     *
     * @return La representación en cadena de texto del objeto Servicio.
     */
    @Override
    public String toString() {
        return "Servicio:" + descripcion;
    }
}


