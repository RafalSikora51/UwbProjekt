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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import pl.edu.uwb.server.entity.Doctor;
import pl.edu.uwb.server.entity.Specialization;
import pl.edu.uwb.server.repository.DoctorDao;
import pl.edu.uwb.server.repository.SpecializationDao;

@RestController
@CrossOrigin
@RequestMapping("/specs")
public class SpecjalizationController {

	private static Logger logger = LogManager.getLogger(SpecjalizationController.class);

	private final SpecializationDao specializationDao;
	private final DoctorDao doctorDao;

	@Autowired
	public SpecjalizationController(SpecializationDao specializationDao, DoctorDao doctorDao) {
		this.specializationDao = specializationDao;
		this.doctorDao = doctorDao;
	}

	@PostMapping
	public ResponseEntity<Void> createSpecialization(@RequestParam String specName, UriComponentsBuilder ucBuilder) {
		if (specializationDao.getSpecByName(specName).isPresent()) {
			logger.debug("Specialization already exists");
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		} else {
			specializationDao.createSpecialization(specName);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucBuilder.path("/specs/{id}")
					.buildAndExpand(specializationDao.getSpecByName(specName).get().getId()).toUri());
			return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		}
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<Specialization>> findAllSpecializations() {
		List<Specialization> specs = specializationDao.findAllSpecializations();
		if (specs.isEmpty()) {
			return new ResponseEntity<List<Specialization>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Specialization>>(specs, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Specialization> getSpecById(@PathVariable int id) {
		Optional<Specialization> specOptional = specializationDao.getSpecializationById(id);
		if (specOptional.isPresent()) {
			return new ResponseEntity<Specialization>(specOptional.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<Specialization>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/{id}/doctors", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<Doctor>> findDoctorsBySpecialization(@PathVariable int id) {
		List<Doctor> doctors = doctorDao.findDoctorsBySpecId(id);
		if (doctors.isEmpty()) {
			return new ResponseEntity<List<Doctor>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Doctor>>(doctors, HttpStatus.OK);
	}

}
