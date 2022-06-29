package com.tp2.turnosrotativos.controllers;

import java.util.ArrayList;
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
import com.tp2.turnosrotativos.validators.JornadasWeek;
import com.tp2.turnosrotativos.validators.LaboralDay;
import static com.tp2.turnosrotativos.utils.Util.getSemanalHours;

@CrossOrigin(origins = "https://turnos-rotativos.herokuapp.com")
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
		List<JornadasWeek> listJornadaSemana = jornadaService.listJornadaSemanal(jornadaDTO.getFecha(), dni);
		int cantPorTurno = jornadaService.countDateAndTipo(jornadaDTO.getFecha(), jornadaDTO.getTipo());

		// carga el mensaje de acuerdo a la validacion
		String mensaje = jornadaValidator.isValidAddJornada(listDay, jornadaDTO, listJornadaSemana, cantPorTurno, listLaboralDay);
		if (!mensaje.equals("isValid")) {
			// resetea las horas a null si son dia libre o vacaciones
			if (mensaje.equals("resetNull")) {
				jornadaDTO.setHoraEntrada(null);
				jornadaDTO.setHoraSalida(null);
			} else {
				return new ResponseEntity(mensaje, HttpStatus.BAD_REQUEST);
			}
		}
		
		Jornada jornada = new Jornada();
		jornada.setEmpleado(empleadoService.findByDni(dni));
		jornada.setFecha(jornadaDTO.getFecha());
		jornada.setHoraEntrada(jornadaDTO.getHoraEntrada());
		jornada.setHoraSalida(jornadaDTO.getHoraSalida());
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
		int cantPorTurno = jornadaService.countDateAndTipo(jornadaDTO.getFecha(), jornadaDTO.getTipo());
		
		if (!jornadaService.existsById(jornadaId)) {
			return new ResponseEntity("La jornada ingresada no existe en la base de datos", HttpStatus.BAD_REQUEST);
		}
		
		Jornada jornada = jornadaService.findById(jornadaId).get();
		String dni = jornada.getEmpleado().getDni();
		List<LaboralDay> listLaboralDay = jornadaService.listSemanaLaboral(jornadaDTO, dni);
		List<Jornada> listDay = jornadaService.findByFechaAndEmpleadoDni(jornadaDTO.getFecha(), dni);
		List<JornadasWeek> listJornadaSemana = jornadaService.listJornadaSemanal(jornadaDTO.getFecha(), dni);
		
		// carga el mensaje de acuerdo a la validacion
		String mensaje = jornadaValidator.isValidUpdateJornada(listDay, jornadaDTO, listJornadaSemana, cantPorTurno, listLaboralDay, jornada.getId());
		if (!mensaje.equals("isValid")) {
			// resetea las horas a null si son dia libre o vacaciones
			if (mensaje.equals("resetNull")) {
				jornadaDTO.setHoraEntrada(null);
				jornadaDTO.setHoraSalida(null);
			} else {
				return new ResponseEntity(mensaje, HttpStatus.BAD_REQUEST);
			}
		}
		
		jornada.setHoraEntrada(jornadaDTO.getHoraEntrada());
		jornada.setHoraSalida(jornadaDTO.getHoraSalida());
		
		return new ResponseEntity(jornada, HttpStatus.OK);
	}
	
	/*
	 * Elimina la jornada
	 * 
	 */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteJornada(@PathVariable("id") Long id){
		
		if (!jornadaService.existsById(id)) {
			return new ResponseEntity("No se encuentra esta jornada cargada", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		jornadaService.delete(id);
		
		return new ResponseEntity("La jornada fue eliminada", HttpStatus.OK);
	}
}
