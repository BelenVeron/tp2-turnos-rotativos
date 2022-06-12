package com.tp2.turnosrotativos.validators;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tp2.turnosrotativos.entities.Jornada;
import com.tp2.turnosrotativos.enums.TipoJornadaEnum;
import com.tp2.turnosrotativos.requests.PostJornadaRequest;

public class JornadaValidator {

	
	// retorna true si es valido
	public boolean isValid(List<Jornada> listDay, PostJornadaRequest jornadaDTO) {
		
		// si ya ha cargado el dia
		if(!listDay.isEmpty()) {
			
			// si no es turno normal ni turno extra no se puede agregar si ya el dia tiene una jornada guardada
			if (!jornadaDTO.getTipo().equals(TipoJornadaEnum.TURNO_NORMAL.description) 
					&& !jornadaDTO.getTipo().equals(TipoJornadaEnum.TURNO_EXTRA.description)){
				return false;
			}
			
			// o si ya tiene cargado 2 elementos, es decir turno extra y normal juntos		
			if (listDay.size() == 2) {
				return false;
			}
			
			// o si contiene el mismo tipo jornada que el que se esta queriendo ingresar
			if (listDay.stream()
					.filter(element -> element.getTipoJornada().getTipo().description.equals(jornadaDTO.getTipo()))
					.findFirst()
					.isPresent()
					) {
				return false;
			}
		
		}
					
		return true;
	}

}
