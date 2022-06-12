package com.tp2.turnosrotativos.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.tp2.turnosrotativos.entities.Jornada;
import com.tp2.turnosrotativos.requests.PostJornadaRequest;
import com.tp2.turnosrotativos.validators.LaboralDay;

public interface JornadaService {
	
	public void save(Jornada jornada);
	public List<Jornada> list(Long empleadoId);
	public Optional<Jornada> findById(Long jornadaId);
	public List<Jornada> findByFechaAndEmpleadoId(LocalDate date, Long id);
	public List<LaboralDay> listSemanaLaboral(PostJornadaRequest jornada, Long id);

}
