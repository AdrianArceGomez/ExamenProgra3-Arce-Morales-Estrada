/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Objects;

/**
 *
 * @author User
 */
public class Herramienta {
    
    public int Codigo;
    public String Nombre;
    public String Tipo;
    public String EstadoHerramienta;

    public Herramienta(int Codigo, String Nombre, String Tipo, String EstadoHerramienta) {
        if(Codigo <= 0){
            throw new IllegalArgumentException(" EL codigo no puede ser un numero negativo negativo");
        }
        this.Codigo = Codigo;
        this.Nombre = Nombre;
        this.Tipo = Tipo;
        this.EstadoHerramienta = EstadoHerramienta;
    }

    public int getCodigo() {
        return Codigo;
    }

   
    public String getNombre() {
        return Nombre;
    }

    public String getTipo() {
        return Tipo;
    }

    public String getEstadoHerramienta() {
        return EstadoHerramienta;
    }
    
    
   
    public void setNombre(String Nombre) {
        Objects.requireNonNull(Nombre, "EL nombre es obligatorio");
        if(Nombre.isBlank()||Nombre.length() > 20){
            
        }
        this.Nombre = Nombre;
    }

    public void setTipo(String Tipo) {
        Objects.requireNonNull(Tipo, "El tipo es obligatorio");
        if(Tipo.isBlank()|| Tipo.length() > 20){
            
        }
        this.Tipo = Tipo;
    }

    public void setEstadoHerramienta(String EstadoHerramienta) {

        this.EstadoHerramienta = Objects.requireNonNull(EstadoHerramienta, "El estado de la herramienta es necesario");
    }
    
    
    public String toString(){
        
       return "Herramienta{Codigo=" + Codigo + ", Nombre=" + Nombre + ",Tipo=" + Tipo + "}";
    }
    
    
    
}
