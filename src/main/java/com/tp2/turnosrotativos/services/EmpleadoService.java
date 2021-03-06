package com.tp2.turnosrotativos.services;

import java.util.List;
import java.util.Optional;

import com.tp2.turnosrotativos.entities.Empleado;

public interface EmpleadoService {
	
	public void save(Empleado empleado);
	public List<Empleado> list();
	public Optional<Empleado> findById(Long id);
	public boolean existsByDni(String dni);
	public boolean existsById(Long empleadoId);
	public Empleado findByDni(String dni);
	public void delete(Long id);

}
