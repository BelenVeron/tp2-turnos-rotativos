package com.tp2.turnosrotativos.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tp2.turnosrotativos.entities.Jornada;
import com.tp2.turnosrotativos.enums.TipoJornadaEnum;
import com.tp2.turnosrotativos.requests.PostJornadaRequest;
import com.tp2.turnosrotativos.responses.GetListJornadaResponse;
import com.tp2.turnosrotativos.services.EmpleadoService;
import com.tp2.turnosrotativos.services.JornadaService;
import com.tp2.turnosrotativos.services.TipoJornadaService;
import com.tp2.turnosrotativos.validators.JornadaValidator;
import com.tp2.turnosrotativos.validators.LaboralDay;
import static com.tp2.turnosrotativos.utils.Util.getSemanalHours;

@Controller
@RequestMapping(path="/jornada")
public class JornadaController {
	
	@Autowired
	private JornadaService jornadaService;
	
	@Autowired
	private EmpleadoService empleadoService;
	
	@Autowired
	private TipoJornadaService tipoJornadaService;
	
	private JornadaValidator jornadaValidator = new JornadaValidator();
	private TipoJornadaEnum tipo;
	
	/*
	 * Agrega jornada para un empleado
	 * */
	@PostMapping("/add/{dni}")
	public ResponseEntity<?> create(@PathVariable("dni") String dni, @RequestBody PostJornadaRequest jornadaDTO){
		List<LaboralDay> listLaboralDay = jornadaService.listSemanaLaboral(jornadaDTO, dni);
		
		List<Jornada> listDay = jornadaService.findByFechaAndEmpleadoDni(jornadaDTO.getFecha(), dni);
		if (!jornadaValidator.isValidJornada(listDay, jornadaDTO)) {
			return new ResponseEntity("La jornada para este dia ya fue cargado", HttpStatus.BAD_REQUEST);
		}
		
		// validacion no puede haber mas de 2 empleado por turno
		if (jornadaService.countDateAndTipo(jornadaDTO.getFecha(), jornadaDTO.getTipo()) == 2) {
			return new ResponseEntity("Ya tiene 2 empleados para esta fecha con el tipo de jornada: " + jornadaDTO.getTipo(), HttpStatus.BAD_REQUEST);
		}
		
		// validacion del dia libre
		String mensaje = jornadaValidator.isTipoJornada(jornadaDTO, jornadaService.listJornadaSemanal(jornadaDTO.getFecha(), dni));
		if (!mensaje.equals("isValid")) {
			// resetea las horas a null si son dia libre o vacaciones
			if (mensaje.equals("resetNull")) {
				jornadaDTO.setHoraEntrada(null);
				jornadaDTO.setHoraSalida(null);
			} else {
				return new ResponseEntity(mensaje, HttpStatus.BAD_REQUEST);
			}
		}
		
		// si supera las 48 horas semanales
		if (getSemanalHours(listLaboralDay) > 48){
			return new ResponseEntity("Se superan la cantidad de horas semanal, cargado: " + getSemanalHours(listLaboralDay), HttpStatus.BAD_REQUEST);
		}
		
		// validacion de horas
		mensaje = jornadaValidator.isValidHoras(listDay, jornadaDTO);
		if (!mensaje.equals("isValid")) {
			return new ResponseEntity(mensaje, HttpStatus.BAD_REQUEST);
		}
		
		
		
		Jornada jornada = new Jornada();
		jornada.setEmpleado(empleadoService.findByDni(dni));
		jornada.setFecha(jornadaDTO.getFecha());
		jornada.setHoraEntrada(jornadaDTO.getHoraEntrada());
		jornada.setHoraSalida(jornadaDTO.getHoraSalida());
		System.out.println(jornadaDTO.getTipo());
		jornada.setTipoJornada(tipoJornadaService.findByTipo(tipo.valueOfDescription(jornadaDTO.getTipo())));
		jornadaService.save(jornada);

		return new ResponseEntity(jornada, HttpStatus.OK);
	}
	
	@GetMapping("/list-jornadas/{dni}")
	public ResponseEntity<List<Jornada>> listAll(@PathVariable("dni") String dni){
		Long empleadoId = empleadoService.findByDni(dni).getId();
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
