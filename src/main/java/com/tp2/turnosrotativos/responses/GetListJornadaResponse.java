package com.tp2.turnosrotativos.responses;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tp2.turnosrotativos.entities.TipoJornada;
import com.tp2.turnosrotativos.enums.TipoJornadaEnum;

public class GetListJornadaResponse {
	
	private Long id;
	
	@JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate fecha;
	
	@JsonFormat(pattern = "HH:mm:ss")
	private LocalTime horaEntrada;
	
	@JsonFormat(pattern = "HH:mm:ss")
	private LocalTime horaSalida;
	
    private TipoJornadaEnum tipoJornada;

	public GetListJornadaResponse(Long id, LocalDate fecha, LocalTime horaEntrada, LocalTime horaSalida,
			TipoJornadaEnum tipoJornada) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.horaEntrada = horaEntrada;
		this.horaSalida = horaSalida;
		this.tipoJornada = tipoJornada;
	}

	public GetListJornadaResponse() {
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public LocalTime getHoraEntrada() {
		return horaEntrada;
	}

	public void setHoraEntrada(LocalTime horaEntrada) {
		this.horaEntrada = horaEntrada;
	}

	public LocalTime getHoraSalida() {
		return horaSalida;
	}

	public void setHoraSalida(LocalTime horaSalida) {
		this.horaSalida = horaSalida;
	}

	public TipoJornadaEnum getTipoJornada() {
		return tipoJornada;
	}

	public void setTipoJornada(TipoJornadaEnum tipoJornada) {
		this.tipoJornada = tipoJornada;
	}

    
}
