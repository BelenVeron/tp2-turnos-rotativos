package com.tp2.turnosrotativos.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tp2.turnosrotativos.entities.Empleado;
import com.tp2.turnosrotativos.entities.Jornada;
import com.tp2.turnosrotativos.enums.TipoJornadaEnum;
import com.tp2.turnosrotativos.requests.PostJornadaRequest;
import com.tp2.turnosrotativos.responses.GetListJornadaResponse;
import com.tp2.turnosrotativos.services.EmpleadoService;
import com.tp2.turnosrotativos.services.JornadaService;
import com.tp2.turnosrotativos.services.TipoJornadaService;
import com.tp2.turnosrotativos.validators.DaysValidator;
import com.tp2.turnosrotativos.validators.JornadaValidator;
import com.tp2.turnosrotativos.validators.LaboralDay;

@Controller
@RequestMapping(path="/jornada")
public class JornadaController {
	
	@Autowired
	private JornadaService jornadaService;
	
	@Autowired
	private EmpleadoService empleadoService;
	
	@Autowired
	private TipoJornadaService tipoJornadaService;
	
	private DaysValidator daysValidator = new DaysValidator();
	private JornadaValidator jornadaValidator = new JornadaValidator();
	private TipoJornadaEnum tipo;
	
	/*
	 * Agrega jornada para un empleado
	 * */
	@PostMapping("/add/{empleado-id}")
	public ResponseEntity<?> create(@PathVariable("empleado-id") Long empleadoId, @RequestBody PostJornadaRequest jornadaDTO){
		List<LaboralDay> listLaboralDay = jornadaService.listSemanaLaboral(jornadaDTO, empleadoId);
		
		List<Jornada> listDay = jornadaService.findByFechaAndEmpleadoId(jornadaDTO.getFecha(), empleadoId);
		if (!jornadaValidator.isValid(listDay, jornadaDTO)) {
			return new ResponseEntity("La jornada para este dia ya fue cargado", HttpStatus.ALREADY_REPORTED);
		}
		
			
		/*
			// si supera las 48 horas semanales
			if (daysValidator.getSemanalHours(listLaboralDay) > 48){
				return new ResponseEntity("Se superan la cantidad de horas semanal, cargado: " + daysValidator.getSemanalHours(listLaboralDay), HttpStatus.NOT_ACCEPTABLE);
			}
			// validacion de turno normal entre 6 y 8
			if (jornadaDTO.getTipo().equals(tipo.TURNO_NORMAL.description) 
					&& (daysValidator.getHours(jornadaDTO) < 6 || daysValidator.getHours(jornadaDTO) > 8)) {
				return new ResponseEntity("Cantidad de horas diarias de turno normal debe ser entre 6 y 8", HttpStatus.NOT_ACCEPTABLE);
			} 
			// sino valida si extra es entre 2 y 6 horas
			else if (jornadaDTO.getTipo().equals(tipo.TURNO_EXTRA.description)
					&& (daysValidator.getHours(jornadaDTO) < 2 || daysValidator.getHours(jornadaDTO) > 6)){
				return new ResponseEntity("Cantidad de horas diarias de turno extra debe ser entre 2 y 6", HttpStatus.NOT_ACCEPTABLE);
			}*/
		
		Jornada jornada = new Jornada();
		jornada.setEmpleado(empleadoService.findById(empleadoId).get());
		jornada.setFecha(jornadaDTO.getFecha());
		jornada.setHoraEntrada(jornadaDTO.getHoraEntrada());
		jornada.setHoraSalida(jornadaDTO.getHoraSalida());
		jornada.setTipoJornada(tipoJornadaService.findByTipo(tipo.valueOfDescription(jornadaDTO.getTipo())));
		jornadaService.save(jornada);

		return new ResponseEntity(jornada, HttpStatus.OK);
	}
	
	@GetMapping("/list-jornadas/{empleado-id}")
	public ResponseEntity<List<Jornada>> listAll(@PathVariable("empleado-id") Long empleadoId){
		List<Jornada> listJornada = jornadaService.list(empleadoId);
		List<GetListJornadaResponse> list = new ArrayList<GetListJornadaResponse>();
		listJornada.stream().forEach(
				jornada -> {
					GetListJornadaResponse element = new GetListJornadaResponse();
					element.setId(jornada.getId());
					element.setFecha(jornada.getFecha());
					element.setHoraEntrada(jornada.getHoraEntrada());
					element.setHoraSalida(jornada.getHoraSalida());
					element.setTipoJornada(jornada.getTipoJornada().getTipo());
					list.add(element);
				});
		return new ResponseEntity(list, HttpStatus.OK);
	}

	@PutMapping("/update/{jornada-id}")
	public ResponseEntity<Jornada> updateJornada(@PathVariable("jornada-id") Long jornadaId, @RequestBody PostJornadaRequest jornadaDTO){
		Jornada jornada = jornadaService.findById(jornadaId).get();
		jornada.setHoraEntrada(jornadaDTO.getHoraEntrada());
		jornada.setHoraSalida(jornadaDTO.getHoraSalida());
		
		return new ResponseEntity(jornada, HttpStatus.OK);
	}
}
