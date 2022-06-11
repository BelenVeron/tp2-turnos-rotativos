package com.tp2.turnosrotativos.services;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.tp2.turnosrotativos.entities.Jornada;

public interface JornadaService {
	
	public void save(Jornada jornada);
	public List<Jornada> list(Long empleadoId, Pageable pageable);

}
