package pl.edu.uwb.server.repository;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import pl.edu.uwb.server.entity.Specialization;
import pl.edu.uwb.server.util.SessionConnection;


// TODO: Auto-generated Javadoc
/**
 * The Class SpecializationDao.
 * 
 * @author acewiczm
 */
@Component
public class SpecializationDao {

	/** The Log4j2 logger. */
	private static Logger logger = LogManager.getLogger(SpecializationDao.class);

	/**
	 * Adds the new specialization.
	 *
	 * @param name
	 *            the name
	 * @return true, if successful
	 */
	public boolean addNewSpecialization(String name) {
		logger.debug("addNewSpecialization");
		Session session = SessionConnection.getSessionFactory().openSession();
		session.beginTransaction();
		Specialization specialization = new Specialization(name);
		session.save(specialization);
		session.getTransaction().commit();
		SessionConnection.shutdown(session);
		logger.info("New specialization added correctly.");
		return true;
	}

	/**
	 * Find all specializations.
	 *
	 * @return the list
	 */
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

}
