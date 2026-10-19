package mx.edu.utez.casino.controller.partida.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestIniciarPartidaDTO {
    @NotNull(message = "El id del jugador es obligatorio")
    @Positive(message = "El id del jugador debe ser mayor a 0")
    private Long jugadorId;

    @NotNull(message = "La apuesta es obligatoria")
    @Positive(message = "La apuesta debe ser mayor a 0")
    @Digits(integer = 10, fraction = 2, message = "La apuesta admite maximo 10 enteros y 2 decimales")
    private BigDecimal apuesta;
}
