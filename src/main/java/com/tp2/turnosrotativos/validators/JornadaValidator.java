package com.tp2.turnosrotativos.validators;

import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tp2.turnosrotativos.entities.Jornada;
import com.tp2.turnosrotativos.enums.TipoJornadaEnum;
import com.tp2.turnosrotativos.requests.PostJornadaRequest;
import static com.tp2.turnosrotativos.utils.Util.getHours;
import static com.tp2.turnosrotativos.utils.Util.getSemanalHours;


public class JornadaValidator {
	
	// valicacion en /jornada/add/{dni}
	public String isValidAddJornada(List<Jornada> listDay, 
			PostJornadaRequest jornadaDTO, List<JornadasWeek> listJornadaSemanal, 
			int cantPorTurno, List<LaboralDay> listLaboralDay) {
		
		String mensaje = "";
		
		if (!isValidJornada(listDay, jornadaDTO)) {
			return "La jornada para este dia ya fue cargado";
		}
		
		// validacion no puede haber mas de 2 empleado por turno
		if (cantPorTurno == 2) {
			return "Ya tiene 2 empleados para esta fecha con el tipo de jornada: ";
		}
		
		mensaje = isTipoJornada(jornadaDTO, listJornadaSemanal);
		if (!mensaje.equals("isValid")) {
			return mensaje;
		}
		
		// si supera las 48 horas semanales
		if (getSemanalHours(listLaboralDay) > 48){
			return "Se superan la cantidad de horas semanal, cargado: " + getSemanalHours(listLaboralDay);
		}
		
		// validacion de horas
		mensaje = isValidHoras(listDay, jornadaDTO);
		if (!mensaje.equals("isValid")) {
			return mensaje;
		}
		
		// si no esta vacio ver si la suma no mas de 12 horas
		if(!listDay.isEmpty() && moreThan12(listDay, jornadaDTO)) {
			return "La suma de horas de turno extra y normal no puede superar 12 horas";
		}
		
		return "isValid";
	}
	
	// validacion en update jornada
	public String isValidUpdateJornada(List<Jornada> listDay, PostJornadaRequest jornadaDTO,
			List<JornadasWeek> listJornadaSemana, int cantPorTurno, List<LaboralDay> listLaboralDay,
			Long jornadaId) {
		String mensaje = "";
		
		if (!isValidTipoJornadaUpdate(listDay, jornadaDTO, jornadaId)) {
			return "La jornada para este dia ya fue cargado";
		}
		
		// si supera las 48 horas semanales
		if (getSemanalHours(listLaboralDay) > 48){
			return "Se superan la cantidad de horas semanal, cargado: " + getSemanalHours(listLaboralDay);
		}
				
		// validacion de horas
		mensaje = isValidHoras(listDay, jornadaDTO);
		if (!mensaje.equals("isValid")) {
			return mensaje;
		}
		
		// si no esta vacio ver si la suma no mas de 12 horas
		if(!listDay.isEmpty() && moreThan12(listDay, jornadaDTO, jornadaId)) {
			return "La suma de horas de turno extra y normal no puede superar 12 horas";
		}
		
		return "isValid";
	}
	
	
	// retorna true si es valido, es diferente que isValidJornada() ya que no tiene
	// que verificar si el tipo cargado es igual al que está, ya que lo modifica
	// pero si validar si quiere cambiar el tipo
	public boolean isValidTipoJornadaUpdate(List<Jornada> listDay, PostJornadaRequest jornadaDTO, Long jornadaId) {
			
		// si tiene los 2, solo puede modificar el mismo, y no cambiar el tipo	
		if (listDay.size() == 2 &&
				listDay.stream()
				.filter(element -> element.getId() == jornadaId && !element.getTipoJornada().getTipo().description.equals(jornadaDTO.getTipo()))
				.findFirst()
				.isPresent()
				) {
			return false;
		}
				
			
		return true;
	}
	

