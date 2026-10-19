package mx.edu.utez.casino.controller.partida;

import jakarta.validation.Valid;
import mx.edu.utez.casino.controller.partida.dto.RequestIniciarPartidaDTO;
import mx.edu.utez.casino.controller.partida.dto.ResponsePartidaDTO;
import mx.edu.utez.casino.controller.partida.dto.ResponseTiroDTO;
import mx.edu.utez.casino.service.partida.PartidaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin({"*"})
@RequestMapping("/casino/partidas")
public class PartidaController {

    private final PartidaService partidaService;

    public PartidaController(PartidaService partidaService) {
        this.partidaService = partidaService;
    }

    // iniciarPartida
    @PostMapping
    public ResponseEntity<ResponsePartidaDTO> iniciarPartida(@RequestBody @Valid RequestIniciarPartidaDTO payload) {
        return ResponseEntity.status(HttpStatus.CREATED).body(partidaService.iniciarPartida(payload));
    }

    // realizarTiro
    @PostMapping("/{id}/tiros")
    public ResponseEntity<ResponseTiroDTO> realizarTiro(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(partidaService.realizarTiro(id));
    }

    // finalizarPartidaManual
    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<ResponsePartidaDTO> finalizarPartidaManual(@PathVariable Long id) {
        return ResponseEntity.ok(partidaService.finalizarPartidaManual(id));
    }
}
