package pl.edu.uwb.server.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
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
import pl.edu.uwb.server.entity.Doctor;
import pl.edu.uwb.server.entity.Specialization;
import pl.edu.uwb.server.entity.Token;
import pl.edu.uwb.server.entity.User;
import pl.edu.uwb.server.util.SessionConnection;
import pl.edu.uwb.server.util.TokenGenerator;

@Component
public class DoctorDao {

	private static Logger logger = LogManager.getLogger(UserDao.class);

	@Autowired
	private SpecializationDao specializationDao;

	@SuppressWarnings("unchecked")
	public List<Doctor> findAllDoctors() {
		logger.debug("findAllDoctors");
		List<Doctor> doctors;
		Session session = SessionConnection.getSessionFactory().openSession();
		doctors = session.createQuery("from Doctor").list();
		SessionConnection.shutdown(session);
		logger.info("All doctors listed.");
		return doctors;
	}

	public Optional<Doctor> findDoctorById(int id) {
		logger.debug("findUserById");
		Session session = SessionConnection.getSessionFactory().openSession();
		if (session.get(Doctor.class, id) != null) {
			Doctor doctor = session.load(Doctor.class, id);
			SessionConnection.shutdown(session);
			logger.info("Doctor found by id.");
			return Optional.ofNullable(doctor);
		} else {
			SessionConnection.shutdown(session);
			logger.info("There is no such doctor.");
			return Optional.empty();
		}
	}

	public Optional<Doctor> findDoctorByEmail(String email) {
		logger.debug("findUserByEmail");
		Doctor doctor;
		Session session = SessionConnection.getSessionFactory().openSession();
		doctor = session.byNaturalId(Doctor.class).using("email", email).load();
		SessionConnection.shutdown(session);
		return Optional.ofNullable(doctor);
	}

	@SuppressWarnings("unchecked")
	public Optional<Doctor> findDoctorByCountryId(String countryId) {
		logger.debug("findDoctorByCountryId");
		List<Doctor> doctors;
		Session session = SessionConnection.getSessionFactory().openSession();
		String hql = "from Doctor where PESEL = :countryId";
		Query query = session.createQuery(hql);
		query.setParameter("countryId", countryId);
		doctors = query.list();
		SessionConnection.shutdown(session);
		if (!doctors.isEmpty()) {
			return Optional.ofNullable(doctors.get(0));
		} else {
			return Optional.empty();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Doctor> findDoctorsBySpecId(int id) {
		List<Doctor> doctors;
		Session session = SessionConnection.getSessionFactory().openSession();
		String hql = "from Doctor where SPECIALIZATIONID = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		doctors = query.list();
		SessionConnection.shutdown(session);
		return doctors;
	}

	public void createDoctor(Doctor doctor, String specName) {
		Session session = SessionConnection.getSessionFactory().openSession();
		session.beginTransaction();
		doctor.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		String token = TokenGenerator.randomStringGenerator(10).get("token");
		Token userToken = new Token(token, doctor);
		doctor.getTokenSet().add(userToken);
		Specialization spec = specializationDao.getSpecByName(specName).get();
		doctor.setSpecId(spec.getId());
		session.save(doctor);
		session.save(userToken);
		session.getTransaction().commit();
		SessionConnection.shutdown(session);
		logger.info("Doctor created correctly.");
	}

	public static Predicate<User> isUserAlreadyInList(List<User> users) {
		return p -> !users.contains(p);
	}

	public List<User> findAllUsersByDoctor(Doctor doctor) {
		logger.debug("findAllUsersByDoctor");
		List<Appointment> appointments = doctor.getAppointmentSet().stream().collect(Collectors.toList());
		List<User> users = new ArrayList<>();
		appointments.forEach(x -> users.add(x.getUser()));
		logger.info("All users listed.");
		return users.stream().distinct().collect(Collectors.toList());
	}

	public List<User> findAllUsersByDoctor(int docId) throws Exception {
		logger.debug("findAllUsersByDoctor");
		Optional<Doctor> docOptional = findDoctorById(docId);

		if (docOptional.isPresent()) {
			List<Appointment> appointments = docOptional.get().getAppointmentSet().stream()
					.collect(Collectors.toList());
			List<User> users = new ArrayList<>();
			appointments.forEach(x -> users.add(x.getUser()));
			logger.info("All users listed.");
			return users.stream().distinct().collect(Collectors.toList());
		} else {
			throw new Exception("Doctor not found");
		}

	}

}
