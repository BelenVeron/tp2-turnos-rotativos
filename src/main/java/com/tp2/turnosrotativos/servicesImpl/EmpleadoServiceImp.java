package com.tp2.turnosrotativos.servicesImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tp2.turnosrotativos.entities.Empleado;
import com.tp2.turnosrotativos.repositories.EmpleadoRepository;
import com.tp2.turnosrotativos.services.EmpleadoService;

@Service
@Transactional
public class EmpleadoServiceImp implements EmpleadoService {
	
	@Autowired
	EmpleadoRepository repository;

	@Override
	public void save(Empleado empleado) {
		repository.save(empleado);
	}

	@Override
	public List<Empleado> list() {
		return repository.findAll();
	}

	@Override
	public Optional<Empleado> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public boolean existsByDni(String dni) {
		return repository.existsByDni(dni);
	}

	@Override
	public boolean existsById(Long empleadoId) {
		return repository.existsById(empleadoId);
	}

	@Override
	public Empleado findByDni(String dni) {
		return repository.findByDni(dni);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

}
