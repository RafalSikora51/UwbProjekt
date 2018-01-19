package pl.edu.uwb.server.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.edu.uwb.server.entity.MedicalHistory;
import pl.edu.uwb.server.entity.Token;
import pl.edu.uwb.server.entity.User;
import pl.edu.uwb.server.util.MailService;
import pl.edu.uwb.server.util.SessionConnection;
import pl.edu.uwb.server.util.TokenGenerator;

@Component
public class UserDao {

	private static final String NOTACCEPTABLE = "NOTACCEPTABLE";
	private static final String CONFLICT = "CONFLICT";
	private static final String CREATED_VALUE = "CREATED";
	private static final String STATUS = "status";
	private static final String CREATED_KEY = "created";

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

	public boolean validUserDetails(User user) {
		boolean countryId = user.getCountryId().length() >= 11;
		boolean names = user.getFirstName().length() >= 3 && user.getLastName().length() >= 3;
		boolean email = user.getEmail().contains("@") && user.getEmail().contains(".");
		return countryId && names && email;
	}

	public boolean createUser(User user) {
		if (validUserDetails(user)) {
			Session session = SessionConnection.getSessionFactory().openSession();
			session.beginTransaction();
			user.setCreatedOn(new Timestamp(System.currentTimeMillis()));
			session.save(user);
			String token = TokenGenerator.randomStringGenerator(10).get("token");
			Token userToken = new Token(token, user);
			user.getTokenSet().add(userToken);
			if (MailService.sendEmail(user.getEmail(), token, user.getFirstName(), user.getLastName())) {
				session.save(userToken);
				session.getTransaction().commit();
				SessionConnection.shutdown(session);
				logger.info("User created correctly.");
				return true;
			} else {
				session.getTransaction().rollback();
				SessionConnection.shutdown(session);
				return false;
			}
		} else {
			return false;
		}
	}

	public JSONObject createUserJSON(User user) {
		JSONObject jsonResponse = new JSONObject();
		if (findUserByEmail(user.getEmail()).isPresent() || findUserByCountryId(user.getCountryId()).isPresent()) {
			logger.debug("User already exists");
			jsonResponse.put(CREATED_KEY, false);
			jsonResponse.put(STATUS, CONFLICT);
		} else if (createUser(user)) {
			jsonResponse.put(CREATED_KEY, true);
			jsonResponse.put(STATUS, CREATED_VALUE);
		} else {
			jsonResponse.put(CREATED_KEY, false);
			jsonResponse.put(STATUS, NOTACCEPTABLE);
		}
		return jsonResponse;
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

	public boolean isUserAdmin(User user) {
		return user.isAdmin();
	}

	@SuppressWarnings("unchecked")
	public JSONObject isUserInDataBaseJSON(String email, String token) {
		logger.debug("isUserInDataBase");
		JSONObject jsonResponse = new JSONObject();
		if (isUserInDataBase(email, token)) {
			User user = findUserByEmail(email).get();
			jsonResponse.put("canLogin", true);
			jsonResponse.put("admin", isUserAdmin(user)); // jesli true to to jest pani recepcjonistka, else zwykly user
		} else {
			jsonResponse.put("canLogin", false);
		}
		return jsonResponse;
	}

	public List<MedicalHistory> findAllMedicalHistoriesForUser(User user) {
		logger.debug("findAllMedicalHistoriesForUser");
		List<MedicalHistory> medicalHistories = user.getMedicalHistorySet().stream().collect(Collectors.toList());
		logger.info("All medical histories for user listed.");
		return medicalHistories;
	}

	public List<MedicalHistory> findAllMedicalHistoriesForUser(int userId) throws Exception {
		logger.debug("findAllMedicalHistoriesForUser");
		Optional<User> userOptional = findUserById(userId);
		if (userOptional.isPresent()) {
			return findAllMedicalHistoriesForUser(userOptional.get());
		} else
			throw new Exception("User not found");
	}

	public static Predicate<MedicalHistory> isMedicalHistoryFromGivenSpec(int specId) {
		return p -> p.getSpecId() == specId;
	}

	public List<MedicalHistory> findAllMedicalHistoriesForUserBySpecialization(User user, int specId) {
		logger.debug("findAllMedicalHistoriesForUserBySpecialization");
		logger.info("All medical histories for user from given specialization listed.");
		return findAllMedicalHistoriesForUser(user).stream().filter(isMedicalHistoryFromGivenSpec(specId))
				.collect(Collectors.toList());
	}

	public List<MedicalHistory> findAllMedicalHistoriesForUserBySpecialization(int userId, int specId)
			throws Exception {
		logger.debug("findAllMedicalHistoriesForUserBySpecialization");
		Optional<User> userOptional = findUserById(userId);
		if (userOptional.isPresent()) {
			return findAllMedicalHistoriesForUserBySpecialization(userOptional.get(), specId);
		} else
			throw new Exception("User not found");
	}

	public int findUserIdByEmail(String email) throws Exception {
		logger.debug("findAllMedicalHistoriesForUserBySpecialization");
		if (StringUtils.isNotBlank(email)) {
			Optional<User> userOptional = findUserByEmail(email);
			if (userOptional.isPresent()) {
				return userOptional.get().getId();
			} else
				throw new Exception("User not found");
		} else
			throw new Exception("Email cant be blank");

	}

	public boolean changeUserAdminRights(int id) {
		Session session = SessionConnection.getSessionFactory().openSession();
		Optional<User> userOptional = findUserById(id);
		if (userOptional.isPresent()) {
			session.beginTransaction();
			User user = userOptional.get();
			user.setAdmin(!user.isAdmin());
			session.saveOrUpdate(user);
			session.getTransaction().commit();
			SessionConnection.shutdown(session);
			return true;
		} else {
			SessionConnection.shutdown(session);
			return false;
		}
	}

}
