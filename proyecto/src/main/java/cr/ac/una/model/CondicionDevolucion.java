/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package cr.ac.una.model;

/**
 *
 * @author moral
 */
public enum CondicionDevolucion {
    
    DAÑADA("Dañada"),
    BUENA("Buena");
    private final String Estado;

    private CondicionDevolucion(String Estado) {
        this.Estado = Estado;
    }

    public String getEstado() {
        return Estado;
    }
    
    
}
