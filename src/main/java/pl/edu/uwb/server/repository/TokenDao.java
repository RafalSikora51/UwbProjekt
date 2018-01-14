package pl.edu.uwb.server.repository;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.edu.uwb.server.entity.Token;
import pl.edu.uwb.server.util.SessionConnection;

@Component
public class TokenDao {

	private static Logger logger = LogManager.getLogger(TokenDao.class);

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private DoctorDao doctorDao;

	@SuppressWarnings("unchecked")
	public Token findActiveTokenByUserId(int id) {
		logger.debug("findActiveTokenByUserId");
		Session session = SessionConnection.getSessionFactory().openSession();
		String hql = "from Token t where t.user = :userId and t.active = :tokenActive";
		Query query = session.createQuery(hql);
		query.setParameter("userId", userDao.findUserById(id).get());
		query.setParameter("tokenActive", true);
		List<Token> tokens = query.list();
		SessionConnection.shutdown(session);
		logger.info("User active token found.");
		return tokens.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public Token findActiveTokenByDoctorId(int id) {
		logger.debug("findActiveTokenByDoctorId");
		Session session = SessionConnection.getSessionFactory().openSession();
		String hql = "from Token t where t.doctor = :doctorId and t.active = :tokenActive";
		Query query = session.createQuery(hql);
		query.setParameter("doctorId", doctorDao.findDoctorById(id).get());
		query.setParameter("tokenActive", true);
		List<Token> tokens = query.list();
		SessionConnection.shutdown(session);
		logger.info("Doctor active token found.");
		return tokens.get(0);
	}
	

}