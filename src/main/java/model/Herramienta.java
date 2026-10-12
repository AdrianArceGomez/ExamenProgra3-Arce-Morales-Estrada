/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author User
 */
public class Herramienta {
    
    public int Codigo;
    public String Nombre;
    public String Tipo;
    public String Estado;

    public Herramienta(int Codigo, String Nombre, String Tipo, String Estado) {
        this.Codigo = Codigo;
        this.Nombre = Nombre;
        this.Tipo = Tipo;
        this.Estado = Estado;
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

    public String getEstado() {
        return Estado;
    }

    public void setCodigo(int Codigo) {
        this.Codigo = Codigo;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }
    
    
    
}
