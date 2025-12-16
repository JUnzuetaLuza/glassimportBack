package isil.pe.glassimport.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Table(name = "horarios")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrearHorario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Solo la hora (ej: 09:00, 14:30)
    @Column(nullable = false)
    private LocalTime hora;

}
