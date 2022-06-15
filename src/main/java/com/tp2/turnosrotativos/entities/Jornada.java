package com.tp2.turnosrotativos.entities;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Jornada {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	@JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate fecha;
	
	@JsonFormat(pattern = "HH:mm:ss")
	private LocalTime horaEntrada;
	
	@JsonFormat(pattern = "HH:mm:ss")
	private LocalTime horaSalida;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "empleado_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
    private Empleado empleado;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tipo_jornada_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
    private TipoJornada tipoJornada;

	public Jornada(@NotNull TipoJornada tipoJornada, LocalDate fecha, LocalTime horaEntrada, LocalTime horaSalida,
			Empleado empleado) {
		this.tipoJornada = tipoJornada;
		this.fecha = fecha;
		this.horaEntrada = horaEntrada;
		this.horaSalida = horaSalida;
		this.empleado = empleado;
	}

	public Jornada() {
	}

	
	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoJornada getTipoJornada() {
		return tipoJornada;
	}

	public void setTipoJornada(TipoJornada tipoJornada) {
		this.tipoJornada = tipoJornada;
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

	@Override
	public String toString() {
		return "Jornada [id=" + id + ", fecha=" + fecha + ", horaEntrada=" + horaEntrada + ", horaSalida=" + horaSalida
				+ ", empleado=" + empleado + ", tipoJornada=" + tipoJornada + "]";
	}
	
	

}
