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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import pl.edu.uwb.server.entity.User;
import pl.edu.uwb.server.repository.UserDao;

@RestController
@RequestMapping("/users")
public class UserController {

	private static Logger logger = LogManager.getLogger(UserController.class);

	private final UserDao userDao;

	@Autowired
	public UserController(UserDao userDao) {
		this.userDao = userDao;
	}

	@PostMapping
	public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
		if (userDao.findUserByEmail(user.getEmail()).isPresent()
				|| userDao.findUserByCountryId(user.getCountryId()).isPresent()) {
			logger.debug("User already exists");
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		} else {
			userDao.createUser(user);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri());
			return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		}
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

}
