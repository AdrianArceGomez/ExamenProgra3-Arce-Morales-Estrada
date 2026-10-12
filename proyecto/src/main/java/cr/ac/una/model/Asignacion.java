/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.model;

import java.time.LocalDateTime;


/**
 *objeto que tiene los daros de la asignacion de la herramienta 
 * @author moral
 */
public class Asignacion {
    int id;
    int herramientaId;
    int usuarioId;
    LocalDateTime horasolicitud;
    LocalDateTime horaDevolucion;
    CondicionDevolucion condicionDevolucion;
    
    public Asignacion(){}
    public Asignacion(int id, int herramientaId, int usuarioId, LocalDateTime horasolicitud, LocalDateTime horaDevolucion, CondicionDevolucion condicionDevolucion ){
       this.id = id;
       this.herramientaId = herramientaId;
       this.usuarioId = usuarioId;
       this.horasolicitud = horasolicitud;
       this.horaDevolucion = horaDevolucion;
       this.condicionDevolucion = condicionDevolucion;
    }
    
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getHerramientaId(){
        return herramientaId;
    }
    public void setHerramientaId(int herramientaId){
        this.herramientaId = herramientaId;
    }
    public int getUsuarioId(){
        return usuarioId;
    }
    public void setUsuarioId(int usuarioId){
        this.usuarioId = usuarioId;
    }
    public LocalDateTime getHoraSolicitud(){
        return horasolicitud;
    }
    public void setHorasolicitud(LocalDateTime horasolicitud){
    this.horasolicitud = horasolicitud;
    }
    public LocalDateTime getHoraDevolucion(){
        return horaDevolucion;
    }
    public void HoraDevolucion(LocalDateTime horaDevolucion){
        this.horaDevolucion = horaDevolucion;
    }
    public CondicionDevolucion getCondicionDevolucion(){
        return condicionDevolucion;
    }
    public void setCondicionDevolucion(CondicionDevolucion condicionDevolucion){
        this.condicionDevolucion = condicionDevolucion;
    }
}
