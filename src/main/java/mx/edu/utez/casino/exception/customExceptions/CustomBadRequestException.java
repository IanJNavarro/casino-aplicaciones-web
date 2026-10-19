package mx.edu.utez.casino.exception.customExceptions;

/**
 * Se lanza cuando la solicitud es sintacticamente valida pero incumple
 * una regla de negocio (jugador inactivo, saldo insuficiente, partida ya finalizada...).
 */
public class CustomBadRequestException extends RuntimeException {
    public CustomBadRequestException(String message) {
        super(message);
    }
}
