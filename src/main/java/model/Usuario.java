/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author User
 */
public class Usuario {
    public int Carnet;
    public String Nombre;
    public String Carrera;
    public Boolean Estado = true;

    public Usuario(int Carnet, String Nombre, String Carrera) {
        this.Carnet = Carnet;
        this.Nombre = Nombre;
        this.Carrera = Carrera;
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

    public Boolean getEstado() {
        return Estado;
    }

    public void setCarnet(int Carnet) {
        this.Carnet = Carnet;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public void setCarrera(String Carrera) {
        this.Carrera = Carrera;
    }

    public void setEstado(Boolean Estado) {
        this.Estado = Estado;
    }
    
    
    
}
