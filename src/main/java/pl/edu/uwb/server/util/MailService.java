package pl.edu.uwb.server.util;

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class MailService {

	private final static String userName = "janemailtesing@gmail.com";
	private final static String password = "wysylanie123";
	static String host = "smtp.gmail.com";
	private static final Logger log = LogManager.getLogger(MailService.class);

	public static boolean sendEmail(String userEmail, String token, String firstName, String lastName) {

		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "587");
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		});

		StringBuilder sb = new StringBuilder();
		String subject = "MediCare - hasło dostępu.";
		sb.append("Witaj ").append(firstName).append(" ").append(lastName).append("\n").append(
				"Wygenerowane zostało dla Ciebie hasło do logowania się w elektronicznym systemie naszej przychodni.\n");
		sb.append("Wiadomość z hasłem została wygenerowana automatycznie. Prosimy na nią nie odpowiadać. \n");
		sb.append("Hasło: ").append(token);

		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(userName));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));
			message.setSubject(subject);
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(sb.toString());
			Multipart multiPart = new MimeMultipart();
			multiPart.addBodyPart(messageBodyPart);
			message.setContent(multiPart);
			Transport.send(message);
			log.info("Authentication was successful.");
			log.info("Email with new token sent. ");
			log.debug("Sent email to '{}'", userEmail);
			return true;
		} catch (MessagingException exception) {
			log.error("Email could not be sent to user '{}'", userEmail);
			log.error(exception.getMessage());
			return false;
		}
	}

}
