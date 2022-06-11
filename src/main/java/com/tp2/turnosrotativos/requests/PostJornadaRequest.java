package com.tp2.turnosrotativos.requests;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PostJornadaRequest {
	
	private String tipo;
	
	@JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate fecha;
	
	@JsonFormat(pattern = "HH:mm:ss")
	private LocalTime horaEntrada;
	
	@JsonFormat(pattern = "HH:mm:ss")
	private LocalTime horaSalida;
	
	

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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
	
	

}
