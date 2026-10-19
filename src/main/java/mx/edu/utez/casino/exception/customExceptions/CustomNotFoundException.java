package mx.edu.utez.casino.exception.customExceptions;

/**
 * Se lanza cuando un recurso consultado (jugador, partida) no existe.
 */
public class CustomNotFoundException extends RuntimeException {
    public CustomNotFoundException(String message) {
        super(message);
    }
}
