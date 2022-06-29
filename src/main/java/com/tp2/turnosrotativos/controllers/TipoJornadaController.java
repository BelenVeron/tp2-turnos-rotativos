package com.tp2.turnosrotativos.controllers;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tp2.turnosrotativos.entities.TipoJornada;
import com.tp2.turnosrotativos.enums.TipoJornadaEnum;
import com.tp2.turnosrotativos.responses.PostCreateTipoJornadaRequest;
import com.tp2.turnosrotativos.services.TipoJornadaService;
import com.tp2.turnosrotativos.validators.TipoJornadaValidator;

@CrossOrigin(origins = "https://turnos-rotativos.herokuapp.com")
@Controller
@RequestMapping(path="/tipo-jornada")
public class TipoJornadaController {
	

	@Autowired
	private TipoJornadaService service;
	
	private TipoJornadaEnum tipo;
	TipoJornadaValidator validator = new TipoJornadaValidator();
	
	@PostMapping("/add")
	public ResponseEntity<?> create(@RequestBody PostCreateTipoJornadaRequest tipoJornadaDTO){
		
		// validaciones de tipo jornada
		String mensaje = validator.isValid(tipoJornadaDTO);
		if (!mensaje.equals("isValid")) {
			return new ResponseEntity(mensaje, HttpStatus.BAD_REQUEST);
		}
		
		// validar si ya fue agregado para que no se repita el tipo
		if (service.existsByTipo(tipoJornadaDTO.getTipo())) {
			return new ResponseEntity("Este tipo de jornada ya fue agregado", HttpStatus.BAD_REQUEST);
		}
		
		
		TipoJornada tipoJornada = new TipoJornada();
		tipoJornada.setTipo(tipo.valueOfDescription(tipoJornadaDTO.getTipo()));
		service.save(tipoJornada);
		return new ResponseEntity(tipoJornada, HttpStatus.OK);
	}
	
	@GetMapping("/list-tipo-jornadas")
	public ResponseEntity<List<TipoJornada>> listAll(){
		List<TipoJornada> list = service.list();
		return new ResponseEntity(list, HttpStatus.OK);
	}
	
	/*
	 * Elimina el tipo de jornada
	 * */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteTipoJornada(@PathVariable("id") Long id){
		if (!service.existsById(id)) {
			return new ResponseEntity("No se encuentra el tipo de jornada cargado", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		service.deleteById(id);
		
		return new ResponseEntity("El tipo de jornada fue eliminado", HttpStatus.OK);
	}

}
