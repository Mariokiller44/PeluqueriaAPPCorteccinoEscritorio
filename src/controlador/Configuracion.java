/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

/**
 * Interfaz para la conexion con la base de datos
 * @author Mario
 */
public interface Configuracion {
    String DB_NAME="bd_alcorteccino";
    String DB_USER="admin";
    String DB_PASSWORD="123pelu";
    //LA URL DE LA BASE DE DATOS ESTA ENMASCARADA, PARA USAR LA BASE DE DATOS IMPORTADA EN TU SERVIDOR, POR FAVOR CAMBIE LA URL.
    String URL="jdbc:mysql://localhost:3306/bd_alcorteccino";
    
}
