package com.tp2.turnosrotativos.responses;

import com.tp2.turnosrotativos.enums.TipoJornadaEnum;

public class GetHorasCargadasResponse {
	
	private TipoJornadaEnum tipo;
	private Float horasCargadas;
	
	public GetHorasCargadasResponse(TipoJornadaEnum tipo, Float horasCargadas) {
		this.tipo = tipo;
		this.horasCargadas = horasCargadas;
	}

	public GetHorasCargadasResponse() {
	}

	public TipoJornadaEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoJornadaEnum tipo) {
		this.tipo = tipo;
	}

	public Float getHorasCargadas() {
		return horasCargadas;
	}

	public void setHorasCargadas(Float horasCargadas) {
		this.horasCargadas = horasCargadas;
	}

}
