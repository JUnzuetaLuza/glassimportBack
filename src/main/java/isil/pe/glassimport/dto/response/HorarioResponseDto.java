package isil.pe.glassimport.dto.response;

import java.time.LocalTime;

public record HorarioResponseDto(
        Long id,
        LocalTime hora
) {}
