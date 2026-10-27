/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.exception;

/**
 * clase excepcion para mostrar las horas vencidas de las herramientas
 * @author moral
 */
public class AsignacionVencidaException extends Exception {
    
    public AsignacionVencidaException(String mensaje){
        super(mensaje);
    }
}
