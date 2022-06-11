package com.tp2.turnosrotativos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tp2.turnosrotativos.entities.Empleado;
import com.tp2.turnosrotativos.services.EmpleadoService;

@Controller
@RequestMapping(path="/empleado")
public class EmpleadoController {
	
	@Autowired
	private EmpleadoService empleadoService;
	
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

}
