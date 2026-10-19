package mx.edu.utez.casino.controller.partida.dto;

import mx.edu.utez.casino.model.EstadoPartida;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePartidaDTO {
    private Long id;
    private LocalDateTime fecha;
    private EstadoPartida estado;
    private Long jugadorId;
    private String jugadorNombre;
    private BigDecimal apuesta;
}
