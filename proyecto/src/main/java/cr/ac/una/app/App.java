package cr.ac.una.app;

import cr.ac.una.config.ConexionBD;
import cr.ac.una.controller.AsignacionController;
import cr.ac.una.controller.HerramientaController;
import cr.ac.una.controller.UsuarioController;
import cr.ac.una.model.Usuario;
import cr.ac.una.model.Herramienta;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * Punto de entrada de la aplicación (interfaz de consola).
 *
 * Responsabilidad de esta clase: mostrar menús, leer datos y presentar
 * resultados. No debe contener SQL ni reglas de negocio: debe delegar en
 * los Controllers del paquete controller.
 *
 * Flujo esperado:  App (consola) → Controller → Service → Repository → MySQL
 */
public class App {

    private static final UsuarioController usuarioController = new UsuarioController();
    private static final HerramientaController herramientaController = new HerramientaController();
    private static final AsignacionController asignacionController = new AsignacionController();
    
    public static void main(String[] args) {
        int opcion;
        do {
            Consola.titulo("SISTEMA DE CONTROL DE HERRAMIENTAS - CASO 10");
            System.out.println("1. Probar conexion con la base de datos");
            System.out.println("2. Modulo: Gestion de Estudiantes");
            System.out.println("3. Modulo: Gestion de Herramientas");
            System.out.println("4. Operacion de Negocio: Asignar Herramienta (Prestamo)");
            System.out.println("5. Operacion de Negocio: Devolver Herramienta");
            System.out.println("0. Salir de la Aplicacion");
            
            opcion = Consola.leerEntero("Seleccione una opción del menu: ");

            switch (opcion) {
                case 1 -> probarConexion();
                case 2 -> ejecutarMenuEstudiantes();
                case 3 -> ejecutarMenuHerramientas();
                case 4 -> procesarAsignacion();
                case 5 -> procesarDevolucion();
                case 0 -> System.out.println("\n-> Sistema cerrado correctamente. ¡Muchos exitos en la entrega!");
                default -> System.out.println("Opcion no valida. Digite un numero entre 0 y 5.");
            }
        } while (opcion != 0);
    }

    private static void probarConexion() {
        try (Connection con = ConexionBD.obtenerConexion()) {
            DatabaseMetaData meta = con.getMetaData();
            System.out.println("ConexiOn exitosa: " + meta.getDatabaseProductName()
                    + " " + meta.getDatabaseProductVersion()
                    + " — base de datos: " + con.getCatalog());
        } catch (SQLException e) {
            System.out.println("No fue posible conectar: " + e.getMessage());
        }
    }
    
    private static void ejecutarMenuEstudiantes() {
        Consola.titulo("MODULO INTERNO: GESTION DE ESTUDIANTES");
        System.out.println("1. Registrar Estudiante");
        System.out.println("2. Consultar Estudiante por Carnet");
        System.out.println("3. Listar Catalogo Completo de Estudiantes");
        System.out.println("4. Actualizar Datos / Estado");
        int subOpcion = Consola.leerEntero("Seleccione una sub-opción: ");

        switch (subOpcion) {
            case 1 -> {
                int carnet = Consola.leerEntero("Digite el número de carnet: ");
                String nombre = Consola.leerTexto("Nombre completo del estudiante: ");
                String carrera = Consola.leerTexto("Carrera universitaria: ");

                Usuario estudiante = new Usuario();
                estudiante.setCarnet(carnet);
                estudiante.setNombre(nombre);
                estudiante.setCarrera(carrera);
                estudiante.setEstado("HABILITADO");

                usuarioController.registrarEstudiante(estudiante);
            }
            case 2 -> {
                int carnet = Consola.leerEntero("Ingrese el carnet a buscar: ");
                usuarioController.consultarEstudiante(carnet);
            }
            case 3 -> {
                System.out.println("\nExtrayendo registros desde la base de datos...");
                usuarioController.listarEstudiantes();
            }
            case 4 -> {
                int carnet = Consola.leerEntero("Carnet del estudiante a modificar: ");
                String nombre = Consola.leerTexto("Nuevo Nombre completo: ");
                String carrera = Consola.leerTexto("Nueva Carrera: ");
                String estado = Consola.leerTexto("Nuevo Estado (HABILITADO / DESHABILITADO): ");

                Usuario modificado = new Usuario();
                modificado.setCarnet(carnet);
                modificado.setNombre(nombre);
                modificado.setCarrera(carrera);
                modificado.setEstado(estado.toUpperCase().trim());

                usuarioController.actualizarEstudiante(modificado);
            }
            default -> System.out.println(" Sub-opción inválida.");
        }  
    }
    
