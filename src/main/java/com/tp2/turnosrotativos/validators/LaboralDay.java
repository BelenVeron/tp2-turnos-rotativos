package com.tp2.turnosrotativos.validators;

public class LaboralDay {
	
	private int dayOfWeek;
	private Float hours;
	
	public LaboralDay(int dayOfWeek, Float hours) {
		super();
		this.dayOfWeek = dayOfWeek;
		this.hours = hours;
	}
	
	public LaboralDay() {
	}
	
	public int getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public Float getHours() {
		return hours;
	}
	public void setHours(Float hours) {
		this.hours = hours;
	}
	
	

}
