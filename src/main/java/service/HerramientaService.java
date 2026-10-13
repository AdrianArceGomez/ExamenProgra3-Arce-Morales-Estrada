/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import exception.ValidacionException;
import java.util.Objects;
import model.Herramienta;
import repository.HerramientaRepository;
import model.CondicionHerramienta;

/**
 *
 * @author tadan
 */
public class HerramientaService {
    private final HerramientaRepository herramientaRepository;

public HerramientaService(HerramientaRepository herramientaRepository) {
    this.herramientaRepository = Objects.requireNonNull(
            herramientaRepository,
            "El repositorio de herramientas es obligatorio");
}
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

        if (herramienta.getNombre().length() > 20) {
            throw new ValidacionException(
                    "El nombre no puede superar los 20 caracteres");
        }

        if (herramienta.getTipo() == null
                || herramienta.getTipo().trim().isEmpty()) {
            throw new ValidacionException(
                    "El tipo de herramienta es obligatorio");
        }

<<<<<<< HEAD
        if (herramienta.getEstadoHerramienta() == null
                || herramienta.getEstadoHerramienta().trim().isEmpty()) {
=======
        if (herramienta.getTipo().length() > 20) {
            throw new ValidacionException(
                    "El tipo no puede superar los 20 caracteres");
        }

        if (herramienta.getEstadoHerrramienta() == null
                || herramienta.getEstadoHerrramienta().trim().isEmpty()) {
>>>>>>> b4a316df746aff94965f81945ba8e4da7ef20e10
            throw new ValidacionException(
                    "El estado de la herramienta es obligatorio");
        }
    String estado = herramienta.getEstadoHerrramienta();

<<<<<<< HEAD
        String estado = herramienta.getEstadoHerramienta().trim();
=======
    boolean estadoValido = false;
>>>>>>> b4a316df746aff94965f81945ba8e4da7ef20e10

    for (CondicionHerramienta condicion : CondicionHerramienta.values()) {
    if (condicion.getEstado().equals(estado)) {
        estadoValido = true;
        break;
    }
    }

    if (!estadoValido) {
    throw new ValidacionException(
            "El estado de la herramienta no es valido");
    }
    }
}
