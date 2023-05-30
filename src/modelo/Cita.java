
/**
 * Paquete modelo
 */
package modelo;
/**
 * Clase Cita: representa una cita con sus atributos.
 * @author Mario
 */
public class Cita {
    // Atributos
    private int id;             // ID de la cita
    private String fecha;       // Fecha de la cita
    private String hora;        // Hora de la cita
    private String servicio;    // Servicio de la cita
    private String precio;      // Precio del servicio de la cita
    private String empleado;    // Empleado asignado a la cita

    /*
     * Constructor de la clase Cita.
     * 
     * @param id        ID de la cita
     * @param fecha     Fecha de la cita
     * @param hora      Hora de la cita
     * @param servicio  Servicio de la cita
     * @param precio    Precio del servicio de la cita
     * @param empleado  Empleado asignado a la cita
     */
    public Cita(int id, String fecha, String hora, String servicio, String precio, String empleado) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.servicio = servicio;
        this.precio = precio;
        this.empleado = empleado;
    }

    // Métodos de acceso y modificación de los atributos

    /*
     * Obtener el ID de la cita.
     *
     * @return El ID de la cita.
     */
    public int getId() {
        return id;
    }

    /*
     * Establecer el ID de la cita.
     *
     * @param id El ID de la cita.
     */
    public void setId(int id) {
        this.id = id;
    }

    /*
     * Obtener el precio del servicio de la cita.
     *
     * @return El precio del servicio de la cita.
     */
    public String getPrecio() {
        return precio;
    }

    /*
     * Establecer el precio del servicio de la cita.
     *
     * @param precio El precio del servicio de la cita.
     */
    public void setPrecio(String precio) {
        this.precio = precio;
    }

    /*
     * Obtener la fecha de la cita.
     *
     * @return La fecha de la cita.
     */
    public String getFecha() {
        return fecha;
    }

    /*
     * Establecer la fecha de la cita.
     *
     * @param fecha La fecha de la cita.
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /*
     * Obtener la hora de la cita.
     *
     * @return La hora de la cita.
     */
    public String getHora() {
        return hora;
    }

    /*
     * Establecer la hora de la cita.
     *
     * @param hora La hora de la cita.
     */
    public void setHora(String hora) {
        this.hora = hora;
    }

    /*
     * Obtener el servicio de la cita.
     *
     * @return El servicio de la cita.
     */
    public String getServicio() {
        return servicio;
    }

    /*
     * Establecer el servicio de la cita.
     *
     * @param servicio El servicio de la cita.
     */
    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    /*
     * Obtener la descripción de la cita.
     * (En este caso, la descripción es el mismo precio).
     *
     * @return La descripción de la cita.
     */
    public String getDescripcion() {
        return precio;
    }

    /*
     * Establecer la descripción de la cita.
     * (En este caso, la descripción es el mismo precio).
     *
     * @param descripcion La descripción de la cita.
     */
    public void setDescripcion(String descripcion) {
        this.precio = descripcion;
    }

    /*
     * Obtener el empleado asignado a la cita.
     *
     * @return El empleado asignado a la cita.
     */
    public String getEmpleado() {
        return empleado;
    }

    /*
     * Establecer el empleado asignado a la cita.
     *
     * @param empleado El empleado asignado a la cita.
     */
    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    /*
     * Devolver una representación en cadena de la cita.
     *
     * @return La representación en cadena de la cita.
     */
    @Override
    public String toString() {
        return "fecha: " + fecha + ", hora: " + hora + ", servicio: " + servicio + ", precio: " + precio + ", empleado: " + empleado;
    }
}