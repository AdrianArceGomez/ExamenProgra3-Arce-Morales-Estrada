/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.model;

import java.time.LocalDateTime;
import java.util.Objects;


/**
 *objeto que tiene los daros de la asignacion de la herramienta 
 * @author moral
 */
public class Asignacion {
    private int id;                       
    private final int usuarioId;          
    private final int herramientaId;      
    private final LocalDateTime horasolicitud;
    private LocalDateTime horadevolucion; 
    private CondicionDevolucion condicionDevolucion; 
    
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
        this.usuarioId = usuarioId;
        this.herramientaId = herramientaId;
        this.horasolicitud = Objects.requireNonNull(horasolicitud, "La hora de solicitud es obligatoria");
        this.horadevolucion = horadevolucion;
        this.condicionDevolucion = condicionDevolucion;
    }

    public int getId() { return id; }
    public int getUsuarioId() { return usuarioId; }
    public int getHerramientaId() { return herramientaId; }
    public LocalDateTime getHorasolicitud() { return horasolicitud; }
    public LocalDateTime getHoradevolucion() { return horadevolucion; }
    public CondicionDevolucion getCondicionDevolucion() { return condicionDevolucion; }

    /** Lo usa el repositorio despues de insertar, con el id que genero MySQL. */
    public void setId(int id) {
        this.id = id;
    }

    /** Registra la devolucion de la herramienta. */
    public void devolver(LocalDateTime horadevolucion, CondicionDevolucion condicion) {
        this.horadevolucion = Objects.requireNonNull(horadevolucion, "La hora de devolucion se debe rellenar");
        this.condicionDevolucion = Objects.requireNonNull(condicion, "La condicion en la devolucion se debe mencionar");
    }

    public boolean estaActiva() {
        return horadevolucion == null;
    }
}
