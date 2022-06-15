package com.tp2.turnosrotativos.responses;

import com.tp2.turnosrotativos.enums.TipoJornadaEnum;

public class GetHorasCargadasResponse {
	
	private TipoJornadaEnum tipo;
	private float horasCargadas;
	

	public void setHorasCargadas(float horasCargadas) {
		this.horasCargadas = horasCargadas;
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
