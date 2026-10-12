/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.model;

/**
 * objeto encargado de relacionar las tablas usuarios y herramientas 
 * @author moral
 */
public class UsuarioHerramienta {
    int herramientaId;
    int usuarioId;
    
    public UsuarioHerramienta(){}
    public UsuarioHerramienta(int herramientaId, int usuarioId){
        this.herramientaId = herramientaId;
        this.usuarioId = usuarioId;
    }
    public int getHerramientaEnUso(){
        return herramientaId;
    }
    public void setHerramientaEnUso(int herramientaId){
        this.herramientaId = herramientaId;
    }
    public int getUsuarioConHerramienta(){
        return usuarioId;
    }
    public void setUsuarioConHerramienta(int usuarioId){
        this.usuarioId = usuarioId;
    }
}
