/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 * Clase que representa al Personal
 * @author Mario
 */
public class Personal extends Usuario{
    private double salario;
    private String tipo;

    public Personal() {
    }

    public Personal(double salario, String tipo, int id, int telefono, String nombre, String apellidos, String email, String cuenta, String contrasenia, String tipo_de_usuario) {
        super(id, telefono, nombre, apellidos, email, cuenta, contrasenia, tipo_de_usuario);
        this.salario = salario;
        this.tipo = tipo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return super.toString()+", salario: " + salario + ", tipo: " + tipo;
    }

    
    
}
