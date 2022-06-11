package com.tp2.turnosrotativos.services;

import java.util.List;

import com.tp2.turnosrotativos.entities.TipoJornada;

public interface TipoJornadaService {

	public void save(TipoJornada tipoJornada);
	public List<TipoJornada> list();
	public TipoJornada findByTipo(String tipo);
}
