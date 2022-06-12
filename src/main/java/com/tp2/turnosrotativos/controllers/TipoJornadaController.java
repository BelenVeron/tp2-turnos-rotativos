package com.tp2.turnosrotativos.controllers;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tp2.turnosrotativos.entities.TipoJornada;
import com.tp2.turnosrotativos.enums.TipoJornadaEnum;
import com.tp2.turnosrotativos.responses.PostCreateTipoJornadaRequest;
import com.tp2.turnosrotativos.services.TipoJornadaService;

@Controller
@RequestMapping(path="/tipo-jornada")
public class TipoJornadaController {
	

	@Autowired
	private TipoJornadaService service;
	
	private TipoJornadaEnum tipo;
	
	@PostMapping("/add")
	public ResponseEntity<?> create(@RequestBody PostCreateTipoJornadaRequest tipoJornadaDTO){
		// validar si existe dentro del TipoJornadaEnum
		if (Objects.isNull(tipo.valueOfDescription(tipoJornadaDTO.getTipo()))) {
			return new ResponseEntity("No estiste ese tipo de jornada", HttpStatus.NOT_ACCEPTABLE);
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

}
