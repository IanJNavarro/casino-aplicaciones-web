package mx.edu.utez.casino.service.jugador;

import mx.edu.utez.casino.controller.jugador.dto.RequestJugadorDTO;
import mx.edu.utez.casino.controller.jugador.dto.RequestRecargaDTO;
import mx.edu.utez.casino.controller.jugador.dto.ResponseJugadorDTO;
import mx.edu.utez.casino.exception.customExceptions.CustomNotFoundException;
import mx.edu.utez.casino.model.Jugador;
import mx.edu.utez.casino.repository.JugadorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.RoundingMode;

@Service
public class JugadorService {

    private final JugadorRepository jugadorRepository;

    public JugadorService(JugadorRepository jugadorRepository) {
        this.jugadorRepository = jugadorRepository;
    }

    @Transactional
    public ResponseJugadorDTO crear(RequestJugadorDTO dto) {
        Jugador jugador = new Jugador();
        jugador.setNombre(dto.getNombre().trim());
        jugador.setSaldo(dto.getSaldo().setScale(2, RoundingMode.HALF_UP));
        jugador.setActivo(dto.getActivo() == null || dto.getActivo());
        return aDto(jugadorRepository.save(jugador));
    }

    @Transactional(readOnly = true)
    public ResponseJugadorDTO obtener(Long id) {
        return aDto(buscarJugador(id));
    }

    // recargarSaldo: suma saldo al jugador
    @Transactional
    public ResponseJugadorDTO recargarSaldo(Long id, RequestRecargaDTO dto) {
        Jugador jugador = buscarJugador(id);
        jugador.setSaldo(jugador.getSaldo().add(dto.getMonto().setScale(2, RoundingMode.HALF_UP)));
        return aDto(jugadorRepository.save(jugador));
    }

    private Jugador buscarJugador(Long id) {
        return jugadorRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("El jugador con id " + id + " no existe"));
    }

    private ResponseJugadorDTO aDto(Jugador j) {
        return new ResponseJugadorDTO(j.getId(), j.getNombre(), j.getSaldo(), j.isActivo());
    }
}
