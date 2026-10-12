/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package model;

/**
 *
 * @author User
 */
public enum CondicionHerramienta {
    
    DISPONIBLE("Disponible"),
    ASIGNADA("Asignada"),
    DAÑADA("Dañada");
    
    private final String Estado;

    private CondicionHerramienta(String Estado) {
        this.Estado = Estado;
    }

    public String getEstado() {
        return Estado;
    }
    
    
    
    
}
