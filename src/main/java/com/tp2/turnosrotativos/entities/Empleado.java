package com.tp2.turnosrotativos.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Empleado {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull
	@Size(max = 100)
	private String nombre;
	@NotNull
	@Size(max = 100)
	private String apellido;
	@Column(unique=true)
	@NotNull
	@Size(max = 100)
	private String dni;
	
	public Empleado(@NotNull @Size(max = 100) String nombre, @NotNull @Size(max = 100) String apellido,
			@NotNull @Size(max = 100) String dni) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
	}

	public Empleado() {
	}
	
	

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
