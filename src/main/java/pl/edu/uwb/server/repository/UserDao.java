package pl.edu.uwb.server.repository;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.edu.uwb.server.entity.Token;
import pl.edu.uwb.server.entity.User;
import pl.edu.uwb.server.util.SessionConnection;

// TODO: Auto-generated Javadoc
/**
 * The Class UserDao.
 * 
 * @author acewiczm
 */

@Component
public class UserDao {

	/** The Log4j2 logger. */
	private static Logger logger = LogManager.getLogger(UserDao.class);

	/** The token dao. */
	@Autowired
	private TokenDao tokenDao;
	
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
	public Optional<User> findUserById(int id) {
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

	/**
	 * Find user by country id.
	 *
	 * @param countryId
	 *            the country id
	 * @return the optional
	 */
	@SuppressWarnings("unchecked")
	public Optional<User> findUserByCountryId(String countryId) {
		logger.debug("findUserByCountryId");
		List<User> users;
		Session session = SessionConnection.getSessionFactory().openSession();
		String hql = "from User where PESEL = :countryId";
		Query query = session.createQuery(hql);
		query.setParameter("countryId", countryId);
		users = query.list();
		SessionConnection.shutdown(session);
		if (!users.isEmpty()) {
			return Optional.ofNullable(users.get(0));
		} else {
			return Optional.empty();
		}
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
	 * @param token
	 *            the token
	 * @return true, if successful
	 */
	public boolean addNewUser(String countryId, String firstName, String lastName, String email, String token) {
		logger.debug("addNewUser");
		if (findUserByEmail(email).isPresent()) {
			logger.info("Email is already taken.");
			return false;
		}
		if (findUserByCountryId(countryId).isPresent()) {
			logger.info("User with given PESEL already exist.");
			return false;
		} else {
			Session session = SessionConnection.getSessionFactory().openSession();
			session.beginTransaction();
			User user = new User(countryId, firstName, lastName, email);
			session.save(user);
			Token userToken = new Token(token, user);
			user.getTokenSet().add(userToken);
			session.save(userToken);
			session.getTransaction().commit();
			SessionConnection.shutdown(session);
			logger.info("User registered correctly.");
			return true;
		}
	}

	/**
	 * Checks if user is in data base.
	 *
	 * @param email
	 *            the email
	 * @param token
	 *            the token
	 * @return true, if is user in data base
	 */
	public boolean isUserInDataBase(String email, String token) {
		logger.debug("isUserInDataBase");
		if (!findUserByEmail(email).isPresent()) {
			logger.info("No such user in data base.");
			return false;
		} else if (findUserByEmail(email).isPresent()
				&& !tokenDao.findActiveTokenByUserId(findUserByEmail(email).get().getId()).getToken().equals(token)) {
			logger.info("The token is invalid.");
			return false;
		} else {
			logger.info("User found in data base.");
			return true;
		}
	}
	
	
}
