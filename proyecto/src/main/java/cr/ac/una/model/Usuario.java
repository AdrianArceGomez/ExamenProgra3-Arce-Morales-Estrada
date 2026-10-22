/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.model;

import java.util.Objects;

/**
 *Objeto que contendra los datos de los estudiantes del laboratorio
 * @author moral
 */
public class Usuario {
 public int Carnet;
    public String Nombre;
    public String Carrera;
    public String Estado;
   
    public Usuario(){
        
    }

    public Usuario(int Carnet, String Nombre, String Carrera, String Estado) {
        if(Carnet <= 0){
        throw new IllegalArgumentException("El Carnet debe tener 9 o 10 Digitos");
    }
        this.Carnet = Carnet;
        this.Nombre = Nombre;
        this.Carrera = Carrera;
        this.Estado = Estado;
    }

    public int getCarnet() {
        return Carnet;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getCarrera() {
        return Carrera;
    }
    
    public String getEstado(){
        return Estado;
    }
    public void setCarnet(int Carnet){
        Objects.requireNonNull(Carnet, "El carnet es Obligatorio");
        if(Carnet <= 0 || Carnet > 99999999){
         throw new IllegalArgumentException("El carnet debe ser un número entero positivo válido de hasta 9 dígitos.");   
        }
        this.Carnet = Carnet;
    }

    public void setNombre(String Nombre) {
        Objects.requireNonNull(Nombre, "El nombre es Obligatorio");
        if(Nombre.isBlank()||Nombre.length() > 30){
           
        }
        this.Nombre = Nombre;
    }

    public void setCarrera(String Carrera) {
        Objects.requireNonNull(Carrera, "La Carrera es obligatoria");
        if(Carrera.isBlank()||Carrera.length() > 20){
            
        }
        this.Carrera = Carrera;
    }
    
    public void setEstado(String Estado){
        Objects.requireNonNull(Estado, "EL esrado se debe rellenar");
        if(Estado.isBlank() || Estado.length() > 20){
            
        }
        this.Estado = Estado;
    }
   

   public String toString(){
       return "Usuario{Carnet=" + Carnet + ",Nombre=" + Nombre + ",Carrera=" + Carrera + "}";
   }
    
    
}
