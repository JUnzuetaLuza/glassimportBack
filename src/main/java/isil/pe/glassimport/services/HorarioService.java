package isil.pe.glassimport.services;

import isil.pe.glassimport.entity.Horario;
import isil.pe.glassimport.repository.HorarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;

@Service
public class HorarioService {
    @Autowired
    private HorarioRepository repo;

    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private final List<String> HORAS = Arrays.asList("09:00", "10:30", "12:00", "15:00", "17:00");

    // Devuelve estado de cada horario para una fecha
    public Map<String, String> getEstadosHorarios(String fecha) {
        Map<String, String> result = new HashMap<>();
        for(String hora : HORAS) {
            String estado = repo.findByFechaAndHora(fecha, hora)
                    .map(Horario::getEstado)
                    .orElse("LIBRE");
            result.put(hora, estado);
        }
        return result;
    }

    // Bloquea un horario durante 3 minutos (EN_PROCESO)
    public boolean bloquearHorario(String fecha, String hora) {
        Optional<Horario> hOpt = repo.findByFechaAndHora(fecha, hora);
        if (hOpt.isPresent() && !"LIBRE".equals(hOpt.get().getEstado()))
            return false;
        Horario h = hOpt.orElse(new Horario());
        h.setFecha(fecha);
        h.setHora(hora);
        h.setEstado("EN_PROCESO");
        repo.save(h);

        scheduler.schedule(() -> {
            Horario hh = repo.findByFechaAndHora(fecha, hora).orElse(null);
            if (hh != null && "EN_PROCESO".equals(hh.getEstado())) {
                hh.setEstado("LIBRE");
                repo.save(hh);
            }
        }, 3, TimeUnit.MINUTES);

        return true;
    }

    // Reserva definitiva
    public void reservarHorario(String fecha, String hora) {
        Horario h = repo.findByFechaAndHora(fecha, hora).orElse(new Horario());
        h.setFecha(fecha);
        h.setHora(hora);
        h.setEstado("OCUPADO");
        repo.save(h);
    }
}
