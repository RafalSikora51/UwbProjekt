package pl.edu.uwb.server.controller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import pl.edu.uwb.server.entity.Doctor;
import pl.edu.uwb.server.entity.User;
import pl.edu.uwb.server.repository.DoctorDao;

@CrossOrigin
@RestController
@RequestMapping("/doctors")
public class DoctorController {

	private static Logger logger = LogManager.getLogger(DoctorController.class);

	@Autowired
	private final DoctorDao doctorDao;

	@Autowired
	public DoctorController(DoctorDao doctorDao) {
		this.doctorDao = doctorDao;
	}

	@PostMapping
	public ResponseEntity<Void> createDoctor(@RequestBody Doctor doctor, @RequestParam String specName,
			UriComponentsBuilder ucBuilder) {
		if (doctorDao.findDoctorByEmail(doctor.getEmail()).isPresent()
				|| doctorDao.findDoctorByCountryId(doctor.getCountryId()).isPresent()) {
			logger.debug("Doctor already exists");
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		} else {
			doctorDao.createDoctor(doctor, specName);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucBuilder.path("/doctors/{id}").buildAndExpand(doctor.getId()).toUri());
			return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		}
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<Doctor>> findAllDoctors() {
		List<Doctor> doctors = doctorDao.findAllDoctors();
		if (doctors.isEmpty()) {
			return new ResponseEntity<List<Doctor>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Doctor>>(doctors, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Doctor> getDoctor(@PathVariable int id) {
		Optional<Doctor> doctorOptional = doctorDao.findDoctorById(id);
		if (doctorOptional.isPresent()) {
			return new ResponseEntity<Doctor>(doctorOptional.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<Doctor>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/{id}/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<User>> findAllUsersForDoctor(@PathVariable int id) throws Exception {
		List<User> users = doctorDao.findAllUsersByDoctor(id);
		if (users.isEmpty()) {
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

}
