package pl.edu.uwb.server.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.edu.uwb.server.entity.User;
import pl.edu.uwb.server.repository.UserDao;
import pl.edu.uwb.server.util.TokenGenerator;

// TODO: Auto-generated Javadoc
/**
 * The Class UserController.
 * 
 * @author acewiczm
 */

@RestController
@RequestMapping("/users")
public class UserController {

	/** The Log4j2 logger. */
	private static Logger logger = LogManager.getLogger(UserController.class);

	/** The user dao. */
	private final UserDao userDao;

	/**
	 * Instantiates a new user controller.
	 *
	 * @param userDao
	 *            the user dao
	 * 
	 */
	@Autowired
	public UserController(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * Adds the new user.
	 *
	 * @param countryId
	 *            the country id
	 * @param firstName
	 *            the first name
	 * @param lastName
	 *            the last name
	 * @param email
	 *            the email
	 * @return true, if successful
	 */
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean addNewUser(@RequestParam String countryId, @RequestParam String firstName,
			@RequestParam String lastName, @RequestParam String email) {
		logger.debug("addNewUser");
		String token = TokenGenerator.randomStringGenerator(10).get("token");
		return userDao.addNewUser(countryId, firstName, lastName, email, token);
	}

	/**
	 * Find all users.
	 *
	 * @return the list
	 */
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<User> findAllUsers() {
		logger.debug("findAllUsers");
		return userDao.findAllUsers();
	}

	/**
	 * Get the user info.
	 *
	 * @param id
	 *            the id
	 * @return the user info
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public User getUserInfo(@PathVariable int id) {
		logger.debug("getUserInfo");
		return userDao.findUserById(id).get();
	}

}
