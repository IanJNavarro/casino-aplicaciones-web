package mx.edu.utez.casino.controller.jugador.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestJugadorDTO {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;

    @NotNull(message = "El saldo inicial es obligatorio")
    @PositiveOrZero(message = "El saldo inicial no puede ser negativo")
    @Digits(integer = 10, fraction = 2, message = "El saldo admite maximo 10 enteros y 2 decimales")
    private BigDecimal saldo;

    // Opcional: si no se envia, el jugador se crea activo
    private Boolean activo;
}
