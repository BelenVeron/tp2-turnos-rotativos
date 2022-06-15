package com.tp2.turnosrotativos.controllers;

import java.util.ArrayList;
import java.util.List;

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
import com.tp2.turnosrotativos.enums.TipoJornadaEnum;
import com.tp2.turnosrotativos.requests.PostAddEmpleadoRequest;
import com.tp2.turnosrotativos.responses.GetHorasCargadasResponse;
import com.tp2.turnosrotativos.services.EmpleadoService;
import com.tp2.turnosrotativos.services.JornadaService;
import com.tp2.turnosrotativos.validators.EmpleadoValidator;

import static com.tp2.turnosrotativos.utils.Util.getHours;
import static com.tp2.turnosrotativos.utils.Util.isLaboralType;


@Controller
@RequestMapping(path="/empleado")
public class EmpleadoController {
	
	@Autowired
	private EmpleadoService empleadoService;
	
	@Autowired
	private JornadaService jornadaService;
	
	private EmpleadoValidator empleadoValidator = new EmpleadoValidator();
	
	/*
	 * Se agrega empleado
	 * 
	 */
	@PostMapping("/add")
	public ResponseEntity<?> create(@RequestBody PostAddEmpleadoRequest empleadoDTO){
		
		// diferentes validaciones para los campos
		String mensaje = empleadoValidator.isValid(empleadoDTO);
		if (!mensaje.equals("isValid")) {
			return new ResponseEntity(mensaje, HttpStatus.BAD_REQUEST);
		}
		
		// dni solo puede ser unico
		if (empleadoService.existsByDni(empleadoDTO.getDni())) {
			return new ResponseEntity("El dni ya se encuentra guardado", HttpStatus.BAD_REQUEST);
		}
		
		Empleado empleado = new Empleado();
		empleado.setNombre(empleadoDTO.getNombre());
		empleado.setApellido(empleadoDTO.getApellido());
		empleado.setDni(empleadoDTO.getDni());
		
		empleadoService.save(empleado);
		return new ResponseEntity(empleado, HttpStatus.OK);
	}
	
	/*
	 * Lista los empleados guardados
	 * 
	 */
	@GetMapping("/list-empleados")
	public ResponseEntity<List<Empleado>> listAll(){
		List<Empleado> list = empleadoService.list();
		return new ResponseEntity(list, HttpStatus.OK);
	}
	
	/*
	 * Lista la cantidad de horas guardadas por tipo de jornada utilizando el dni
	 * 
	 */
	@GetMapping("/list-horas-cargadas/{dni}")
	public ResponseEntity<List<GetHorasCargadasResponse>> listHorasCargadas(@PathVariable("dni") String dni){
		
		if (!empleadoService.existsByDni(dni)) {
			return new ResponseEntity("No se encuentra el empleado con este dni cargado", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		List<Jornada> jornadas = jornadaService.list(empleadoService.findByDni(dni).getId());
		List<GetHorasCargadasResponse> response = new ArrayList<GetHorasCargadasResponse>();

		// recorre las jornadas del empleado
		jornadas.stream().forEach(jornada -> {
			
			// solo se mostrarian las horas de los tipos de jornadas que son laborales
			if (isLaboralType(jornada)) {
				
				// si no se cargo el tipo dentro de la respuesta, se carga
				if (response.isEmpty() || !response.stream()
					.filter(element -> element.getTipo().equals(jornada.getTipoJornada().getTipo()) 
							)
					.findFirst().isPresent()) {
					GetHorasCargadasResponse newTipo = new GetHorasCargadasResponse();
					newTipo.setHorasCargadas(getHours(jornada));
					newTipo.setTipo(jornada.getTipoJornada().getTipo());
					
					response.add(newTipo);
				}else {
					// si ya se cargo, se suma
					response.stream()
						.forEach(element -> {
							if(element.getTipo().equals(jornada.getTipoJornada().getTipo())) {
								element.setHorasCargadas(
										element.getHorasCargadas() + (getHours(jornada))
										);
							}
						});
				}
				
			}
			
			
		});
		
		return new ResponseEntity(response, HttpStatus.OK);
	}

}
