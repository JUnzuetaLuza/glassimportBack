package isil.pe.glassimport.repository;

import isil.pe.glassimport.entity.Reserva;
import isil.pe.glassimport.entity.enums.EstadoReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByUserId(Long userId);

    List<Reserva> findByAutomovilId(Long automovilId);

    // Cambiado de String a EstadoReserva
    List<Reserva> findByEstado(EstadoReserva estado);

    List<Reserva> findByUserIdAndEstado(Long userId, EstadoReserva estado);

    long countByEstado(EstadoReserva estado);

    long countByUserId(Long userId);

    long countByAutomovilId(Long automovilId);

    // Método que necesita tu Scheduler
    List<Reserva> findAllByFechaBetweenAndEstado(LocalDateTime inicio, LocalDateTime fin, EstadoReserva estado);
}
