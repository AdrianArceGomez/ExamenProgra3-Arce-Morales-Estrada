/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package model;

/**
 *
 * @author User
 */
public enum CondicionDevolucion {
    
    DAÑADO("Dañado"),
    BUENO("Bueno");
    private final String Estado;

    private CondicionDevolucion(String Estado) {
        this.Estado = Estado;
    }

    public String getEstado() {
        return Estado;
    }
    
    
    
}
