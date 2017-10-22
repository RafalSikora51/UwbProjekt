package pl.edu.uwb.server.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.edu.uwb.server.repository.UserDao;

// TODO: Auto-generated Javadoc
/**
 * The Class AdminLoginController.
 * 
 * @author acewiczm
 */

@CrossOrigin
@RestController
@RequestMapping("/login")
public class LoginController {

	/** The Log4j2 logger. */
	private static Logger logger = LogManager.getLogger(LoginController.class);

	/** The user dao. */
	@Autowired
	private UserDao userDao;

	/**
	 * Checks if user has login rights.
	 *
	 * @param email
	 *            the email
	 * @param token
	 *            the token
	 * @return true, if successful
	 */

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean userLoginRightsChecking(@RequestParam String email, @RequestParam String token) {
		logger.debug("userLoginRightsChecking");
		return userDao.isUserInDataBase(email, token);
	}

}