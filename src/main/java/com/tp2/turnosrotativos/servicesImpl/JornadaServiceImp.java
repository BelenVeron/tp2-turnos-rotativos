package com.tp2.turnosrotativos.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tp2.turnosrotativos.entities.Jornada;
import com.tp2.turnosrotativos.repositories.JornadaRepository;
import com.tp2.turnosrotativos.services.JornadaService;

@Service
@Transactional
public class JornadaServiceImp implements JornadaService{
	
	@Autowired
	private JornadaRepository repository;

	@Override
	public void save(Jornada jornada) {
		repository.save(jornada);
	}

	@Override
	public List<Jornada> list(Long empleadoId) {
		return repository.findByEmpleadoId(empleadoId);
	}

}
