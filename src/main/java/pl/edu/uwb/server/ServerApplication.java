package pl.edu.uwb.server;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The Class ServerApplication.
 * 
 * @author acewiczm
 */
@SpringBootApplication
public class ServerApplication {

	/** The Log4j2 logger. */
	private static final Logger logger = LogManager.getLogger(ServerApplication.class);

	/**
	 * The main method starting server.
	 *
	 * @param args the arguments
	 * @throws SQLException the SQL exception
	 */
	public static void main(String[] args) throws SQLException {
		SpringApplication.run(ServerApplication.class);
		logger.info("Server started");

	}
}