    private static void ejecutarMenuHerramientas() {
        Consola.titulo("MODULO INTERNO: GESTION DE HERRAMIENTAS");
        System.out.println("1. Registrar Nueva Herramienta en Inventario");
        System.out.println("2. Consultar Herramienta por Código");
        System.out.println("3. Listar Inventario de Herramientas");
        System.out.println("4. Modificar Datos / Estado Fisico");
        int subOpcion = Consola.leerEntero("Seleccione una sub-opcion: ");

        switch (subOpcion) {
            case 1 -> {
                int codigo = Consola.leerEntero("Codigo identificador (ID unico): ");
                String nombre = Consola.leerTexto("Nombre/Descripcion del equipo: ");
                String tipo = Consola.leerTexto("Tipo de herramienta: ");

                Herramienta herramienta = new Herramienta();
                herramienta.setCodigo(codigo);
                herramienta.setNombre(nombre);
                herramienta.setTipo(tipo);
                herramienta.setEstadoHerrramienta("DISPONIBLE"); // Atributo real con doble 'rr'

                herramientaController.registrarHerramienta(herramienta);
            }
            case 2 -> {
                int codigo = Consola.leerEntero("Código de la herramienta a consultar: ");
                herramientaController.consultarHerramienta(codigo);
            }
            case 3 -> {
                System.out.println("\nCargando inventario completo del laboratorio...");
                herramientaController.listarHerramientas();
            }
            case 4 -> {
                int codigo = Consola.leerEntero("Código de la herramienta a modificar: ");
                String nombre = Consola.leerTexto("Nuevo Nombre/Descripción: ");
                String tipo = Consola.leerTexto("Nuevo Tipo: ");
                System.out.println("Estados válidos: DISPONIBLE, ASIGNADA, DAÑADA, EN_MANTENIMIENTO");
                String estadoStr = Consola.leerTexto("Digite el nuevo estado: ");

                Herramienta modificada = new Herramienta();
                modificada.setCodigo(codigo);
                modificada.setNombre(nombre);
                modificada.setTipo(tipo);
                modificada.setEstadoHerrramienta(estadoStr.toUpperCase().trim());

                herramientaController.actualizarHerramienta(modificada);
            }
            default -> System.out.println("Sub-opción inválida.");
        }
    }
    
    private static void procesarAsignacion() {
        Consola.titulo("TRANSACCIÓN DE NEGOCIO: ASIGNACIÓN DE HERRAMIENTA");
        int carnet = Consola.leerEntero("Ingrese el Carnet del Estudiante solicitante: ");
        int codigo = Consola.leerEntero("Ingrese el Código de la Herramienta a retirar: ");

        System.out.println("\nEnviando datos de asignación al controlador...");
        try {
            asignacionController.postAsignar(carnet, codigo); 
        } catch (Exception e) {
            System.out.println("Error inesperado en el hilo de ejecución principal: " + e.getMessage());
        }
    }
    
    private static void procesarDevolucion() {
        Consola.titulo("TRANSACCIÓN DE NEGOCIO: RETORNO / DEVOLUCIÓN");
        int codigo = Consola.leerEntero("Ingrese el Código de la Herramienta que se devuelve: ");
        
        System.out.println("Condiciones válidas del sistema: BUENA / DAÑADA");
        String condicion = Consola.leerTexto("Digite la condición física de entrega: ");

        System.out.println("\nEnviando datos de retorno al controlador...");
        asignacionController.putDevolver(codigo, condicion); 
    
    }
    
}
