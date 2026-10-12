/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.model;

/**
 *contendra los datos de las herraminetas como multimetros, etc
 * @author moral
 */

public class Herramienta {
    private int codigo;
    private CondicionHerramienta estado;
    private String nombre;
    private String tipo;
    
    public Herramienta(){}
    public Herramienta(int codigo, CondicionHerramienta estado, String nombre, String tipo){
        this.codigo = codigo;
        this.estado = estado;
        this.nombre = nombre;
        this.tipo = tipo;
    }
    
    public int getCodigo(){
        return codigo;
    }
    public void setCodigo(int codigo){
        this.codigo = codigo;
    }
    public CondicionHerramienta getEstado(){
        return estado;
    }
    public void setEstado(CondicionHerramienta estado){
        this.estado = estado;
    }
    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public String getTipo(){
        return tipo;
    }
    public void setTipo(String tipo){
        this.tipo = tipo;
    }
}
