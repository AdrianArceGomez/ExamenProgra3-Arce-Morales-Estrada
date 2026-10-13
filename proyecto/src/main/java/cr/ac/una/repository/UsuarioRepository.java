/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cr.ac.una.repository;

import java.util.List;
import java.util.Optional;
import cr.ac.una.model.Usuario;

/**
 *
 * @author User
 */
public interface UsuarioRepository {
    void save(Usuario u);
    Optional<Usuario> findById(int carnet);
    List<Usuario> findAll();
    int countActiveAsignaciones(int carnet);
}
