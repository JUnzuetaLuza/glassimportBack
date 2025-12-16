package isil.pe.glassimport.repository;

import isil.pe.glassimport.entity.CrearHorario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface CrearHorarioRepository extends JpaRepository<CrearHorario, Long> {

    // Buscar por hora exacta
    Optional<CrearHorario> findByHora(LocalTime hora);

    // Verificar si ya existe una hora
    boolean existsByHora(LocalTime hora);

}
