/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.controller;

import cr.ac.una.exception.ValidacionException;
import cr.ac.una.model.CondicionDevolucion;
import cr.ac.una.service.AsignacionService;
import cr.ac.una.model.Asignacion;
import cr.ac.una.exception.ReglaNegocioException;
import java.time.LocalDateTime;

/**
 *
 * @author moral
 */
public class AsignacionController {
   private final AsignacionService asignacionService;
   
    public AsignacionController(AsignacionService asignacionService) {
        this.asignacionService = asignacionService;
    }
    //Registra el prestamo al estudiante de carnet , con la herramienta
    public void postAsignar(int carnet, int codigoHerramienta) throws Exception{
        if (carnet <= 0 ||codigoHerramienta <= 0) {
            System.out.println("Error el carnet o el codigo de herramienta digitado debe ser mayor a cero.");
            return;
        }        
        try {
            
            asignacionService.registrarPrestamo(carnet, codigoHerramienta);
            System.out.println("Herramienta (" + codigoHerramienta + ") asignada correctamente al carnet " + carnet + ".");
            
        } catch (ReglaNegocioException | ValidacionException e) {
        System.out.println("Regla de negocio violada" + e.getMessage());
        } catch (Exception e) {
        System.out.println("Error del sistema no se pudo procesar: " + e.getMessage());
        }
    }
    // retorna el equipo 
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
            
            asignacionService.registrarDevolucion(codigoHerramienta, condicionEnum);
            System.out.println("Devolución de la herramienta (" + codigoHerramienta + ") registrada correctamente.");
            
        } catch (IllegalArgumentException e) {
            System.out.println("Error condición inválida. Debe escribir únicamente 'BUENA' o 'DAÑADA'.");
        } catch (ReglaNegocioException | ValidacionException e) {
            System.out.println("Regla de negocio violada: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error del sistema o regla de negocio violada: " + e.getMessage());
        }
    }  
}
