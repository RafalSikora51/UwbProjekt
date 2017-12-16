package pl.edu.uwb.server.repository;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.edu.uwb.server.entity.Appointment;
import pl.edu.uwb.server.entity.Doctor;
import pl.edu.uwb.server.entity.User;
import pl.edu.uwb.server.util.SessionConnection;

@Component
public class AppointmentDao {

	private static Logger logger = LogManager.getLogger(SpecializationDao.class);
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private DoctorDao doctorDao;

	public void createAppointment(int userId, int doctorId) {
		logger.debug("createAppointment");
		Session session = SessionConnection.getSessionFactory().openSession();
		session.beginTransaction();
		Appointment appointment = new Appointment();
		User user = userDao.findUserById(userId).get();
		Doctor doctor = doctorDao.findDoctorById(doctorId).get();
		user.getAppointmentSet().add(appointment);
		doctor.getAppointmentSet().add(appointment);
		appointment.setUser(user);
		appointment.setDoctor(doctor);
		session.save(appointment);
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

}
