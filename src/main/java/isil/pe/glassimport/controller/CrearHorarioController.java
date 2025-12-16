package isil.pe.glassimport.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import isil.pe.glassimport.dto.request.HorarioRequestDto;
import isil.pe.glassimport.dto.response.HorarioResponseDto;
import isil.pe.glassimport.services.CrearHorarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/newhorarios")
@Tag(
        name = "Horarios",
        description = "Endpoints para la gestión de horarios (solo hora)"
)
@SecurityRequirement(
        name = "Bearer Authentication"
)
public class CrearHorarioController {

    private final CrearHorarioService crearHorarioService;

    public CrearHorarioController(CrearHorarioService crearHorarioService) {
        this.crearHorarioService = crearHorarioService;
    }

    @Operation(summary = "Crear un nuevo horario")
    @PostMapping
    public ResponseEntity<HorarioResponseDto> createHorario(@RequestBody HorarioRequestDto requestDto) {
        try {
            HorarioResponseDto created = crearHorarioService.createHorario(requestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "Listar todos los horarios")
    @GetMapping
    public ResponseEntity<List<HorarioResponseDto>> getAllHorarios() {
        List<HorarioResponseDto> horarios = crearHorarioService.getAllHorarios();
        return ResponseEntity.ok(horarios);
    }

    @Operation(summary = "Obtener horario por ID")
    @GetMapping("/{id}")
    public ResponseEntity<HorarioResponseDto> getHorarioById(@PathVariable Long id) {
        try {
            HorarioResponseDto horario = crearHorarioService.getHorarioById(id);
            return ResponseEntity.ok(horario);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Buscar horario por hora exacta")
    @GetMapping("/hora/{hora}")
    public ResponseEntity<HorarioResponseDto> getHorarioByHora(@PathVariable String hora) {
        try {
            // Convertir String "HH:mm" a LocalTime
            LocalTime horaTime = LocalTime.parse(hora);
            HorarioResponseDto horario = crearHorarioService.getHorarioByHora(horaTime);
            return ResponseEntity.ok(horario);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
