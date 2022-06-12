package com.tp2.turnosrotativos.responses;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tp2.turnosrotativos.entities.TipoJornada;

public class GetListJornadaResponse {
	
	@JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate fecha;
	
	@JsonFormat(pattern = "HH:mm:ss")
	private LocalTime horaEntrada;
	
	@JsonFormat(pattern = "HH:mm:ss")
	private LocalTime horaSalida;
	
    private String tipoJornada;

	public GetListJornadaResponse(LocalDate fecha, LocalTime horaEntrada, LocalTime horaSalida,
			String tipoJornada) {
		super();
		this.fecha = fecha;
		this.horaEntrada = horaEntrada;
		this.horaSalida = horaSalida;
		this.tipoJornada = tipoJornada;
	}

	public GetListJornadaResponse() {
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

	public String getTipoJornada() {
		return tipoJornada;
	}

	public void setTipoJornada(String tipoJornada) {
		this.tipoJornada = tipoJornada;
	}

    
}
