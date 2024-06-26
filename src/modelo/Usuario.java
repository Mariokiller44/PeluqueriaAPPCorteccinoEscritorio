/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * Clase Usuario: representa un usuario con sus atributos.
 * @author Mario
 */
public class Usuario {
    private int id;                  // ID del usuario
    private int telefono;            // Número de teléfono del usuario
    private String nombre;           // Nombre del usuario
    private String apellidos;        // Apellidos del usuario
    private String email;            // Email del usuario
    private String cuenta;           // Cuenta del usuario
    private String contrasenia;      // Contraseña del usuario
    private String tipo_de_usuario;  // Tipo de usuario

    /*
     * Constructor vacío de la clase Usuario.
     */
    public Usuario() {
    }

    /*
     * Constructor de la clase Usuario.
     *
     * @param id              ID del usuario
     * @param telefono        Número de teléfono del usuario
     * @param nombre          Nombre del usuario
     * @param apellidos       Apellidos del usuario
     * @param email           Email del usuario
     * @param cuenta          Cuenta del usuario
     * @param contrasenia     Contraseña del usuario
     * @param tipo_de_usuario Tipo de usuario
     */
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

    /*
     * Constructor de la clase Usuario que recibe ID, nombre, apellidos, teléfono, email, cuenta y contraseña.
     *
     * @param id          ID del usuario
     * @param nombre      Nombre del usuario
     * @param apellidos   Apellidos del usuario
     * @param telefono    Número de teléfono del usuario
     * @param email       Email del usuario
     * @param cuenta      Cuenta del usuario
     * @param contrasenia Contraseña del usuario
     */
    public Usuario(int id, String nombre, String apellidos, int telefono, String email, String cuenta, String contrasenia) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.email = email;
        this.cuenta = cuenta;
        this.contrasenia = contrasenia;
    }

    /*
     * Constructor de la clase Usuario que recibe ID, nombre y apellidos.
     *
     * @param id        ID del usuario
     * @param nombre    Nombre del usuario
     * @param apellidos Apellidos del usuario
     */
    public Usuario(int id, String nombre, String apellidos) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    // Métodos de acceso y modificación de los atributos

    /*
     * Obtener el ID del usuario.
     *
     * @return El ID del usuario.
     */
    public int getId() {
        return id;
    }

    /*
     * Establecer el ID del usuario.
     *
     * @param id El ID del usuario.
     */
    public void setId(int id) {
        this.id = id;
    }

    /*
     * Obtener el número de teléfono del usuario.
     *
     * @return El número de teléfono del usuario.
     */
    public int getTelefono() {
        return telefono;
    }

    /*
     * Establecer el número de teléfono del usuario.
     *
     * @param telefono El número de teléfono del usuario.
     */
    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    /*
     * Obtener el nombre del usuario.
     *
     * @return El nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /*
     * Establecer el nombre del usuario.
     *
     * @param nombre El nombre del usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /*
     * Obtener los apellidos del usuario.
     *
     * @return Los apellidos del usuario.
     */
    public String getApellidos() {
        return apellidos;
    }

    /*
     * Establecer los apellidos del usuario.
     *
     * @param apellidos Los apellidos del usuario.
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /*
     * Obtener el email del usuario.
     *
     * @return El email del usuario.
     */
    public String getEmail() {
        return email;
    }

    /*
     * Establecer el email del usuario.
     *
     * @param email El email del usuario.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /*
     * Obtener la cuenta del usuario.
     *
     * @return La cuenta del usuario.
     */
    public String getCuenta() {
        return cuenta;
    }

    /*
     * Establecer la cuenta del usuario.
     *
     * @param cuenta La cuenta del usuario.
     */
    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    /*
     * Obtener la contraseña del usuario.
     *
     * @return La contraseña del usuario.
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /*
     * Establecer la contraseña del usuario.
     *
     * @param contrasenia La contraseña del usuario.
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    /*
     * Obtener el tipo de usuario.
     *
     * @return El tipo de usuario.
     */
    public String getTipo_de_usuario() {
        return tipo_de_usuario;
    }

    /*
     * Establecer el tipo de usuario.
     *
     * @param tipo_de_usuario El tipo de usuario.
     */
    public void setTipo_de_usuario(String tipo_de_usuario) {
        this.tipo_de_usuario = tipo_de_usuario;
    }

    /*
     * Sobrescritura del método toString() para representar el objeto como una cadena de texto.
     *
     * @return La representación en cadena de texto del objeto Usuario.
     */
    @Override
    public String toString() {
        return "id:" + id + ", nombre:" + nombre + ", apellidos:" + apellidos;
    }
}
