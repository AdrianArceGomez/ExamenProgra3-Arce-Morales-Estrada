/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import exception.ValidacionException;
import java.util.Objects;

import model.Asignacion;
import repository.AsignacionRepository;
import repository.HerramientaRepository;
import repository.UsuarioRepository;

import exception.ReglaNegocioException;
import model.CondicionDevolucion;
import model.Usuario;
import model.Herramienta;
import model.UsuarioHabilitado;
import model.CondicionHerramienta;



public class AsignacionService {
    
    private final AsignacionRepository asignacionRepository;
    private final HerramientaRepository herramientaRepository;
    private final UsuarioRepository usuarioRepository;

    public AsignacionService(
            AsignacionRepository asignacionRepository,
            HerramientaRepository herramientaRepository,
            UsuarioRepository usuarioRepository) {

        this.asignacionRepository = Objects.requireNonNull(
                asignacionRepository,
                "El repositorio de asignaciones es obligatorio");

        this.herramientaRepository = Objects.requireNonNull(
                herramientaRepository,
                "El repositorio de herramientas es obligatorio");

        this.usuarioRepository = Objects.requireNonNull(
                usuarioRepository,
                "El repositorio de usuarios es obligatorio");
    }
    public void validarAsignacion(Asignacion asignacion)
            throws ValidacionException {

        if (asignacion == null) {
            throw new ValidacionException(
                    "La asignacion no puede ser nula");
        }

        if (asignacion.getUsuarioId() <= 0) {
            throw new ValidacionException(
                    "El usuario es obligatorio");
        }

        if (asignacion.getHerramientaId() <= 0) {
            throw new ValidacionException(
                    "La herramienta es obligatoria");
        }

        if (asignacion.getHorasolicitud() == null) {
            throw new ValidacionException(
                    "La fecha de solicitud es obligatoria");
        }

        if (asignacion.getHoradevolucion() != null
                && asignacion.getHoradevolucion()
                        .isBefore(asignacion.getHorasolicitud())) {

            throw new ValidacionException(
                    "La fecha de devolucion no puede ser anterior a la solicitud");
        }

        if (asignacion.getHoradevolucion() != null
                && asignacion.getCondicionDevolucion() == null) {

            throw new ValidacionException(
                    "La condicion de devolucion es obligatoria");
        }
    }
    
     
public void validarDisponibilidad(int carnet, int codigo)
        throws ReglaNegocioException {

    Usuario usuario = usuarioRepository.findById(carnet)
            .orElseThrow(() -> new ReglaNegocioException(
                    "El usuario no existe"));

    Herramienta herramienta = herramientaRepository.findById(codigo)
            .orElseThrow(() -> new ReglaNegocioException(
                    "La herramienta no existe"));

    if (!UsuarioHabilitado.HABILITADO.getTipo()
            .equals(usuario.getEstado())) {

        throw new ReglaNegocioException(
                "El usuario no esta habilitado para solicitar herramientas");
    }

    if (!CondicionHerramienta.DISPONIBLE.getEstado()
            .equals(herramienta.getEstadoHerrramienta())) {

        throw new ReglaNegocioException(
                "La herramienta no esta disponible");
    }

    if (asignacionRepository
            .findActiveByHerramientaId(codigo)
            .isPresent()) {

        throw new ReglaNegocioException(
                "La herramienta ya tiene una asignacion activa");
    }
}

public void validarLimiteHerramientas(int carnet)
        throws ReglaNegocioException {

    if (carnet <= 0) {
        throw new ReglaNegocioException(
                "El carnet del usuario no es valido");
    }

    int cantidadActivas =
            usuarioRepository.countActiveAsignaciones(carnet);

    if (cantidadActivas >= 3) {
        throw new ReglaNegocioException(
                "El usuario ya tiene el maximo de 3 herramientas asignadas");
    }
}

public Asignacion validarDevolucion(int idAsignacion)
        throws ReglaNegocioException {

    if (idAsignacion <= 0) {
        throw new ReglaNegocioException(
                "El identificador de la asignacion no es valido");
    }

    Asignacion asignacion = asignacionRepository.findById(idAsignacion)
            .orElseThrow(() -> new ReglaNegocioException(
                    "La asignacion no existe"));

    if (!asignacion.estaActiva()) {
        throw new ReglaNegocioException(
                "La herramienta ya fue devuelta");
    }

    return asignacion;
}

public void aplicarSancionPorDanio(
        Asignacion asignacion,
        CondicionDevolucion condicion)
        throws ReglaNegocioException {

    if (asignacion == null) {
        throw new ReglaNegocioException(
                "La asignacion no puede ser nula");
    }

    if (condicion == null) {
        throw new ReglaNegocioException(
                "La condicion de devolucion es obligatoria");
    }

    if (condicion == CondicionDevolucion.DAÑADO) {

        Usuario usuario = usuarioRepository
                .findById(asignacion.getUsuarioId())
                .orElseThrow(() -> new ReglaNegocioException(
                        "El usuario no existe"));

        usuario.setEstado(
                UsuarioHabilitado.DESHABILITADO.getTipo());

        usuarioRepository.save(usuario);
    }
}

  
}
