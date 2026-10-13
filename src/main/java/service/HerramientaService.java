/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import exception.ValidacionException;
import model.Herramienta;

/**
 *
 * @author tadan
 */
public class HerramientaService {
    
    public void validarHerramienta(Herramienta herramienta)
            throws ValidacionException {

        if (herramienta == null) {
            throw new ValidacionException(
                    "La herramienta no puede ser nula");
        }

        if (herramienta.getCodigo() <= 0) {
            throw new ValidacionException(
                    "El codigo debe ser mayor que cero");
        }

        if (herramienta.getNombre() == null
                || herramienta.getNombre().trim().isEmpty()) {
            throw new ValidacionException(
                    "El nombre de la herramienta es obligatorio");
        }

        if (herramienta.getTipo() == null
                || herramienta.getTipo().trim().isEmpty()) {
            throw new ValidacionException(
                    "El tipo de herramienta es obligatorio");
        }

        if (herramienta.getEstadoHerramienta() == null
                || herramienta.getEstadoHerramienta().trim().isEmpty()) {
            throw new ValidacionException(
                    "El estado de la herramienta es obligatorio");
        }

        String estado = herramienta.getEstadoHerramienta().trim();

        if (!estado.equals("DISPONIBLE")
                && !estado.equals("ASIGNADA")
                && !estado.equals("DANADA")
                && !estado.equals("EN_MANTENIMIENTO")) {

            throw new ValidacionException(
                    "El estado de la herramienta no es valido");
        }
    }
}
