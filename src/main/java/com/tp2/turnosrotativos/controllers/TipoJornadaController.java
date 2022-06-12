package com.tp2.turnosrotativos.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tp2.turnosrotativos.entities.Jornada;
import com.tp2.turnosrotativos.entities.TipoJornada;
import com.tp2.turnosrotativos.responses.GetListJornadaResponse;
import com.tp2.turnosrotativos.services.TipoJornadaService;

@Controller
@RequestMapping(path="/tipo-jornada")
public class TipoJornadaController {
	

	@Autowired
	private TipoJornadaService service;
	
	@PostMapping("/add")
	public ResponseEntity<?> create(@RequestBody TipoJornada tipoJornada){
		service.save(tipoJornada);
		return new ResponseEntity(tipoJornada, HttpStatus.OK);
	}
	
	@GetMapping("/list-tipo-jornadas")
	public ResponseEntity<List<TipoJornada>> listAll(){
		List<TipoJornada> list = service.list();
		return new ResponseEntity(list, HttpStatus.OK);
	}

}
