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

// TODO: Auto-generated Javadoc
/**
 * The Class SpecjalizationController.
 */
@RestController
@RequestMapping("/specs")
public class SpecjalizationController {

	/** The Log4j2 logger. */
	private static Logger logger = LogManager.getLogger(SpecjalizationController.class);

	/** The specialization dao. */
	private final SpecializationDao specializationDao;

	/**
	 * Instantiates a new specjalization controller.
	 *
	 * @param specializationDao
	 *            the specialization dao
	 */
	@Autowired
	public SpecjalizationController(SpecializationDao specializationDao) {
		this.specializationDao = specializationDao;
	}

	/**
	 * Adds the new specialization.
	 *
	 * @param name
	 *            the name
	 * @return true, if successful
	 */
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean addNewSpecialization(@RequestParam String name) {
		logger.debug("addNewSpecialization");
		return specializationDao.addNewSpecialization(name);
	}

	/**
	 * Find all specializations.
	 *
	 * @return the list
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Specialization> findAllSpecializations() {
		logger.debug("findAllSpecializations");
		return specializationDao.findAllSpecializations();
	}

}
