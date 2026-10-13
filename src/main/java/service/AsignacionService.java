/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import exception.ValidacionException;
import model.Asignacion;
import model.CondicionDevolucion;

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

        if (asignacion.getUsuarioId() == null || asignacion.getUsuarioId().trim().isEmpty()) {
            throw new ValidacionException(
                    "El estudiante es obligatorio");
        } else {
        }

        if (asignacion.getHerramientaId() == null || asignacion.getHerramientaId().trim().isEmpty()) {
            throw new ValidacionException(
                    "La herramienta es obligatoria");
        } else {
        }

        if (asignacion.getHorasolicitud() == null) {
            throw new ValidacionException(
                    "La fecha de asignacion es obligatoria");
        }

        if (asignacion.getHoradevolucion() != null && asignacion.getHoradevolucion().isBefore(asignacion.getHorasolicitud())) {
            throw new ValidacionException(
                    "La devolucion no puede ser anterior a la asignacion");
        }

        if (asignacion.getHoradevolucion() != null) {

            CondicionDevolucion condicion = asignacion.getCondicionDevolucion();

            if (condicion == null
                    || (!condicion.equals("BUENA")
                    && !condicion.equals("DANADA"))) {

                throw new ValidacionException(
                        "La condicion de devolucion debe ser BUENA o DANADA");
            }
        }
    }
}
