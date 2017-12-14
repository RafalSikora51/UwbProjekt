package pl.edu.uwb.server.repository;

import java.sql.Timestamp;
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
import pl.edu.uwb.server.util.TokenGenerator;

@Component
public class UserDao {

	/** The Log4j2 logger. */
	private static Logger logger = LogManager.getLogger(UserDao.class);

	@Autowired
	private TokenDao tokenDao;

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

	public Optional<User> findUserByEmail(String email) {
		logger.debug("findUserByEmail");
		User user;
		Session session = SessionConnection.getSessionFactory().openSession();
		user = session.byNaturalId(User.class).using("email", email).load();
		SessionConnection.shutdown(session);
		return Optional.ofNullable(user);
	}

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

	public void saveUser(User user)
	{
		Session session = SessionConnection.getSessionFactory().openSession();
		session.beginTransaction();
		user.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		session.save(user);
		String token = TokenGenerator.randomStringGenerator(10).get("token");
		Token userToken = new Token(token, user);
		user.getTokenSet().add(userToken);
		session.save(userToken);
		session.getTransaction().commit();
		SessionConnection.shutdown(session);
		logger.info("User created correctly.");
	}
	
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
