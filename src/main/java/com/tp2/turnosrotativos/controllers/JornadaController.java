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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tp2.turnosrotativos.entities.Empleado;
import com.tp2.turnosrotativos.entities.Jornada;
import com.tp2.turnosrotativos.requests.PostJornadaRequest;
import com.tp2.turnosrotativos.responses.GetListJornadaResponse;
import com.tp2.turnosrotativos.services.EmpleadoService;
import com.tp2.turnosrotativos.services.JornadaService;
import com.tp2.turnosrotativos.services.TipoJornadaService;

@Controller
@RequestMapping(path="/jornada")
public class JornadaController {
	
	@Autowired
	private JornadaService jornadaService;
	
	@Autowired
	private EmpleadoService empleadoService;
	
	@Autowired
	private TipoJornadaService tipoJornadaService;
	
	@PostMapping("/add/{empleado-id}")
	public ResponseEntity<?> create(@PathVariable("empleado-id") Long empleadoId, @RequestBody PostJornadaRequest jornadaDTO){
		Jornada jornada = new Jornada();
		jornada.setEmpleado(empleadoService.findById(empleadoId).get());
		jornada.setFecha(jornadaDTO.getFecha());
		jornada.setHoraEntrada(jornadaDTO.getHoraEntrada());
		jornada.setHoraSalida(jornadaDTO.getHoraSalida());
		jornada.setTipoJornada(tipoJornadaService.findByTipo(jornadaDTO.getTipo()));
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
					element.setFecha(jornada.getFecha());
					element.setHoraEntrada(jornada.getHoraEntrada());
					element.setHoraSalida(jornada.getHoraSalida());
					element.setTipoJornada(jornada.getTipoJornada().getTipo());
					list.add(element);
				});
		return new ResponseEntity(list, HttpStatus.OK);
	}

}
