package pl.edu.uwb.server.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.edu.uwb.server.entity.Appointment;
import pl.edu.uwb.server.entity.AppointmentHour;
import pl.edu.uwb.server.entity.MedicalHistory;
import pl.edu.uwb.server.util.Helper;
import pl.edu.uwb.server.util.SessionConnection;

@Component
public class AppointmentHourDao {

	private static Logger logger = LogManager.getLogger(AppointmentHourDao.class);

	@Autowired
	private AppointmentDao appointmentDao;

	public void createAppointmentHour(String hour) {
		logger.debug("createAppointmentHour");
		Session session = SessionConnection.getSessionFactory().openSession();
		session.beginTransaction();
		AppointmentHour appHour = new AppointmentHour(hour);
		session.save(appHour);
		session.getTransaction().commit();
		SessionConnection.shutdown(session);
		logger.info("New Appointment Hour added correctly.");
	}

	public static void createAppointmentHour(List<String> hours) {
		logger.debug("createAppointmentHour");
		Session session = SessionConnection.getSessionFactory().openSession();
		session.beginTransaction();
		if (!hours.isEmpty()) {
			for (String hour : hours) {
				if (!getAppointmentHourByHour(hour).isPresent()) {
					AppointmentHour appHour = new AppointmentHour(hour);
					session.save(appHour);
					logger.info("New Appointment Hour added correctly.");
				}
			}
		}
		session.getTransaction().commit();
		SessionConnection.shutdown(session);

	}

	@SuppressWarnings("unchecked")
	public List<AppointmentHour> findAllAppointmentHours() {
		logger.debug("findAllAppointmentHours");
		List<AppointmentHour> hours;
		Session session = SessionConnection.getSessionFactory().openSession();
		hours = session.createQuery("from AppointmentHour").list();
		SessionConnection.shutdown(session);
		logger.info("All Appointment Hours listed.");
		return hours;
	}

	public static Optional<AppointmentHour> getAppointmentHourByHour(String hour) {
		logger.debug("getAppointmentHourByHour");
		AppointmentHour appHour = null;
		Session session = SessionConnection.getSessionFactory().openSession();
		appHour = session.byNaturalId(AppointmentHour.class).using("hour", hour).load();
		SessionConnection.shutdown(session);
		return Optional.ofNullable(appHour);
	}

	public Optional<AppointmentHour> getAppointmentHourById(int id) {
		logger.debug("getAppointmentHourById");
		Session session = SessionConnection.getSessionFactory().openSession();
		if (session.get(AppointmentHour.class, id) != null) {
			AppointmentHour appHour = session.load(AppointmentHour.class, id);
			SessionConnection.shutdown(session);
			logger.info("Appointment Hour found by id.");
			return Optional.ofNullable(appHour);
		} else {
			SessionConnection.shutdown(session);
			logger.info("There is no such Appointment Hour.");
			return Optional.empty();
		}
	}

	public static void insertHoursIntoDataBase() {
		logger.info("insertHoursIntoDataBase");
		List<String> hours = Arrays.asList("7:00", "7:30", "8:00", "8:30", "9:00", "9:30", "10:00", "10:30", "11:00",
				"11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30");
		createAppointmentHour(hours);
	}

	public List<AppointmentHour> findAllTakenHoursForDoctorFromGivenDay(int docId, int day, int month, int year) {
		logger.debug("findAllTakenHoursForDoctorFromGivenDay");
		List<Appointment> doctorAppointmentsByDay = appointmentDao.findAllAppointmentsForDoctorFromGivenDay(docId, day,
				month, year);
		List<AppointmentHour> takenHours = new ArrayList<>();
		doctorAppointmentsByDay.forEach(x -> takenHours.add(getAppointmentHourById(x.getAppHourId()).get()));
		return takenHours;
	}

	public static Predicate<AppointmentHour> isHourAlreadyInList(List<AppointmentHour> takenHours) {
		return p -> !takenHours.contains(p);
	}

	public List<AppointmentHour> findAllNotTakenHoursForDoctorFromGivenDay(int docId, int day, int month, int year) {
		List<AppointmentHour> takenHours = findAllTakenHoursForDoctorFromGivenDay(docId, day, month, year);
		return findAllAppointmentHours().stream().filter(isHourAlreadyInList(takenHours)).collect(Collectors.toList());
	}

	public List<AppointmentHour> findAllNotTakenHoursForDoctorFromGivenDay(int docId, String date) {
		Map<String, Integer> dateMap = Helper.createMapFromDate(date);
		return findAllNotTakenHoursForDoctorFromGivenDay(docId, dateMap.get("day"), dateMap.get("month"),
				dateMap.get("year"));
	}

	public List<AppointmentHour> findAllTakenHoursForDoctorFromGivenDay(int docId, String date) {
		Map<String, Integer> dateMap = Helper.createMapFromDate(date);
		return findAllTakenHoursForDoctorFromGivenDay(docId, dateMap.get("day"), dateMap.get("month"),
				dateMap.get("year"));
	}
	
}
