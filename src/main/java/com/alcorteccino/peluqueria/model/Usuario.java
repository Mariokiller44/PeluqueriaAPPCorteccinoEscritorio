package com.alcorteccino.peluqueria.model;

public class Usuario {

    private int id;
    private String telefono;
    private String nombre;
    private String apellidos;
    private String email;
    private String cuenta;
    private String contrasenia;

    public Usuario() {
    }

    public Usuario(int id, String nombre, String apellidos, String email,
                   String telefono, String cuenta, String contrasenia) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
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


    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
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

    @Override
    public String toString() {
        return "Usuario [id=" + id +
                ", nombre=" + nombre +
                ", apellidos=" + apellidos +
                ", email=" + email +
                ", telefono=" + telefono +
                ", cuenta=" + cuenta +
                "]";
    }
}