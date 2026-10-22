/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.service;

import Dao.HerramientaDao;
import cr.ac.una.exception.ReglaNegocioException;
import cr.ac.una.exception.ValidacionException;
import cr.ac.una.model.Herramienta;
import cr.ac.una.repository.HerramientaRepository;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author tadan
 */
public class HerramientaService {
    //VAriable para conectar la base de datos 
    private final HerramientaRepository herramientaRepository;
    //Constructor 
    public HerramientaService(){
        this.herramientaRepository = new HerramientaDao();
    }
    public HerramientaService(HerramientaRepository herramientaRepository) {
        this.herramientaRepository = Objects.requireNonNull(herramientaRepository, "El repositorio de herramientas es obligatorio");
    }
    //Registrar una nueva herramienta en el inventario y verifica duplicados 
    public void registrarHerramienta(Herramienta herramienta) throws ValidacionException, ReglaNegocioException{
        validarHerramienta(herramienta);
        
        //verificacion de dobles
        if (herramientaRepository.findById(herramienta.getCodigo()).isPresent()){
            throw new ReglaNegocioException("Ya existe una herramienta registrada con el código" + herramienta.getCodigo());
        }
        //se manda al repositorio 
        herramientaRepository.save(herramienta);
    }
    //busca la herramienta por su codigo y si no la halla lo devuelve nulo si no la encuentra
    public Herramienta ConsultarHerramienta(int codigo){
        return herramientaRepository.findById(codigo).orElse(null);
    }
    //lista de todas las herramientas 
    public List<Herramienta> listarHerramientas() {
        return herramientaRepository.findAll();
    }
    //actualiza solo a las herramientas existentes
    public void actualizarHerramienta(Herramienta herramienta) throws ReglaNegocioException, ValidacionException{
        validarHerramienta(herramienta);
        
        if (!herramientaRepository.findById(herramienta.getCodigo()).isPresent()) {
            throw new ReglaNegocioException("No se puede actualizar porque la herramienta no existe");
        }
        herramientaRepository.save(herramienta);
    }
    //valida las herramientas 
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

        if (herramienta.getEstadoHerrramienta() == null
                || herramienta.getEstadoHerrramienta().trim().isEmpty()) {
            throw new ValidacionException(
                    "El estado de la herramienta es obligatorio");
        }

        String estado = herramienta.getEstadoHerrramienta().trim();

        if (!estado.equals("DISPONIBLE")
                && !estado.equals("ASIGNADA")
                && !estado.equals("DANADA")
                && !estado.equals("EN_MANTENIMIENTO")) {

            throw new ValidacionException(
                    "El estado de la herramienta no es valido");
        }
    }
}
