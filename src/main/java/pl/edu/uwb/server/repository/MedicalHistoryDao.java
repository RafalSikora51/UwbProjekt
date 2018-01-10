package pl.edu.uwb.server.repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import pl.edu.uwb.server.entity.MedicalHistory;
import pl.edu.uwb.server.entity.User;
import pl.edu.uwb.server.util.SessionConnection;

@Component
public class MedicalHistoryDao {

	private static Logger logger = LogManager.getLogger(SpecializationDao.class);

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

	public static Predicate<MedicalHistory> isHistoryFromGivenSpec(int specId) {
		return p -> p.getSpecId() == specId;
	}

	public Optional<MedicalHistory> getMedicalHistoryForUserBySpec(User user, int specId) {
		List<MedicalHistory> medHistories = user.getMedicalHistorySet().stream().filter(isHistoryFromGivenSpec(specId))
				.collect(Collectors.toList());
		return Optional.ofNullable(medHistories.get(0));
	}

}
