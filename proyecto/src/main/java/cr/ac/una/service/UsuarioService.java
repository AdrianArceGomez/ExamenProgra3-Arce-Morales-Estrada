/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.service;

import Dao.UsuarioDao;
import cr.ac.una.exception.ReglaNegocioException;
import cr.ac.una.exception.ValidacionException;
import cr.ac.una.model.Usuario;
import cr.ac.una.repository.UsuarioRepository;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author tadan
 */
public class UsuarioService {
    //variable para conectar con el repository
    private final UsuarioRepository usuarioRepository;
    //constrctor 
    public UsuarioService(){
        this.usuarioRepository = new UsuarioDao();
    }
    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = Objects.requireNonNull(usuarioRepository, "El repositorio de usuarios es obligatorio");
    }
    //valida y registra nuevo usuario 
    public void registrarUsuario(Usuario usuario) throws Exception{
    validarUsuario(usuario);
    if(usuarioRepository.findById(usuario.getCarnet()).isPresent()){
        throw new ReglaNegocioException("Ya existe un estudiante registrado con el carnet " + usuario.getCarnet());
    }
    //nuevos usuarios Habilitados por defecto
    usuario.setEstado("HABILITADO");
    usuarioRepository.save(usuario);
    
    }
    // busca un estudiante por su carnet y si no esta devuelve nulo
    public Usuario consultarUsuario(int carnet) {
        return usuarioRepository.findById(carnet).orElse(null);
    }    
    // lista completa de los alumnos
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }
    // para actualizar los datos de los estudiantes
    public void actualizarUsuario(Usuario usuario) throws Exception{
        validarUsuario(usuario);
        if(!usuarioRepository.findById(usuario.getCarnet()).isPresent()){
            throw new ReglaNegocioException("No se puede actualizar porque el estudiante con carnet " + usuario.getCarnet() + " no existe.");
        }
        usuarioRepository.save(usuario);
    }
    //valida a los usuarios
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
