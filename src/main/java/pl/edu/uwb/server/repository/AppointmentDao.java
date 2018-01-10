package pl.edu.uwb.server.repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.edu.uwb.server.entity.Appointment;
import pl.edu.uwb.server.entity.Doctor;
import pl.edu.uwb.server.entity.MedicalHistory;
import pl.edu.uwb.server.entity.User;
import pl.edu.uwb.server.util.SessionConnection;

@Component
public class AppointmentDao {

	private static Logger logger = LogManager.getLogger(SpecializationDao.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private DoctorDao doctorDao;

	@Autowired
	private MedicalHistoryDao medicalHistoryDao;

	public void createAppointment(int userId, int doctorId) {
		logger.debug("createAppointment");
		Session session = SessionConnection.getSessionFactory().openSession();
		session.beginTransaction();
		User user = userDao.findUserById(userId).get();
		Doctor doctor = doctorDao.findDoctorById(doctorId).get();
		int specId = doctor.getSpecId();
		MedicalHistory medHistory = null;
		Optional<MedicalHistory> medHistoryOptional = medicalHistoryDao.getMedicalHistoryForUserBySpec(user, specId);

		if (medHistoryOptional.isPresent()) {
			medHistory = medHistoryOptional.get();
		} else {
			medHistory = new MedicalHistory(user, specId);
		}

		Appointment appointment = new Appointment(user, doctor, medHistory);
		medHistory.getAppointmentSet().add(appointment);
		user.getAppointmentSet().add(appointment);
		doctor.getAppointmentSet().add(appointment);
		session.save(appointment);
		session.saveOrUpdate(medHistory);
		session.getTransaction().commit();
		SessionConnection.shutdown(session);
		logger.info("New appointment added correctly.");
	}

	@SuppressWarnings("unchecked")
	public List<Appointment> findAllAppointments() {
		logger.debug("findAllAppointments");
		List<Appointment> appointments;
		Session session = SessionConnection.getSessionFactory().openSession();
		appointments = session.createQuery("from Appointment").list();
		SessionConnection.shutdown(session);
		logger.info("All appointments listed.");
		return appointments;
	}

	public List<Appointment> findAllAppointmentsForUser(User user) {
		logger.debug("findAllAppointmentsForUser");
		List<Appointment> appointments = user.getAppointmentSet().stream().collect(Collectors.toList());
		logger.info("All appointments for user listed.");
		return appointments;
	}

	public List<Appointment> findAllAppointmentsForUser(int userId) throws Exception {
		logger.debug("findAllAppointmentsForUser");
		Optional<User> userOptional = userDao.findUserById(userId);
		if (userOptional.isPresent()) {
			return findAllAppointmentsForUser(userOptional.get());
		} else
			throw new Exception("User not found");
	}

	public static Predicate<Appointment> isAppointmentFromGivenSpec(int specId) {
		return p -> p.getDoctor().getSpecId() == specId;
	}

	public List<Appointment> findAllAppointmentsForUserBySpecialization(User user, int specId) {
		logger.debug("findAllAppointmentsForUserBySpecialization");
		logger.info("All appointments for user from given specialization listed.");
		return findAllAppointmentsForUser(user).stream().filter(isAppointmentFromGivenSpec(specId))
				.collect(Collectors.toList());
	}

	public List<Appointment> findAllAppointmentsForUserBySpecialization(int userId, int specId) throws Exception {
		logger.debug("findAllAppointmentsForUser");
		Optional<User> userOptional = userDao.findUserById(userId);
		if (userOptional.isPresent()) {
			return findAllAppointmentsForUserBySpecialization(userOptional.get(), specId);
		} else
			throw new Exception("User not found");
	}

}
