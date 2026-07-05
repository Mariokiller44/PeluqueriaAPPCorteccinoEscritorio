/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.marioescribano.peluqueriaappcorteccinoescritorio.controlador;


/**
 * Interfaz para la conexion con la base de datos
 * @author Mario
 */
public interface Configuracion {
    String DB_NAME="bd_alcorteccino";
    String DB_USER="devmario";
    String DB_PASSWORD="marioDev26";
    String URL="jdbc:mysql://192.168.56.101:3306/" + DB_NAME;
    //LA URL DE LA BASE DE DATOS ESTA ENMASCARADA, PARA USAR LA BASE DE DATOS IMPORTADA EN TU SERVIDOR, POR FAVOR CAMBIE LA URL.
    String DB_DRIVER="com.mysql.cj.jdbc.Driver";
    public void cerrarConnection();
    
}
