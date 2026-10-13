/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author User
 */
public class Asignacion {
    
    private int id;                       
    private final int UsuarioId;          
    private final int HerramientaId;      
    private final LocalDateTime Horasolicitud;
    private LocalDateTime Horadevolucion; 
    private CondicionDevolucion CondicionDevolucion; 

    /** Para una asignacion nueva (todavia sin id ni devolucion). */
    public Asignacion(int usuarioId, int herramientaId, LocalDateTime horasolicitud) {
        this(0, usuarioId, herramientaId, horasolicitud, null, null);
    }

    /** Para reconstruir una asignacion leida de la base de datos. */
    public Asignacion(int id, int usuarioId, int herramientaId, LocalDateTime horasolicitud,
                        LocalDateTime horadevolucion, CondicionDevolucion condicionDevolucion) {
        if (usuarioId <= 0) {
            throw new IllegalArgumentException("El usuario es obligatorio");
        }
        if (herramientaId <= 0) {
            throw new IllegalArgumentException("La herramienta es obligatoria");
        }
        this.id = id;
        this.UsuarioId = usuarioId;
        this.HerramientaId = herramientaId;
        this.Horasolicitud = Objects.requireNonNull(horasolicitud, "La hora de solicitud es obligatoria");
        this.Horadevolucion = horadevolucion;
        this.CondicionDevolucion = condicionDevolucion;
    }

    public int getId() { return id; }
    public int getUsuarioId() { return UsuarioId; }
    public int getHerramientaId() { return HerramientaId; }
    public LocalDateTime getHorasolicitud() { return Horasolicitud; }
    public LocalDateTime getHoradevolucion() { return Horadevolucion; }
    public CondicionDevolucion getCondicionDevolucion() { return CondicionDevolucion; }

    /** Lo usa el repositorio despues de insertar, con el id que genero MySQL. */
    public void setId(int id) {
        this.id = id;
    }

    /** Registra la devolucion de la herramienta. */
    public void devolver(LocalDateTime horadevolucion, CondicionDevolucion condicion) {
        this.Horadevolucion = Objects.requireNonNull(horadevolucion, "La hora de devolucion se debe rellenar");
        this.CondicionDevolucion = Objects.requireNonNull(condicion, "La condicion en la devolucion se debe mencionar");
    }

    public boolean estaActiva() {
        return Horadevolucion == null;
    }
}
