package pl.edu.uwb.server.repository;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import pl.edu.uwb.server.entity.Specialization;
import pl.edu.uwb.server.util.SessionConnection;

@Component
public class SpecializationDao {

	private static Logger logger = LogManager.getLogger(SpecializationDao.class);

	private static final String NOTACCEPTABLE = "NOTACCEPTABLE";
	private static final String CONFLICT = "CONFLICT";
	private static final String CREATED_VALUE = "CREATED";
	private static final String STATUS = "status";
	private static final String CREATED_KEY = "created";

	public boolean validSpecDetails(String specName) {
		return specName.length() >= 2;
	}

	public boolean createSpecialization(String name) {
		logger.debug("createSpecialization");
		if (validSpecDetails(name)) {
			Session session = SessionConnection.getSessionFactory().openSession();
			session.beginTransaction();
			Specialization specialization = new Specialization(name);
			session.save(specialization);
			session.getTransaction().commit();
			SessionConnection.shutdown(session);
			logger.info("New specialization added correctly.");
			return true;
		} else {
			return false;
		}
	}

	public JSONObject createSpecJSON(String specName) {
		JSONObject jsonResponse = new JSONObject();
		if (getSpecByName(specName).isPresent()) {
			logger.debug("Specialization already exists");
			jsonResponse.put(CREATED_KEY, false);
			jsonResponse.put(STATUS, CONFLICT);
		} else if (createSpecialization(specName)) {
			jsonResponse.put(CREATED_KEY, true);
			jsonResponse.put(STATUS, CREATED_VALUE);
		} else {
			jsonResponse.put(CREATED_KEY, false);
			jsonResponse.put(STATUS, NOTACCEPTABLE);
		}
		return jsonResponse;
	}

	@SuppressWarnings("unchecked")
	public List<Specialization> findAllSpecializations() {
		logger.debug("findAllSpecializations");
		List<Specialization> specs;
		Session session = SessionConnection.getSessionFactory().openSession();
		specs = session.createQuery("from Specialization").list();
		SessionConnection.shutdown(session);
		logger.info("All specializations listed.");
		return specs;
	}

	public Optional<Specialization> getSpecByName(String name) {
		logger.debug("findSpecByName");
		Specialization specialization = null;
		Session session = SessionConnection.getSessionFactory().openSession();
		specialization = session.byNaturalId(Specialization.class).using("name", name).load();
		SessionConnection.shutdown(session);
		return Optional.ofNullable(specialization);
	}

	public Optional<Specialization> getSpecializationById(int id) {
		logger.debug("getSpecializationById");
		Session session = SessionConnection.getSessionFactory().openSession();
		if (session.get(Specialization.class, id) != null) {
			Specialization specialization = session.load(Specialization.class, id);
			SessionConnection.shutdown(session);
			logger.info("Specialization found by id.");
			return Optional.ofNullable(specialization);
		} else {
			SessionConnection.shutdown(session);
			logger.info("There is no such Specialization.");
			return Optional.empty();
		}
	}

}
