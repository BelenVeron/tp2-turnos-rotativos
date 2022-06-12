package com.tp2.turnosrotativos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tp2.turnosrotativos.entities.Jornada;

@Repository
public interface JornadaRepository extends JpaRepository<Jornada, Long>{

	List<Jornada> findByEmpleadoId(Long empleadoId);

}
