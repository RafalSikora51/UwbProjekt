package pl.edu.uwb.server.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.edu.uwb.server.repository.DoctorDao;

@CrossOrigin
@RestController
@RequestMapping("/doctorlogin")
public class DoctorLoginController {

	private static Logger logger = LogManager.getLogger(DoctorLoginController.class);

	@Autowired
	private DoctorDao doctorDao;

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject doctorLoginRightsChecking(@RequestParam String email, @RequestParam String token) {
		logger.debug("doctorLoginRightsChecking");
		return doctorDao.isDoctorInDataBaseJSON(email, token);
	}

}
