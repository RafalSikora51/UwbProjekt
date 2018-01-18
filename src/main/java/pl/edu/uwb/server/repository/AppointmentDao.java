package pl.edu.uwb.server.repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.edu.uwb.server.entity.Appointment;
import pl.edu.uwb.server.entity.AppointmentHour;
import pl.edu.uwb.server.entity.Doctor;
import pl.edu.uwb.server.entity.MedicalHistory;
import pl.edu.uwb.server.entity.Token;
import pl.edu.uwb.server.entity.User;
import pl.edu.uwb.server.util.Helper;
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

	public boolean createAppointment(int userId, int doctorId, int appHourId, int year, int month, int dayOfMonth) {
		logger.debug("createAppointment");
		Session session = SessionConnection.getSessionFactory().openSession();
		session.beginTransaction();

		Optional<User> userOptional = userDao.findUserById(userId);
		Optional<Doctor> doctorOptional = doctorDao.findDoctorById(doctorId);

		if (userOptional.isPresent() && doctorOptional.isPresent()) {
			User user = userDao.findUserById(userId).get();
			Doctor doctor = doctorDao.findDoctorById(doctorId).get();
			int specId = doctor.getSpecId();
			MedicalHistory medHistory = null;
			Optional<MedicalHistory> medHistoryOptional = medicalHistoryDao.getMedicalHistoryForUserBySpec(user,
					specId);

			if (medHistoryOptional.isPresent()) {
				medHistory = medHistoryOptional.get();
			} else {
				medHistory = new MedicalHistory(user, specId);
			}

			Appointment appointment = new Appointment(user, doctor, medHistory, appHourId, year, month, dayOfMonth);
			medHistory.getAppointmentSet().add(appointment);
			user.getAppointmentSet().add(appointment);
			doctor.getAppointmentSet().add(appointment);
			session.save(appointment);
			session.saveOrUpdate(medHistory);
			session.getTransaction().commit();
			SessionConnection.shutdown(session);
			logger.info("New appointment added correctly.");
			return true;
		} else {
			SessionConnection.shutdown(session);
			logger.info("Error during creating new appointment. User or doctor not found.");
			return false;
		}
	}

	public boolean createAppointment(String userEmail, int doctorId, int appHourId, int year, int month,
			int dayOfMonth) {
		Optional<User> userOptional = userDao.findUserByEmail(userEmail);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			return createAppointment(user.getId(), doctorId, appHourId, year, month, dayOfMonth);
		} else {
			return false;
		}
	}

	public boolean createAppointment(String userEmail, int doctorId, int appHourId, String date) {
		Optional<User> userOptional = userDao.findUserByEmail(userEmail);
		if (userOptional.isPresent()) {
			User user = userOptional.get();
			Map<String, Integer> dateMap = Helper.createMapFromDate(date);
			return createAppointment(user.getId(), doctorId, appHourId, dateMap.get("year"), dateMap.get("month"),
					dateMap.get("day"));
		} else {
			return false;
		}
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

	@SuppressWarnings("unchecked")
	public List<Appointment> findAllAppointmentsForDoctorFromGivenDay(int docId, int day, int month, int year) {
		logger.debug("findAppointmentsForDoctorFromGivenDay");
		Session session = SessionConnection.getSessionFactory().openSession();
		String hql = "from Appointment a where a.doctor = :doctor and a.localDate = :appDay";
		Query query = session.createQuery(hql);
		query.setParameter("doctor", doctorDao.findDoctorById(docId).get());
		query.setParameter("appDay", LocalDate.of(year, month, day).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
		List<Appointment> apps = query.list();
		SessionConnection.shutdown(session);
		if (apps.isEmpty()) {
			logger.info("Appointments list For Doctor From Given Day is empty.");
		}
		logger.info("Appointments For Doctor From Given Day found.");
		return apps;
	}

	public List<Appointment> findAllAppointmentsForDoctorFromGivenDay(int docId, String date) {
		Map<String, Integer> dateMap = Helper.createMapFromDate(date);
		return findAllAppointmentsForDoctorFromGivenDay(docId, dateMap.get("day"), dateMap.get("month"),
				dateMap.get("year"));
	}

	public List<Appointment> findAllAppointmentsForDoctor(Doctor doctor) {
		logger.debug("findAllAppointmentsForDoctor");
		List<Appointment> appointments = doctor.getAppointmentSet().stream().collect(Collectors.toList());
		logger.info("All appointments for doctor listed.");
		return appointments;
	}

	public List<Appointment> findAllAppointmentsForDoctor(int docId) throws Exception {
		logger.debug("findAllAppointmentsForDoctor");
		Optional<Doctor> doctorOptional = doctorDao.findDoctorById(docId);
		if (doctorOptional.isPresent()) {
			return findAllAppointmentsForDoctor(doctorOptional.get());
		} else
			throw new Exception("Doctor not found");
	}

}