	// retorna true si es valido
	public boolean isValidJornada(List<Jornada> listDay, PostJornadaRequest jornadaDTO) {
		
		// si ya ha cargado el dia
		if(!listDay.isEmpty()) {
			
			// si no es turno normal ni turno extra no se puede agregar si el dia ya tiene una jornada guardada
			// incluye el dia libre
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
	
	
	// retorna mensaje de error, si es valido retorna isValid
	public String isValidHoras(List<Jornada> listDay, PostJornadaRequest jornadaDTO) {
		
		// escapa de la validacion ya que al ser vacaciones o dia libre no se 
		// cargan horas
		if (jornadaDTO.getTipo().equals(TipoJornadaEnum.VACACIONES.description) 
				|| jornadaDTO.getTipo().equals(TipoJornadaEnum.DIA_LIBRE.description)){
			return "isValid";
		}
		
		// la hora de entrada debe ser menor que la de salida
		if (jornadaDTO.getHoraSalida().compareTo(jornadaDTO.getHoraEntrada()) <= 0) {
			return "La hora de salida no puede ser igual o menor a la de entrada";
		}

		// validacion de turno normal entre 6 y 8
		if (jornadaDTO.getTipo().equals(TipoJornadaEnum.TURNO_NORMAL.description) 
				&& (getHours(jornadaDTO) < 6 || getHours(jornadaDTO) > 8)) {
			return "Cantidad de horas de turno normal debe ser entre 6 y 8";
		} 
		
		// sino valida si extra es entre 2 y 6 horas
		if (jornadaDTO.getTipo().equals(TipoJornadaEnum.TURNO_EXTRA.description)
				&& (getHours(jornadaDTO) < 2 || getHours(jornadaDTO) > 6)){
			return "Cantidad de horas diarias de turno extra debe ser entre 2 y 6";
		}
		
		return "isValid";
	}
	
	
	// retorna true si es mayor de 12 horas
	public boolean moreThan12(List<Jornada> listDay, PostJornadaRequest jornadaDTO) {
		// la suma de los 2 no tiene que ser mayor de 12, ya se valida
		// anteriormente en isValidJornada() que son diferentes
		float hoursDTO = getHours(jornadaDTO); // horas ingresadas
		float hoursDia = getHours(listDay.get(0)); // horas guardadas
					
		if (hoursDTO + hoursDia > 12) {
			return true;
		}
		
		return false;
	}

	// retorna true si es mayor de 12 horas pero en update
	public boolean moreThan12(List<Jornada> listDay, PostJornadaRequest jornadaDTO, Long jornadaId) {
		// la suma de los 2 no tiene que ser mayor de 12, ya se valida
		// anteriormente en isValidJornada() que son diferentes
		float hoursDTO = getHours(jornadaDTO); // horas ingresadas
		float hoursDia = getHours(listDay.stream()
						.filter(element -> element.getId() != jornadaId)
						.findFirst().orElse(null)); // horas guardadas
					
		if (hoursDTO + hoursDia > 12) {
			return true;
		}
		
		return false;
	}


	
	public String isTipoJornada(PostJornadaRequest jornadaDTO, List<JornadasWeek> listJornadaSemanal) {
		
		// valida que el tipo jornada sea uno de los del enum
		if (Objects.isNull(TipoJornadaEnum.valueOfDescription(jornadaDTO.getTipo()))) {
			return "El tipo de jornada no coincide con los posibles tipos";
		}
		
		// valida si tiene 2 dias libres en la semana
		if (jornadaDTO.getTipo().equals(TipoJornadaEnum.DIA_LIBRE.description)){
			if (listJornadaSemanal.stream()
					.mapToInt(element -> {
						if (!element.getJornadaDay().isEmpty() && element.getJornadaDay().get(0).getTipoJornada().getTipo().equals(TipoJornadaEnum.DIA_LIBRE)) 
							return 1;
						else
							return 0;
					}).sum() == 2
					) {
				return "No se puede cargar mas de 2 dias libres por semana";
			}
		}
		
		// no se pueden agregar horas si es dia libre ni vacaciones, se setean a null
		if ((jornadaDTO.getTipo().equals(TipoJornadaEnum.VACACIONES.description) 
				|| jornadaDTO.getTipo().equals(TipoJornadaEnum.DIA_LIBRE.description))
			&& (!Objects.isNull(jornadaDTO.getHoraEntrada()) || !Objects.isNull(jornadaDTO.getHoraSalida()))
						){
			return "resetNull";			
		}
			
		return "isValid";
	}



	

}
