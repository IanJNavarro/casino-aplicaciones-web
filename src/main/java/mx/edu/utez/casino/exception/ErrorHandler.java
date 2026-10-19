package mx.edu.utez.casino.exception;

import mx.edu.utez.casino.exception.customExceptions.CustomBadRequestException;
import mx.edu.utez.casino.exception.customExceptions.CustomNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {

    // Errores de @Valid en los DTO de entrada
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validar(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new LinkedHashMap<>();
        for (FieldError err : ex.getBindingResult().getFieldErrors()) {
            errores.put(err.getField(), err.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errores);
    }

    // Reglas de negocio incumplidas
    @ExceptionHandler(CustomBadRequestException.class)
    public ResponseEntity<Map<String, String>> manejarCustomBadRequest(CustomBadRequestException ex) {
        return ResponseEntity.badRequest().body(error(ex.getMessage()));
    }

    // Jugador o partida inexistente
    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<Map<String, String>> manejarNoEncontrado(CustomNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error(ex.getMessage()));
    }

    // JSON mal formado o tipos de dato incorrectos (ej. texto en un campo numerico)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> manejarJsonInvalido(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest()
                .body(error("El cuerpo de la peticion no es un JSON valido o contiene datos con tipo incorrecto"));
    }

    // Id con formato incorrecto en la URL (ej. /partidas/abc)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> manejarTipoInvalido(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.badRequest().body(error("El parametro '" + ex.getName() + "' tiene un formato invalido"));
    }

    private Map<String, String> error(String mensaje) {
        Map<String, String> errores = new LinkedHashMap<>();
        errores.put("error", mensaje);
        return errores;
    }
}
