package com.tp2.turnosrotativos.validators;

import com.tp2.turnosrotativos.requests.PostAddEmpleadoRequest;
import static com.tp2.turnosrotativos.utils.Util.stringEmpty;

public class EmpleadoValidator {

	
	public String isValid(PostAddEmpleadoRequest empleadoDTO) {
		
		// 3 condicionales que validan si los campos fueron cargados
		if (stringEmpty(empleadoDTO.getNombre())) {
			return "El nombre es obligatorio";
		}
		if (stringEmpty(empleadoDTO.getApellido())) {
			return "El apellido es obligatorio";
		}
		if (stringEmpty(empleadoDTO.getDni())) {
			return "El dni es obligatorio";
		}
		
		// dni solo puede ingresar numeros
		if (!empleadoDTO.getDni().matches("[0-9]+")) {
			return "El dni solo debe contener numeros";
		}
		// dni entre 7 y 8
		if (empleadoDTO.getDni().length() < 7 || empleadoDTO.getDni().length() > 8) {
			return "dni debe contener 7 u 8 numeros";
		}
		
		return "isValid";
	}
	

}
