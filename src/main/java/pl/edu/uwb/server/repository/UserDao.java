package pl.edu.uwb.server.repository;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import pl.edu.uwb.server.entity.User;
import pl.edu.uwb.server.util.SessionConnection;

/**
 * The Class UserDao.
 * 
 * @author acewiczm
 */

@Component
public class UserDao {

	/** The Log4j2 logger. */
	private static Logger logger = LogManager.getLogger(UserDao.class);

	/**
	 * Find all users.
	 *
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public List<User> findAllUsers() {
		logger.debug("findAllUsers");
		List<User> users;
		Session session = SessionConnection.getSessionFactory().openSession();
		users = session.createQuery("from User").list();
		SessionConnection.shutdown(session);
		logger.info("All users listed.");
		return users;
	}

	/**
	 * Find user by id.
	 *
	 * @param id
	 *            the id
	 * @return the user
	 */
	public Optional<User> findUserById(long id) {
		logger.debug("findUserById");
		Session session = SessionConnection.getSessionFactory().openSession();
		if (session.get(User.class, id) != null) {
			User user = session.load(User.class, id);
			SessionConnection.shutdown(session);
			logger.info("User found by id.");
			return Optional.ofNullable(user);
		} else {
			SessionConnection.shutdown(session);
			logger.info("There is no such user.");
			return Optional.empty();
		}
	}

	/**
	 * Find user by email.
	 *
	 * @param email
	 *            the email
	 * @return the optional
	 */
	public Optional<User> findUserByEmail(String email) {
		logger.debug("findUserByEmail");
		User user;
		Session session = SessionConnection.getSessionFactory().openSession();
		user = session.byNaturalId(User.class).using("email", email).load();
		SessionConnection.shutdown(session);
		return Optional.ofNullable(user);
	}

	// public Optional<User> findUserByCountryId(long countryId) {
	// logger.debug("findUserById");
	//
	// }

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
	public boolean addNewUser(String countryId, String firstName, String lastName, String email) {
		logger.debug("addNewUser");
		if (findUserByEmail(email).isPresent()) {
			logger.info("Email is already taken.");
			return false;
		}
		// if (findUserByCountryId(countryId).isPresent()) {
		// logger.info("User with given PESEL already exist.");
		// return false;
		// }

		else {
			Session session = SessionConnection.getSessionFactory().openSession();
			session.beginTransaction();
			User user = new User(countryId, firstName, lastName, email);
			session.save(user);
			session.getTransaction().commit();
			SessionConnection.shutdown(session);
			logger.info("User registered correctly.");
			return true;
		}
	}

}
