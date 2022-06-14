package com.tp2.turnosrotativos.enums;

public enum TipoJornadaEnum {
	
	TURNO_EXTRA("Turno Extra"),
	VACACIONES("Vacaciones"),
	DIA_LIBRE("Dia Libre"),
	TURNO_NORMAL("Turno Normal");
	
	public final String description;

	private TipoJornadaEnum(String description) {
		this.description = description;
	}
	
	public static TipoJornadaEnum valueOfDescription(String description) {
	    for (TipoJornadaEnum e : values()) {
	        if (e.description.equals(description)) {
	            return e;
	        }
	    }
	    return null;
	}
	
	

}
