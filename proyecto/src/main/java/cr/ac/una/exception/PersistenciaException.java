package cr.ac.una.exception;

/**
 * Error al leer o escribir en la base de datos.
 * Es RuntimeException para que los DAO puedan cumplir las interfaces
 * de repository sin tener que declarar "throws".
 */
public class PersistenciaException extends RuntimeException {

    public PersistenciaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
