/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.controller;
import cr.ac.una.model.Usuario;
import cr.ac.una.service.UsuarioService;
import java.util.List;
/**
 *
 * @author moral
 */
public class UsuarioController {
    
    private final UsuarioService usuarioService;
    
    public UsuarioController(){
        this.usuarioService = new UsuarioService();
    }
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }   

    public void registrarEstudiante(Usuario usuario) {

        try {
            usuarioService.validarUsuario(usuario);
            usuarioService.registrarUsuario(usuario);
            System.out.println("Estudiante registrado correctamente.");
        } catch (Exception e) {
            System.out.println("Error no se pudo registrar el estudiante: " + e.getMessage());
        }
    }
    public void consultarEstudiante(int carnet) {
        if (carnet <= 0 ){
          System.out.println("Error el carnet debe ser un número entero mayor a cero.");  
        }
        Usuario usuario = usuarioService.consultarUsuario(carnet);
        if (usuario != null) {
            System.out.println("\n--- Datos del Estudiante ---");
            System.out.println("Carnet: " + usuario.getCarnet());
            System.out.println("Nombre: " + usuario.getNombre());
            System.out.println("Carrera: " + usuario.getCarrera());
            System.out.println("Estado: " + usuario.getEstado());
        } else {
            System.out.println("Error Estudiante con carnet " + carnet + " no encontrado.");
        }
    }
    public void listarEstudiantes() {
        List<Usuario> estudiantes = usuarioService.listarUsuarios();
        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes registrados.");
            return;
        }
        System.out.println("\n--- Lista de Estudiantes ---");
        for (Usuario u : estudiantes) {
            System.out.println("Carnet: " + u.getCarnet() + " | Nombre: " + u.getNombre() + " | Carrera: " + u.getCarrera());
        }
    }
    public void actualizarEstudiante(Usuario usuario) {
       
        try {
            usuarioService.validarUsuario(usuario);
            usuarioService.actualizarUsuario(usuario);
            System.out.println("Datos del estudiante actualizados.");
        } catch (Exception e) {
            System.out.println("Error no se pudo actualizar: " + e.getMessage());
        }
    }    
}
