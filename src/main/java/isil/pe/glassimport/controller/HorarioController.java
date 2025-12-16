package isil.pe.glassimport.controller;

import isil.pe.glassimport.services.HorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/horarios")
@CrossOrigin(origins = "http://localhost:5173")
public class HorarioController {
    @Autowired
    private HorarioService horarioService;

    @GetMapping
    public Map<String, String> getHorarios(@RequestParam String fecha) {
        return horarioService.getEstadosHorarios(fecha);
    }

    @PostMapping("/lock")
    public ResponseEntity<?> lockHorario(@RequestBody Map<String, String> body) {
        String fecha = body.get("fecha");
        String hora = body.get("hora");
        boolean ok = horarioService.bloquearHorario(fecha, hora);
        if (ok) return ResponseEntity.ok().build();
        return ResponseEntity.status(409).body("Ya ocupado");
    }
}
