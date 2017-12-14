package pl.edu.uwb.server.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.edu.uwb.server.entity.Specialization;
import pl.edu.uwb.server.repository.SpecializationDao;

@RestController
@RequestMapping("/specs")
public class SpecjalizationController {

	private static Logger logger = LogManager.getLogger(SpecjalizationController.class);

	private final SpecializationDao specializationDao;

	@Autowired
	public SpecjalizationController(SpecializationDao specializationDao) {
		this.specializationDao = specializationDao;
	}

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean addNewSpecialization(@RequestParam String name) {
		logger.debug("addNewSpecialization");
		return specializationDao.addNewSpecialization(name);
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Specialization> findAllSpecializations() {
		logger.debug("findAllSpecializations");
		return specializationDao.findAllSpecializations();
	}

}
