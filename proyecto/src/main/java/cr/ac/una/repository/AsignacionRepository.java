/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cr.ac.una.repository;

import java.util.List;
import java.util.Optional;
import cr.ac.una.model.Asignacion;
import cr.ac.una.model.Herramienta;

/**
 *
 * @author User
 */
public interface AsignacionRepository {
    
    
    void save(Asignacion a);
    Optional<Asignacion> findById(int id);
    Optional<Asignacion> findActiveByHerramientaId(int codigo);
    List<Herramienta> findHerramientasByUsuarioId(int carnet);
    List<Asignacion> findHistorialByHerramientaId(int codigo);
}
