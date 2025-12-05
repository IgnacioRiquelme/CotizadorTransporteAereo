package exceptions;

/**
 * Excepción personalizada para confirmar procesos en automatizaciones.
 * Útil para validaciones de negocio durante la ejecución de tests.
 */
public class ConfirmaProceso extends RuntimeException {
    public ConfirmaProceso(String message) {
        super(message);
    }
    
    public ConfirmaProceso(String message, Throwable cause) {
        super(message, cause);
    }
}
