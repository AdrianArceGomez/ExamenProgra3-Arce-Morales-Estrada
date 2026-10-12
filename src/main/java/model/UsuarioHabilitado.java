/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package model;

/**
 *
 * @author User
 */
public enum UsuarioHabilitado {
    
    HABILITADO("Habilitado"),
    DESHABILITADO("Deshabilitado");
    
    private final String Tipo;

    private UsuarioHabilitado(String Tipo) {
        this.Tipo = Tipo;
    }

    public String getTipo() {
        return Tipo;
    }
    
    
}
