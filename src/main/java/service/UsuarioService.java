/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import exception.ValidacionException;
import model.Usuario;

/**
 *
 * @author tadan
 */
public class UsuarioService {
    
    public void validarUsuario(Usuario usuario)
            throws ValidacionException {

        if (usuario == null) {
            throw new ValidacionException(
                    "El usuario no puede ser nulo");
        }

        if (usuario.getCarnet() <= 0) {
            throw new ValidacionException(
                    "El carnet debe ser mayor que cero");
        }

        if (usuario.getNombre() == null
                || usuario.getNombre().trim().isEmpty()) {
            throw new ValidacionException(
                    "El nombre es obligatorio");
        }

        if (usuario.getCarrera() == null
                || usuario.getCarrera().trim().isEmpty()) {
            throw new ValidacionException(
                    "La carrera es obligatoria");
        }

    }

}
