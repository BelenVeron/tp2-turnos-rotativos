package com.tp2.turnosrotativos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tp2.turnosrotativos.entities.TipoJornada;
import com.tp2.turnosrotativos.enums.TipoJornadaEnum;

@Repository
public interface TipoJornadaRepository extends JpaRepository<TipoJornada, Long>{

	TipoJornada findByTipo(TipoJornadaEnum tipoJornadaEnum);

	boolean existsByTipo(TipoJornadaEnum valueOfDescription);

}
