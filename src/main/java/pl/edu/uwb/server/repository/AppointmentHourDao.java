package pl.edu.uwb.server.repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import pl.edu.uwb.server.entity.AppointmentHour;
import pl.edu.uwb.server.util.SessionConnection;

@Component
public class AppointmentHourDao {

	private static Logger logger = LogManager.getLogger(AppointmentHourDao.class);

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
				}
			}
		}
		session.getTransaction().commit();
		SessionConnection.shutdown(session);
		logger.info("New Appointment Hours added correctly.");
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

}
