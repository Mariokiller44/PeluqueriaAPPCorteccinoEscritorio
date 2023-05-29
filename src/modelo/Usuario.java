/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Administrador
 */
public class Usuario {
    private int id,telefono;
    private String nombre,apellidos,email,cuenta,contrasenia,tipo_de_usuario;

    public Usuario() {
    }

    public Usuario(int id, int telefono, String nombre, String apellidos, String email, String cuenta, String contrasenia, String tipo_de_usuario) {
        this.id = id;
        this.telefono = telefono;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.cuenta = cuenta;
        this.contrasenia = contrasenia;
        this.tipo_de_usuario = tipo_de_usuario;
    }

    public Usuario(int id, String nombre, String apellidos,int telefono, String email, String cuenta, String contrasenia) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.email = email;
        this.cuenta = cuenta;
        this.contrasenia = contrasenia;
    }

    public Usuario(int id, String nombre, String apellidos) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getTipo_de_usuario() {
        return tipo_de_usuario;
    }

    public void setTipo_de_usuario(String tipo_de_usuario) {
        this.tipo_de_usuario = tipo_de_usuario;
    }

    @Override
    public String toString() {
        return "id:" + id + ", nombre:" + nombre + ", apellidos:" + apellidos;
    }
}
