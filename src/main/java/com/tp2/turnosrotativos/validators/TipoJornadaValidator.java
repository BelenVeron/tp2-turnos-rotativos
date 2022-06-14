package com.tp2.turnosrotativos.validators;

import com.tp2.turnosrotativos.responses.PostCreateTipoJornadaRequest;
import static com.tp2.turnosrotativos.utils.Util.stringEmpty;

import java.util.Objects;


import com.tp2.turnosrotativos.enums.TipoJornadaEnum;

public class TipoJornadaValidator {

	public String isValid(PostCreateTipoJornadaRequest tipoJornadaDTO) {
		
		// Validan si el campo fue cargado
		if (stringEmpty(tipoJornadaDTO.getTipo())) {
			return "El campo tipo de jornada es obligatorio";
		}
		
		// validar si existe dentro del TipoJornadaEnum
		if (Objects.isNull(TipoJornadaEnum.valueOfDescription(tipoJornadaDTO.getTipo()))) {
			return "No existe ese tipo de jornada";
		}
				
		return "isValid";
	}

}
