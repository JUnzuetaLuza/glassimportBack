package isil.pe.glassimport.services;

import isil.pe.glassimport.dto.request.HorarioRequestDto;
import isil.pe.glassimport.dto.response.HorarioResponseDto;
import isil.pe.glassimport.entity.CrearHorario;
import isil.pe.glassimport.repository.CrearHorarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CrearHorarioService {

    private final CrearHorarioRepository crearHorarioRepository;

    @Autowired
    public CrearHorarioService(CrearHorarioRepository crearHorarioRepository) {
        this.crearHorarioRepository = crearHorarioRepository;
    }

    // Crear un nuevo horario
    public HorarioResponseDto createHorario(HorarioRequestDto requestDto) {
        if (requestDto.hora() == null) {
            throw new IllegalArgumentException("La hora no puede ser nula");
        }

        if (crearHorarioRepository.existsByHora(requestDto.hora())) {
            throw new IllegalArgumentException("Ya existe un horario con la hora: " + requestDto.hora());
        }

        CrearHorario horario = CrearHorario.builder()
                .hora(requestDto.hora())
                .build();

        CrearHorario saved = crearHorarioRepository.save(horario);

        return new HorarioResponseDto(
                saved.getId(),
                saved.getHora()
        );
    }

    // Listar todos los horarios
    public List<HorarioResponseDto> getAllHorarios() {
        return crearHorarioRepository.findAll().stream()
                .map(h -> new HorarioResponseDto(h.getId(), h.getHora()))
                .collect(Collectors.toList());
    }

    // Obtener horario por ID
    public HorarioResponseDto getHorarioById(Long id) {
        CrearHorario horario = crearHorarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado con ID: " + id));

        return new HorarioResponseDto(
                horario.getId(),
                horario.getHora()
        );
    }

    // Buscar por hora exacta
    public HorarioResponseDto getHorarioByHora(java.time.LocalTime hora) {
        CrearHorario horario = crearHorarioRepository.findByHora(hora)
                .orElseThrow(() -> new RuntimeException("No se encontró horario para la hora: " + hora));

        return new HorarioResponseDto(
                horario.getId(),
                horario.getHora()
        );
    }

}
