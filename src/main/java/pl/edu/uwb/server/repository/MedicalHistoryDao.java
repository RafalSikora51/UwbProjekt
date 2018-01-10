package pl.edu.uwb.server.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.edu.uwb.server.entity.MedicalHistory;
import pl.edu.uwb.server.entity.User;
import pl.edu.uwb.server.util.SessionConnection;

@Component
public class MedicalHistoryDao {

	private static Logger logger = LogManager.getLogger(SpecializationDao.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private SpecializationDao specializationDao;

	public void createMedicalHistory(int userId, int specId) {
		logger.debug("createMedicalHistory");
		Session session = SessionConnection.getSessionFactory().openSession();
		session.beginTransaction();
		MedicalHistory medicalHistory = new MedicalHistory();
		User user = userDao.findUserById(userId).get();
		medicalHistory.setUser(user);
		medicalHistory.setSpecId(specId);
		session.save(medicalHistory);
		session.getTransaction().commit();
		SessionConnection.shutdown(session);
		logger.info("New medical history added correctly.");
	}

	@SuppressWarnings("unchecked")
	public List<MedicalHistory> findAllMedicalHistories() {
		logger.debug("findAllMedicalHistories");
		List<MedicalHistory> histories;
		Session session = SessionConnection.getSessionFactory().openSession();
		histories = session.createQuery("from MedicalHistory").list();
		SessionConnection.shutdown(session);
		logger.info("All medical histories listed.");
		return histories;
	}
	
	public List<MedicalHistory> findAllMedicalHistoriesForGivenUser(User user) {
		logger.debug("findAllMedicalHistoriesForGivenUser");
		List<MedicalHistory> histories = user.getMedicalHistorySet().stream().collect(Collectors.toList());	
		logger.info("All medical histories for given user listed.");
		return histories;
	}
	
	public Optional<MedicalHistory> getMedicalHistoryForUserBySpec(User user, int specId) {
		MedicalHistory medicalHistory = null;
		Set<MedicalHistory> medicalHistorySet = user.getMedicalHistorySet();

		for (MedicalHistory medHistory : medicalHistorySet) {
			if (medHistory.getSpecId() == specId) {
				medicalHistory = medHistory;
				break;
			}
		}
		return Optional.ofNullable(medicalHistory);
	}

}
