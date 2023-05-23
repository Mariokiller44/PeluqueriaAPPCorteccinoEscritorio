/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Administrador
 */
public class Cliente extends Usuario{
    private String descripcion;

    public Cliente() {
    }

    public Cliente(String descripcion, int id, int telefono, String nombre, String apellidos, String email, String cuenta, String contrasenia, String tipo_de_usuario) {
        super(id, telefono, nombre, apellidos, email, cuenta, contrasenia, tipo_de_usuario);
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return super.toString()+", descripcion: "+descripcion;
    }
    
    
    
}
