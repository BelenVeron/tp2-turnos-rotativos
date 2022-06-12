package com.tp2.turnosrotativos.responses;

public class GetHorasCargadasResponse {
	
	private String tipo;
	private Float horasCargadas;
	
	public GetHorasCargadasResponse(String tipo, Float horasCargadas) {
		this.tipo = tipo;
		this.horasCargadas = horasCargadas;
	}

	public GetHorasCargadasResponse() {
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Float getHorasCargadas() {
		return horasCargadas;
	}

	public void setHorasCargadas(Float horasCargadas) {
		this.horasCargadas = horasCargadas;
	}

}
