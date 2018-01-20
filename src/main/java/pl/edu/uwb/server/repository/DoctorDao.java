package pl.edu.uwb.server.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
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

import pl.edu.uwb.server.entity.Appointment;
import pl.edu.uwb.server.entity.Doctor;
import pl.edu.uwb.server.entity.Specialization;
import pl.edu.uwb.server.entity.Token;
import pl.edu.uwb.server.entity.User;
import pl.edu.uwb.server.util.MailService;
import pl.edu.uwb.server.util.SessionConnection;
import pl.edu.uwb.server.util.TokenGenerator;

@Component
public class DoctorDao {

	private static Logger logger = LogManager.getLogger(UserDao.class);

	private static final String NOTACCEPTABLE = "NOTACCEPTABLE";
	private static final String CONFLICT = "CONFLICT";
	private static final String CREATED_VALUE = "CREATED";
	private static final String STATUS = "status";
	private static final String CREATED_KEY = "created";

	@Autowired
	private SpecializationDao specializationDao;

	@Autowired
	private TokenDao tokenDao;

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

	public boolean validDoctorDetails(Doctor doctor) {
		boolean countryId = doctor.getCountryId().length() >= 11;
		boolean names = doctor.getFirstName().length() >= 3 && doctor.getLastName().length() >= 3;
		boolean email = doctor.getEmail().contains("@") && doctor.getEmail().contains(".");
		return countryId && names && email;
	}

	public boolean createDoctor(Doctor doctor, String specName) {
		if (validDoctorDetails(doctor)) {
			Session session = SessionConnection.getSessionFactory().openSession();
			session.beginTransaction();
			doctor.setCreatedOn(new Timestamp(System.currentTimeMillis()));
			Specialization spec = specializationDao.getSpecByName(specName).get();
			doctor.setSpecId(spec.getId());
			session.save(doctor);
			String token = TokenGenerator.randomStringGenerator(10).get("token");
			Token userToken = new Token(token, doctor);
			doctor.getTokenSet().add(userToken);

			if (MailService.sendEmail(doctor.getEmail(), token, doctor.getFirstName(), doctor.getLastName())) {
				session.save(userToken);
				session.getTransaction().commit();
				SessionConnection.shutdown(session);
				logger.info("Doctor created correctly.");
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

	public JSONObject createUserJSON(Doctor doctor, String specName) {
		JSONObject jsonResponse = new JSONObject();

		if (findDoctorByEmail(doctor.getEmail()).isPresent()
				|| findDoctorByCountryId(doctor.getCountryId()).isPresent()) {
			logger.debug("Doctor already exists");
			jsonResponse.put(CREATED_KEY, false);
			jsonResponse.put(STATUS, CONFLICT);
		} else if (createDoctor(doctor, specName)) {
			jsonResponse.put(CREATED_KEY, true);
			jsonResponse.put(STATUS, CREATED_VALUE);
		} else {
			jsonResponse.put(CREATED_KEY, false);
			jsonResponse.put(STATUS, NOTACCEPTABLE);
		}
		return jsonResponse;
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

	public boolean isDoctorInDataBase(String email, String token) {
		logger.debug("isDoctorInDataBase");
		if (!findDoctorByEmail(email).isPresent()) {
			logger.info("No such doctor in data base.");
			return false;
		} else if (findDoctorByEmail(email).isPresent() && !tokenDao
				.findActiveTokenByDoctorId(findDoctorByEmail(email).get().getId()).getToken().equals(token)) {
			logger.info("The token is invalid.");
			return false;
		} else {
			logger.info("Doctor found in data base.");
			return true;
		}
	}

	public boolean isDoctorAdmin(Doctor doctor) {
		return doctor.isAdmin();
	}

	@SuppressWarnings("unchecked")
	public JSONObject isDoctorInDataBaseJSON(String email, String token) {
		logger.debug("isDoctorInDataBaseJSON");
		JSONObject jsonResponse = new JSONObject();
		if (isDoctorInDataBase(email, token)) {
			Doctor doctor = findDoctorByEmail(email).get();
			jsonResponse.put("canLogin", true);
			jsonResponse.put("admin", isDoctorAdmin(doctor)); // jesli true to lekarz ma tez admina (wlasciciel kliniki)
		} else {
			jsonResponse.put("canLogin", false);
		}
		return jsonResponse;
	}

	public int findDoctorIdByEmail(String email) throws Exception {
		logger.debug("findAllMedicalHistoriesForUserBySpecialization");
		if (StringUtils.isNotBlank(email)) {
			Optional<Doctor> doctorOptional = findDoctorByEmail(email);
			if (doctorOptional.isPresent()) {
				return doctorOptional.get().getId();
			} else
				throw new Exception("Doctor not found");
		} else
			throw new Exception("Email cant be blank");

	}
	
	public boolean changeDoctorAdminRights(int id) {
		Session session = SessionConnection.getSessionFactory().openSession();
		Optional<Doctor> doctorOptional = findDoctorById(id);
		if (doctorOptional.isPresent()) {
			session.beginTransaction();
			Doctor doctor = doctorOptional.get();
			doctor.setAdmin(!doctor.isAdmin());
			session.saveOrUpdate(doctor);
			session.getTransaction().commit();
			SessionConnection.shutdown(session);
			return true;
		} else {
			SessionConnection.shutdown(session);
			return false;
		}
	}

}
