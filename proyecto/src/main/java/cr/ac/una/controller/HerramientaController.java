/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.controller;
import cr.ac.una.model.Herramienta;
import cr.ac.una.service.HerramientaService;
/**
 *
 * @author moral
 */
public class HerramientaController {
    private final HerramientaService herramientaService;
    
    public HerramientaController(HerramientaService herramientaService){
        this.herramientaService = herramientaService;
    }
    public void registrarHerramienta(Herramienta herramienta){
        if (herramienta == null) {
            System.out.println("Error los datos de la herramienta son nulos.");
            return;
        }
        if (herramienta.getCodigo() <= 0) {
            System.out.println("Error el código de la herramienta debe ser mayor a cero.");
            return;
        }
        if (herramienta.getNombre() == null || herramienta.getNombre().isBlank()) {
            System.out.println("Error el nombre de la herramienta no puede estar vacío.");
            return;
        }
        if (herramienta.getTipo() == null || herramienta.getTipo().isBlank()) {
            System.out.println("Error el tipo de la herramienta (ej: Multímetro) es obligatorio.");
            return;
        }
        try {
            herramientaService.registrarHerramienta(herramienta);
            System.out.println("Herramienta registrado correctamente. ");
            
        }catch (Exception e){
            System.out.println("Error no se pudo registrar la herramienta correctmente. ");
        }
    }
    public void consultarHerramienta(int codigo) {
        if (codigo <= 0) {
            System.out.println("Error el código de la herramienta debe ser mayor a cero.");
            return;
        }        
        Herramienta h = herramientaService.consultarHerramienta(codigo);
        if (h != null) {
            System.out.println("\n =================================");
            System.out.println("     DETALLE DE LA HERRAMIENTA    ");
            System.out.println("====================================");
            System.out.println("Código: " + h.getCodigo());
            System.out.println("Nombre: " + h.getNombre());
            System.out.println("Tipo:   " + h.getTipo());
            System.out.println("Estado: " + h.getEstado());
            System.out.println("=====================================");
        } else {
            System.out.println("Error no se encontró ninguna herramienta con el código: " + codigo);
        }
    }
    public void listarHerramientas() {
        List<Herramienta> lista = herramientaService.listarHerramientas();
        if (lista == null || lista.isEmpty()) {
            System.out.println("No hay herramientas registradas en el sistema.");
            return;
        }
        System.out.println("\n====================================================================");
        System.out.println("                     INVENTARIO DE HERRAMIENTAS                     ");
        System.out.println("====================================================================");
        for (Herramienta h : lista) {
            System.out.printf("Cód: %-6d | %-20s | Tipo: %-12s | Estado: [%s]\n", 
                    h.getCodigo(), h.getNombre(), h.getTipo(), h.getEstado());
        }
        System.out.println("====================================================================");
    }
      public void actualizarHerramienta(Herramienta herramienta) {
        if (herramienta == null) {
            System.out.println("Error los datos de la herramienta son nulos.");
            return;
        }
        if (herramienta.getCodigo() <= 0) {
            System.out.println("Error el código de la herramienta debe ser mayor a cero.");
            return;
        }
        if (herramienta.getNombre() == null || herramienta.getNombre().isBlank()) {
            System.out.println("Error el nombre de la herramienta no puede estar vacío.");
            return;
        }
        if (herramienta.getTipo() == null || herramienta.getTipo().isBlank()) {
            System.out.println("Error el tipo de la herramienta (ej: Multímetro) es obligatorio.");
            return;
        }
        try {
            herramientaService.actualizarHerramienta(herramienta);
            System.out.println("Los datos de la herramienta se actualizaron correctamente.");
        } catch (Exception e) {
            System.out.println("Error no se pudo actualizar: " + e.getMessage());
        }
    }

}
