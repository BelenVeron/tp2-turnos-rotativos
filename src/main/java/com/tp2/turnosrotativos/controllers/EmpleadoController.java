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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tp2.turnosrotativos.entities.Empleado;
import com.tp2.turnosrotativos.entities.Jornada;
import com.tp2.turnosrotativos.responses.GetHorasCargadasResponse;
import com.tp2.turnosrotativos.services.EmpleadoService;
import com.tp2.turnosrotativos.services.JornadaService;
import com.tp2.turnosrotativos.validators.DaysValidator;

import static java.time.temporal.ChronoUnit.MINUTES;;


@Controller
@RequestMapping(path="/empleado")
public class EmpleadoController {
	
	@Autowired
	private EmpleadoService empleadoService;
	
	@Autowired
	private JornadaService jornadaService;
	
	private DaysValidator daysValidator = new DaysValidator();
	
	@PostMapping("/add")
	public ResponseEntity<?> create(@RequestBody Empleado empleado){
		empleadoService.save(empleado);
		return new ResponseEntity(empleado, HttpStatus.OK);
	}
	
	@GetMapping("/list-empleados")
	public ResponseEntity<List<Empleado>> listAll(){
		List<Empleado> list = empleadoService.list();
		return new ResponseEntity(list, HttpStatus.OK);
	}
	
	@GetMapping("/list-horas-cargadas/{empleado-id}")
	public ResponseEntity<List<GetHorasCargadasResponse>> listHorasCargadas(@PathVariable("empleado-id") Long empleadoId){
		List<Jornada> jornadas = jornadaService.list(empleadoId);
		List<GetHorasCargadasResponse> response = new ArrayList<GetHorasCargadasResponse>();

		// recorre las jornadas del empleado
		jornadas.stream().forEach(jornada -> {
			// si no se cargo el tipo dentro de la respuesta, se carga
			if (response.isEmpty() || !response.stream()
				.filter(element -> element.getTipo().equals(jornada.getTipoJornada().getTipo()))
				.findFirst().isPresent()) {
				GetHorasCargadasResponse newTipo = new GetHorasCargadasResponse();
				newTipo.setHorasCargadas(daysValidator.getHours(jornada));
				newTipo.setTipo(jornada.getTipoJornada().getTipo());
				
				response.add(newTipo);
			}else {
				// si ya se cargo, se suma
				response.stream()
					.forEach(element -> {
						if(element.getTipo() == jornada.getTipoJornada().getTipo()) {
							element.setHorasCargadas(
									element.getHorasCargadas() + (daysValidator.getHours(jornada))
									);
						}
					});
			}
		});
		
		return new ResponseEntity(response, HttpStatus.OK);
	}

}
