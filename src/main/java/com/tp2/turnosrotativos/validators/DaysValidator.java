package com.tp2.turnosrotativos.validators;

import static java.time.temporal.ChronoUnit.MINUTES;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;

import com.tp2.turnosrotativos.entities.Jornada;
import com.tp2.turnosrotativos.requests.PostJornadaRequest;
import com.tp2.turnosrotativos.services.JornadaService;

public class DaysValidator {
	
	private Float hours = new Float(0);
	
	// obtiene las horas semanales, suma las horas de todos los dias de la semana
	public Float getSemanalHours (List<LaboralDay> list) {

		list.stream().forEach(element -> {
			if (Objects.nonNull(element)) {
				hours = hours + element.getHours();
			}
		});
		
		return hours;
	}
	
	// obtiene las horas del dia laboral
	public Float getHours (Jornada jornada) {
		return (float) (MINUTES.between(jornada.getHoraEntrada(), jornada.getHoraSalida()))/60;
	}
	
	// obtiene las horas del dia laboral
	public Float getHours (PostJornadaRequest jornada) {
		return (float) (MINUTES.between(jornada.getHoraEntrada(), jornada.getHoraSalida()))/60;
	}

}
