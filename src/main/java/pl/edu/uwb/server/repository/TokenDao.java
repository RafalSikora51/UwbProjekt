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


// TODO: Auto-generated Javadoc
/**
 * The Class TokenDao.
 * 
 * @author acewiczm
 */

@Component
public class TokenDao {

	/** The Log4j2 logger. */
	private static Logger logger = LogManager.getLogger(TokenDao.class);

	/** The user dao. */
	@Autowired
	private UserDao userDao;

	/**
	 * Find active token by user id.
	 *
	 * @param id the id
	 * @return the token
	 */
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

}