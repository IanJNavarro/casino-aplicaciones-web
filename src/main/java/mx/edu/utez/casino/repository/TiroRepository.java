package mx.edu.utez.casino.repository;

import mx.edu.utez.casino.model.Tiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TiroRepository extends JpaRepository<Tiro, Long> {
}
