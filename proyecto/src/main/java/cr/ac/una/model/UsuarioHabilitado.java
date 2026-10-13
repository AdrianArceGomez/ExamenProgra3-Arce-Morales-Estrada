/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package cr.ac.una.model;

/**
 *
 * @author moral
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
