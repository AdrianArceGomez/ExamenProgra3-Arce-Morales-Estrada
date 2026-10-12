/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.controller;

import cr.ac.una.model.CondicionDevolucion;
import cr.ac.una.service.AsignacionService;

/**
 *
 * @author moral
 */
public class AsignacionController {
   private final AsignacionService asignacionService;
   
    public AsignacionController(AsignacionService asignacionService) {
        this.asignacionService = asignacionService;
    }
    public void postAsignar(int carnet, int codigoHerramienta) {
        if (carnet <= 0) {
            System.out.println("Error el carnet digitado debe ser mayor a cero.");
            return;
        }
        if (codigoHerramienta <= 0) {
            System.out.println("Error el código de herramienta digitado debe ser mayor a cero.");
            return;
        }        
        try {
            asignacionService.asignarHerramienta(carnet, codigoHerramienta);
            System.out.println("Herramienta (" + codigoHerramienta + ") asignada correctamente al carnet " + carnet + ".");
        } catch (Exception e) {
            System.out.println("Error no se pudo realizar la asignación: " + e.getMessage());
        }
    }
    public void putDevolver(int codigoHerramienta, String condicion) {
        if (codigoHerramienta <= 0) {
            System.out.println("Error el código de la herramienta debe ser mayor a cero.");
            return;
        }
        if (condicion == null || condicion.isBlank()) {
            System.out.println("Error debe digitar obligatoriamente la condición ('BUENA' o 'DAÑADA').");
            return;
        }        
        try {
            CondicionDevolucion condicionEnum = CondicionDevolucion.valueOf(condicion.toUpperCase().trim());
            
            asignacionService.devolverHerramienta(codigoHerramienta, condicionEnum);
            System.out.println("Devolución de la herramienta (" + codigoHerramienta + ") registrada correctamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error condición inválida. Debe escribir únicamente 'BUENA' o 'DAÑADA'.");
        } catch (Exception e) {
            System.out.println("Erro no se pudo procesar la devolución: " + e.getMessage());
        }
    }    
}
