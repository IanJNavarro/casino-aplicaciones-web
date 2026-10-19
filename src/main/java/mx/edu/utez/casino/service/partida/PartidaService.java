package mx.edu.utez.casino.service.partida;

import mx.edu.utez.casino.controller.partida.dto.RequestIniciarPartidaDTO;
import mx.edu.utez.casino.controller.partida.dto.ResponsePartidaDTO;
import mx.edu.utez.casino.controller.partida.dto.ResponseTiroDTO;
import mx.edu.utez.casino.exception.customExceptions.CustomBadRequestException;
import mx.edu.utez.casino.exception.customExceptions.CustomNotFoundException;
import mx.edu.utez.casino.model.EstadoPartida;
import mx.edu.utez.casino.model.Jugador;
import mx.edu.utez.casino.model.Partida;
import mx.edu.utez.casino.model.Tiro;
import mx.edu.utez.casino.repository.JugadorRepository;
import mx.edu.utez.casino.repository.PartidaRepository;
import mx.edu.utez.casino.repository.TiroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class PartidaService {

    private final JugadorRepository jugadorRepository;
    private final PartidaRepository partidaRepository;
    private final TiroRepository tiroRepository;
    private final Random random = new Random();

    public PartidaService(JugadorRepository jugadorRepository,
                          PartidaRepository partidaRepository,
                          TiroRepository tiroRepository) {
        this.jugadorRepository = jugadorRepository;
        this.partidaRepository = partidaRepository;
        this.tiroRepository = tiroRepository;
    }

    // iniciarPartida
    @Transactional
    public ResponsePartidaDTO iniciarPartida(RequestIniciarPartidaDTO dto) {
        Jugador jugador = jugadorRepository.findById(dto.getJugadorId())
                .orElseThrow(() -> new CustomNotFoundException("El jugador con id " + dto.getJugadorId() + " no existe"));

        // 1. Jugador activo
        if (!jugador.isActivo()) {
            throw new CustomBadRequestException("El jugador no esta activo");
        }

        BigDecimal apuesta = dto.getApuesta().setScale(2, RoundingMode.HALF_UP);

        // 2. Saldo suficiente
        if (jugador.getSaldo().compareTo(apuesta) < 0) {
            throw new CustomBadRequestException("Saldo insuficiente para realizar la apuesta");
        }

        // 3. Descontar apuesta
        jugador.setSaldo(jugador.getSaldo().subtract(apuesta));
        jugadorRepository.save(jugador);

        // 4. Crear partida EN_JUEGO
        Partida partida = new Partida();
        partida.setFecha(LocalDateTime.now());
        partida.setEstado(EstadoPartida.EN_JUEGO);
        partida.setJugador(jugador);
        partida.setApuesta(apuesta);
        return aDto(partidaRepository.save(partida));
    }

    // realizarTiro
    @Transactional
    public ResponseTiroDTO realizarTiro(Long partidaId) {
        Partida partida = buscarPartida(partidaId);

        if (partida.getEstado() != EstadoPartida.EN_JUEGO) {
            throw new CustomBadRequestException("La partida no esta EN_JUEGO, estado actual: " + partida.getEstado());
        }

        // 1. Dos dados aleatorios (1-6)
        int dado1 = random.nextInt(6) + 1;
        int dado2 = random.nextInt(6) + 1;
        // 2. Suma
        int suma = dado1 + dado2;

        // 3. Reglas del juego
        boolean gana = suma == 7 || suma == 11;
        boolean pierde = suma == 2 || suma == 3 || suma == 12;
        String resultado = "CONTINUA";

        Jugador jugador = partida.getJugador();
        if (gana) {
            resultado = "GANADOR";
            partida.setEstado(EstadoPartida.FINALIZADA);
            // el jugador gana el doble de la apuesta
            jugador.setSaldo(jugador.getSaldo().add(partida.getApuesta().multiply(BigDecimal.valueOf(2))));
            jugadorRepository.save(jugador);
            partidaRepository.save(partida);
        } else if (pierde) {
            resultado = "PERDEDOR";
            partida.setEstado(EstadoPartida.FINALIZADA);
            partidaRepository.save(partida);
        }

        // 4. Guardar el tiro como ultimo paso
        Tiro tiro = new Tiro();
        tiro.setPartida(partida);
        tiro.setValorDado1(dado1);
        tiro.setValorDado2(dado2);
        tiro.setSuma(suma);
        tiro.setEsGanador(gana);
        tiro = tiroRepository.save(tiro);

        return new ResponseTiroDTO(tiro.getId(), partida.getId(), dado1, dado2, suma, gana,
                resultado, partida.getEstado(), jugador.getSaldo());
    }

    // finalizarPartidaManual
    @Transactional
    public ResponsePartidaDTO finalizarPartidaManual(Long partidaId) {
        Partida partida = buscarPartida(partidaId);
        if (partida.getEstado() == EstadoPartida.FINALIZADA) {
            throw new CustomBadRequestException("La partida ya esta FINALIZADA");
        }
        partida.setEstado(EstadoPartida.FINALIZADA);
        return aDto(partidaRepository.save(partida));
    }

    // obtenerHistorialPartida
    @Transactional(readOnly = true)
    public List<ResponsePartidaDTO> obtenerHistorialPartida(Long jugadorId) {
        if (!jugadorRepository.existsById(jugadorId)) {
            throw new CustomNotFoundException("El jugador con id " + jugadorId + " no existe");
        }
        return partidaRepository.findByJugadorIdOrderByFechaDesc(jugadorId).stream()
                .map(this::aDto)
                .toList();
    }

    private Partida buscarPartida(Long id) {
        return partidaRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("La partida con id " + id + " no existe"));
    }

    private ResponsePartidaDTO aDto(Partida p) {
        return new ResponsePartidaDTO(p.getId(), p.getFecha(), p.getEstado(),
                p.getJugador().getId(), p.getJugador().getNombre(), p.getApuesta());
    }
}
