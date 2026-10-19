package mx.edu.utez.casino.controller.jugador;

import jakarta.validation.Valid;
import mx.edu.utez.casino.controller.jugador.dto.RequestJugadorDTO;
import mx.edu.utez.casino.controller.jugador.dto.RequestRecargaDTO;
import mx.edu.utez.casino.controller.jugador.dto.ResponseJugadorDTO;
import mx.edu.utez.casino.controller.partida.dto.ResponsePartidaDTO;
import mx.edu.utez.casino.service.jugador.JugadorService;
import mx.edu.utez.casino.service.partida.PartidaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin({"*"})
@RequestMapping("/casino/jugadores")
public class JugadorController {

    private final JugadorService jugadorService;
    private final PartidaService partidaService;

    public JugadorController(JugadorService jugadorService, PartidaService partidaService) {
        this.jugadorService = jugadorService;
        this.partidaService = partidaService;
    }

    @PostMapping
    public ResponseEntity<ResponseJugadorDTO> crear(@RequestBody @Valid RequestJugadorDTO payload) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jugadorService.crear(payload));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseJugadorDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(jugadorService.obtener(id));
    }

    // recargarSaldo
    @PatchMapping("/{id}/recarga")
    public ResponseEntity<ResponseJugadorDTO> recargarSaldo(@PathVariable Long id,
                                                            @RequestBody @Valid RequestRecargaDTO payload) {
        return ResponseEntity.ok(jugadorService.recargarSaldo(id, payload));
    }

    // obtenerHistorialPartida
    @GetMapping("/{id}/partidas")
    public ResponseEntity<List<ResponsePartidaDTO>> obtenerHistorialPartida(@PathVariable Long id) {
        return ResponseEntity.ok(partidaService.obtenerHistorialPartida(id));
    }
}
