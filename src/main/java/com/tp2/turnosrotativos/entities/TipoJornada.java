package com.tp2.turnosrotativos.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.tp2.turnosrotativos.enums.TipoJornadaEnum;

@Entity
public class TipoJornada{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	private TipoJornadaEnum tipo;

	
	public TipoJornada(@NotNull TipoJornadaEnum tipo) {
		super();
		this.tipo = tipo;
	}

	public TipoJornada() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoJornadaEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoJornadaEnum tipo) {
		this.tipo = tipo;
	}
	
}
