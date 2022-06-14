package com.tp2.turnosrotativos.utils;

import static java.time.temporal.ChronoUnit.MINUTES;

import java.util.List;
import java.util.Objects;

import com.tp2.turnosrotativos.entities.Jornada;
import com.tp2.turnosrotativos.requests.PostJornadaRequest;
import com.tp2.turnosrotativos.validators.LaboralDay;

public class Util {
	
	private static Float hours = new Float(0);
	 
	 
	public static boolean stringEmpty (String value) {
		return Objects.isNull(value) || value.isEmpty();
	}
	 

		
	// obtiene las horas semanales, suma las horas de todos los dias de la semana
	public static Float getSemanalHours (List<LaboralDay> list) {

		list.stream().forEach(element -> {
			if (Objects.nonNull(element)) {
				hours = hours + element.getHours();
			}
		});
			
		return hours;
	}
		
	// obtiene las horas del dia laboral
	public static Float getHours (Jornada jornada) {
		return (float) (MINUTES.between(jornada.getHoraEntrada(), jornada.getHoraSalida()))/60;
	}
		
	// obtiene las horas del dia laboral
	public static Float getHours (PostJornadaRequest jornada) {
		return (float) (MINUTES.between(jornada.getHoraEntrada(), jornada.getHoraSalida()))/60;
	}

}
