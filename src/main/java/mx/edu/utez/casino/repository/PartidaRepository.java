package mx.edu.utez.casino.repository;

import mx.edu.utez.casino.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Long> {
    List<Partida> findByJugadorIdOrderByFechaDesc(Long jugadorId);
}
