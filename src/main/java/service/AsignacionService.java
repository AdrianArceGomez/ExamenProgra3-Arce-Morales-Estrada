/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import exception.ValidacionException;
import model.Asignacion;

/**
 *
 * @author tadan
 */
public class AsignacionService {
    
    public void validarAsignacion(Asignacion asignacion)
            throws ValidacionException {

        if (asignacion == null) {
            throw new ValidacionException(
                    "La asignacion no puede ser nula");
        }

        if (asignacion.getEstudiante() == null
                || asignacion.getEstudiante().trim().isEmpty()) {
            throw new ValidacionException(
                    "El estudiante es obligatorio");
        }

        if (asignacion.getHerramienta() == null
                || asignacion.getHerramienta().trim().isEmpty()) {
            throw new ValidacionException(
                    "La herramienta es obligatoria");
        }

        if (asignacion.getHoraasignacion() == null) {
            throw new ValidacionException(
                    "La fecha de asignacion es obligatoria");
        }

        if (asignacion.getHoradevolucion() != null
                && asignacion.getHoradevolucion()
                        .before(asignacion.getHoraasignacion())) {
            throw new ValidacionException(
                    "La devolucion no puede ser anterior a la asignacion");
        }

        if (asignacion.getHoradevolucion() != null) {

            String condicion = asignacion.getCondiciondevolucion();

            if (condicion == null
                    || (!condicion.equals("BUENA")
                    && !condicion.equals("DANADA"))) {

                throw new ValidacionException(
                        "La condicion de devolucion debe ser BUENA o DANADA");
            }
        }
    }
}
