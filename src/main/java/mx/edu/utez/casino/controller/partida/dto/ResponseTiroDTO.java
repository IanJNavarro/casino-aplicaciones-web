package mx.edu.utez.casino.controller.partida.dto;

import mx.edu.utez.casino.model.EstadoPartida;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTiroDTO {
    private Long id;
    private Long partidaId;
    private int valorDado1;
    private int valorDado2;
    private int suma;
    private boolean esGanador;
    /** GANADOR, PERDEDOR o CONTINUA */
    private String resultado;
    private EstadoPartida estadoPartida;
    private BigDecimal saldoJugador;
}
