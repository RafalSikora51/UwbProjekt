package pl.edu.uwb.server.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pl.edu.uwb.server.entity.AppointmentHour;
import pl.edu.uwb.server.repository.AppointmentHourDao;

@CrossOrigin
@RestController
@RequestMapping("/hours")
public class AppointmentHourController {

	private final AppointmentHourDao appointmentHourDao;

	@Autowired
	public AppointmentHourController(AppointmentHourDao appointmentHourDao) {
		this.appointmentHourDao = appointmentHourDao;
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<AppointmentHour>> findAllAppHours() {
		List<AppointmentHour> appHours = appointmentHourDao.findAllAppointmentHours();
		if (appHours.isEmpty()) {
			return new ResponseEntity<List<AppointmentHour>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<AppointmentHour>>(appHours, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AppointmentHour> getAppointmentHourById(@PathVariable int id) {
		Optional<AppointmentHour> appHourOptional = appointmentHourDao.getAppointmentHourById(id);
		if (appHourOptional.isPresent()) {
			return new ResponseEntity<AppointmentHour>(appHourOptional.get(), HttpStatus.OK);

		} else {
			return new ResponseEntity<AppointmentHour>(HttpStatus.NOT_FOUND);
		}
	}

}
