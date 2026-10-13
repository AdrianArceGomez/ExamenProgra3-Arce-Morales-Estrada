/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.controller;
import cr.ac.una.exception.ValidacionException;
import cr.ac.una.model.Herramienta;
import cr.ac.una.service.HerramientaService;
import java.util.List;
/**
 *
 * @author moral
 */
public class HerramientaController {
    private final HerramientaService herramientaService;
    
    public HerramientaController(HerramientaService herramientaService){
        this.herramientaService = herramientaService;
    } 
    // verificacion y registro de la herramienta
    public void registrarHerramienta(Herramienta herramienta){

        try {
            herramientaService.validarHerramienta(herramienta);
            herramientaService.registrarHerramienta(herramienta);
            
            System.out.println("Herramienta registrado correctamente. ");
            
        }catch (Exception e){
            System.out.println("Error no se pudo registrar la herramienta correctmente. ");
        }
    }
    //consulta la herramienta por su codigo y da una su ficha
    public void consultarHerramienta(int codigo) {
        if (codigo <= 0) {
            System.out.println("Error el código de la herramienta debe ser mayor a cero.");
            return;
        }        
        Herramienta h = herramientaService.ConsultarHerramienta(codigo);
        if (h != null) {
            System.out.println("\n =================================");
            System.out.println("     DETALLE DE LA HERRAMIENTA    ");
            System.out.println("====================================");
            System.out.println("Código: " + h.getCodigo());
            System.out.println("Nombre: " + h.getNombre());
            System.out.println("Tipo:   " + h.getTipo());
            System.out.println("Estado: " + h.getEstadoHerrramienta());
            System.out.println("=====================================");
        } else {
            System.out.println("Error no se encontró ninguna herramienta con el código: " + codigo);
        }
    }
    // lista todas las herramientas 
    public void listarHerramientas() {
        List<Herramienta> lista = herramientaService.listarHerramientas();
        if (lista == null || lista.isEmpty()) {
            System.out.println("No hay herramientas registradas en el sistema.");
            return;
        }
        System.out.println("\n====================================================================");
        System.out.println("                     INVENTARIO DE HERRAMIENTAS                     ");
        System.out.println("====================================================================");
       //listar las herramientas encontradas
        for (Herramienta h : lista) {
            System.out.printf("Cód: %-6d | %-20s | Tipo: %-12s | Estado: [%s]\n", 
                    h.getCodigo(), h.getNombre(), h.getTipo(), h.getEstadoHerrramienta());
                System.out.println("====================================================================");

        }
        System.out.println("====================================================================");
    }
    //Actualiza y valida la herramienta
      public void actualizarHerramienta(Herramienta herramienta) {

        try {
            herramientaService.validarHerramienta(herramienta);
            herramientaService.actualizarHerramienta(herramienta);
            System.out.println("Los datos de la herramienta se actualizaron correctamente.");
        } catch (ValidacionException e) {
            System.out.println("Error de validación: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error no se pudo actualizar: " + e.getMessage());
        }
    }

}
