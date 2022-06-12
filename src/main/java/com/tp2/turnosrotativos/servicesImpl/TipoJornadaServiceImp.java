package com.tp2.turnosrotativos.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tp2.turnosrotativos.entities.TipoJornada;
import com.tp2.turnosrotativos.enums.TipoJornadaEnum;
import com.tp2.turnosrotativos.repositories.TipoJornadaRepository;
import com.tp2.turnosrotativos.services.TipoJornadaService;

@Service
@Transactional
public class TipoJornadaServiceImp implements TipoJornadaService{
	
	@Autowired
	private TipoJornadaRepository repository;

	@Override
	public void save(TipoJornada tipoJornada) {
		repository.save(tipoJornada);
	}

	@Override
	public List<TipoJornada> list() {
		return repository.findAll();
	}

	@Override
	public TipoJornada findByTipo(TipoJornadaEnum tipoJornadaEnum) {
		return repository.findByTipo(tipoJornadaEnum);
	}

}
