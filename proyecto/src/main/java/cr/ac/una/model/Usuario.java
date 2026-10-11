/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.model;

/**
 *Objeto que contendra los datos de los estudiantes del laboratorio
 * @author moral
 */
public class Usuario {
    int carnet;
    String carrera;
    String estado;
    String nombre;
    
    public Usuario(){}
    public Usuario(int carnet, String carrera, String estado, String nombre){
        this.carnet = carnet;
        this.carrera = carrera;
        this.estado = estado;
        this.nombre = nombre;
    }
    
    public int getCarnet(){
        return carnet;
    }
    public void setCarnet(int carnet){
        this.carnet = carnet;
    }
    public String getCarrera(){
        return carrera;
    }
    public void setCarrera(String carrera){
        this.carrera = carrera;
    }
    public String getEstado(){
        return estado;
    }
    public void setEstado(String estado){
        this.estado = estado;
    }
    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
}
