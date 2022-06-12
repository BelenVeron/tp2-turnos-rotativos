package com.tp2.turnosrotativos.servicesImpl;

import static java.time.temporal.ChronoUnit.MINUTES;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tp2.turnosrotativos.entities.Jornada;
import com.tp2.turnosrotativos.repositories.JornadaRepository;
import com.tp2.turnosrotativos.requests.PostJornadaRequest;
import com.tp2.turnosrotativos.services.JornadaService;
import com.tp2.turnosrotativos.validators.DaysValidator;
import com.tp2.turnosrotativos.validators.LaboralDay;

@Service
@Transactional
public class JornadaServiceImp implements JornadaService{
	
	@Autowired
	private JornadaRepository repository;
	
	private DaysValidator daysValidator = new DaysValidator();

	@Override
	public void save(Jornada jornada) {
		repository.save(jornada);
	}

	@Override
	public List<Jornada> list(Long empleadoId) {
		return repository.findByEmpleadoId(empleadoId);
	}

	@Override
	public Optional<Jornada> findById(Long jornadaId) {
		return repository.findById(jornadaId);
	}

	@Override
	public List<Jornada> findByFechaAndEmpleadoId(LocalDate date, Long id) {
		return repository.findByFechaAndEmpleadoId(date, id);
	}
	
	/*
	 * Se devuelve una lista con cada dia y sus horas cargadas
	 * */
	@Override
	public List<LaboralDay> listSemanaLaboral(PostJornadaRequest jornada, Long id){
		List<LaboralDay> list = new ArrayList<LaboralDay>();
		int day = jornada.getFecha().getDayOfWeek().getValue();
		
		// suponiendo que son dias laborales de lunes a viernes
		for (int i = 1; i <= 5; i++) {
			LocalDate date = jornada.getFecha().plusDays(i - day);
			LaboralDay laboralDay = new LaboralDay();
			laboralDay.setDayOfWeek(date.getDayOfWeek().getValue());
			
			// es una lista ya que puede tener turno extra y normal juntos en el mismo dia
			List<Jornada> listDay = repository.findByFechaAndEmpleadoId(date, id);
			if (!listDay.isEmpty()) {
				laboralDay.setHours((float)listDay.stream().mapToDouble(element -> daysValidator.getHours(element)).sum());
			}else {
				// si no tiene cargado nada ese dia se setea 0 horas
				laboralDay.setHours(new Float(0));
			}
			
			list.add(laboralDay);
		}
		
		return list;
	}

}
