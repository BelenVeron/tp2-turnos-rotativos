package com.tp2.turnosrotativos.validators;

import java.util.List;

import com.tp2.turnosrotativos.entities.Jornada;

public class JornadasWeek {
	
	private int dayOfWeek;
	private List<Jornada> jornadaDay;
	
	public JornadasWeek(int dayOfWeek, List<Jornada> jornadaDay) {
		super();
		this.dayOfWeek = dayOfWeek;
		this.jornadaDay = jornadaDay;
	}
	
	public JornadasWeek() {
	}

	public int getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public List<Jornada> getJornadaDay() {
		return jornadaDay;
	}

	public void setJornadaDay(List<Jornada> jornadaDay) {
		this.jornadaDay = jornadaDay;
	}
	
	

}
