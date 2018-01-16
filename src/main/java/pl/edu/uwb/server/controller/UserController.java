package pl.edu.uwb.server.controller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pl.edu.uwb.server.entity.Appointment;
import pl.edu.uwb.server.entity.MedicalHistory;
import pl.edu.uwb.server.entity.User;
import pl.edu.uwb.server.repository.AppointmentDao;
import pl.edu.uwb.server.repository.UserDao;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

	private static Logger logger = LogManager.getLogger(UserController.class);

	private final UserDao userDao;
	private final AppointmentDao appointmentDao;

	@Autowired
	public UserController(UserDao userDao, AppointmentDao appointmentDao) {
		this.userDao = userDao;
		this.appointmentDao = appointmentDao;
	}

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject createUser(@RequestBody User user) {
		logger.debug("createUser");
		return userDao.createUserJSON(user);
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<User>> findAllUsers() {
		List<User> users = userDao.findAllUsers();
		if (users.isEmpty()) {
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUser(@PathVariable int id) {
		Optional<User> userOptional = userDao.findUserById(id);
		if (userOptional.isPresent()) {
			return new ResponseEntity<User>(userOptional.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/{id}/appointments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<Appointment>> findAllAppointmentsForUser(@PathVariable int id) throws Exception {
		List<Appointment> appointments = appointmentDao.findAllAppointmentsForUser(id);
		if (appointments.isEmpty()) {
			return new ResponseEntity<List<Appointment>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Appointment>>(appointments, HttpStatus.OK);
	}

	@RequestMapping(value = "/{userId}/appointments/{specId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<Appointment>> findAllAppointmentsForUserBySpecialization(@PathVariable int userId,
			@PathVariable int specId) throws Exception {
		List<Appointment> appointments = appointmentDao.findAllAppointmentsForUserBySpecialization(userId, specId);
		if (appointments.isEmpty()) {
			return new ResponseEntity<List<Appointment>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Appointment>>(appointments, HttpStatus.OK);
	}

	@RequestMapping(value = "/{userId}/histories", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<MedicalHistory>> findAllMedicalHistoriesForUser(@PathVariable int userId)
			throws Exception {
		List<MedicalHistory> medicalHistories = userDao.findAllMedicalHistoriesForUser(userId);
		if (medicalHistories.isEmpty()) {
			return new ResponseEntity<List<MedicalHistory>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<MedicalHistory>>(medicalHistories, HttpStatus.OK);
	}

	@RequestMapping(value = "/{userId}/histories/{specId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<MedicalHistory>> findAllMedicalHistoriesForUser(@PathVariable int userId,
			@PathVariable int specId) throws Exception {
		List<MedicalHistory> medicalHistories = userDao.findAllMedicalHistoriesForUserBySpecialization(userId, specId);
		if (medicalHistories.isEmpty()) {
			return new ResponseEntity<List<MedicalHistory>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<MedicalHistory>>(medicalHistories, HttpStatus.OK);
	}

}
