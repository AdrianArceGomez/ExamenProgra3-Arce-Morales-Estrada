/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author User
 */
public class Asignacion {
    
    public int Id;
    public String Estudiante;
    public String Herramienta;
    public Date Horaasignacion;
    public Date Horadevolucion;
    public String Condiciondevolucion;
    public String Observacion;

    public Asignacion(int Id, String Estudiante, String Herramienta, Date Horaasignacion, Date Horadevolucion, String Condiciondevolucion, String Observacion) {
        this.Id = Id;
        this.Estudiante = Estudiante;
        this.Herramienta = Herramienta;
        this.Horaasignacion = Horaasignacion;
        this.Horadevolucion = Horadevolucion;
        this.Condiciondevolucion = Condiciondevolucion;
        this.Observacion = Observacion;
    }

    public int getId() {
        return Id;
    }

    public String getEstudiante() {
        return Estudiante;
    }

    public String getHerramienta() {
        return Herramienta;
    }

    public Date getHoraasignacion() {
        return Horaasignacion;
    }

    public Date getHoradevolucion() {
        return Horadevolucion;
    }

    public String getCondiciondevolucion() {
        return Condiciondevolucion;
    }

    public String getObservacion() {
        return Observacion;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public void setEstudiante(String Estudiante) {
        this.Estudiante = Estudiante;
    }

    public void setHerramienta(String Herramienta) {
        this.Herramienta = Herramienta;
    }

    public void setHoraasignacion(Date Horaasignacion) {
        this.Horaasignacion = Horaasignacion;
    }

    public void setHoradevolucion(Date Horadevolucion) {
        this.Horadevolucion = Horadevolucion;
    }

    public void setCondiciondevolucion(String Condiciondevolucion) {
        this.Condiciondevolucion = Condiciondevolucion;
    }

    public void setObservacion(String Observacion) {
        this.Observacion = Observacion;
    }
    
    
    
}
