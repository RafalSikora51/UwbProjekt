package pl.edu.uwb.server.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * The Class SessionConnection.
 * 
 * @author acewiczm
 */
public class SessionConnection {

	/** The Log4j2 logger. */
	static Logger logger = LogManager.getLogger(SessionConnection.class);

	/** The Constant sessionFactory. */
	private static final SessionFactory sessionFactory = buildSessionFactory();

	/**
	 * Builds the session factory.
	 *
	 * @return the session factory
	 */
	private static SessionFactory buildSessionFactory() {
		logger.debug("buildSessionFactory");
		try {
			return new Configuration().configure().buildSessionFactory();
		} catch (Throwable exception) {
			logger.error("Initial SessionFactory creation failed.", exception);
			throw new ExceptionInInitializerError(exception);
		}
	}

	/**
	 * Gets the session factory.
	 *
	 * @return the session factory
	 */
	public static SessionFactory getSessionFactory() {
		logger.debug("getSessionFactory");
		return sessionFactory;
	}

	/**
	 * Closing session.
	 *
	 * @param session the session
	 */
	public static void shutdown(Session session) {
		logger.debug("shutdown");
		session.close();
	}

}
