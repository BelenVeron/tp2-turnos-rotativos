package com.tp2.turnosrotativos.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tp2.turnosrotativos.entities.Jornada;
import com.tp2.turnosrotativos.enums.TipoJornadaEnum;

@Repository
public interface JornadaRepository extends JpaRepository<Jornada, Long>{

	List<Jornada> findByEmpleadoId(Long empleadoId);

	List<Jornada> findByFechaAndTipoJornadaTipo(LocalDate fecha, TipoJornadaEnum tipoJornadaEnum);

	List<Jornada> findByFechaAndEmpleadoDni(LocalDate date, String dni);

	List<Jornada> findByFechaAndEmpleadoId(LocalDate date, Long id);

}
