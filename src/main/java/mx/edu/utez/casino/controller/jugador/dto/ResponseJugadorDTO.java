package mx.edu.utez.casino.controller.jugador.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseJugadorDTO {
    private Long id;
    private String nombre;
    private BigDecimal saldo;
    private boolean activo;
}
