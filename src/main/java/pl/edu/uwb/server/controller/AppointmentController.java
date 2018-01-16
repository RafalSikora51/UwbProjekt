package pl.edu.uwb.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import pl.edu.uwb.server.entity.Appointment;
import pl.edu.uwb.server.repository.AppointmentDao;

@CrossOrigin
@RestController
@RequestMapping("/appointments")
public class AppointmentController {

	private final AppointmentDao appointmentDao;

	@Autowired
	public AppointmentController(AppointmentDao appointmentDao) {
		this.appointmentDao = appointmentDao;
	}

	@PostMapping
	public ResponseEntity<Void> createAppointment(@RequestParam int userId, @RequestParam int docId,
			@RequestParam int appHourId, @RequestParam int year, @RequestParam int month, @RequestParam int day,
			UriComponentsBuilder ucBuilder) {
		appointmentDao.createAppointment(userId, docId, appHourId, year, month, day);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<Appointment>> findAllAppointments() {
		List<Appointment> appointments = appointmentDao.findAllAppointments();
		if (appointments.isEmpty()) {
			return new ResponseEntity<List<Appointment>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Appointment>>(appointments, HttpStatus.OK);
	}

}
