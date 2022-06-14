package com.tp2.turnosrotativos.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.tp2.turnosrotativos.entities.Jornada;
import com.tp2.turnosrotativos.requests.PostJornadaRequest;
import com.tp2.turnosrotativos.validators.JornadasWeek;
import com.tp2.turnosrotativos.validators.LaboralDay;

public interface JornadaService {
	
	public void save(Jornada jornada);
	public List<Jornada> list(Long empleadoId);
	public Optional<Jornada> findById(Long jornadaId);
	public List<Jornada> findByFechaAndEmpleadoId(LocalDate date, Long id);
	List<JornadasWeek> listJornadaSemanal(LocalDate dateDTO, String dni);
	public int countDateAndTipo(LocalDate fecha, String tipo);
	public List<LaboralDay> listSemanaLaboral(PostJornadaRequest jornadaDTO, String dni);
	public List<Jornada> findByFechaAndEmpleadoDni(LocalDate fecha, String dni);

}
