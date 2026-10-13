/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cr.ac.una.repository;

import java.util.List;
import java.util.Optional;
import cr.ac.una.model.Herramienta;

/**
 *
 * @author User
 */
public interface HerramientaRepository {
    
    void save(Herramienta h);
    Optional<Herramienta> findById(int codigo);
    List<Herramienta> findAll();
}
