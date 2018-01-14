package pl.edu.uwb.server.util;

import java.util.HashMap;

import org.apache.commons.text.CharacterPredicate;
import org.apache.commons.text.RandomStringGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TokenGenerator {

	private static final Logger logger = LogManager.getLogger(TokenGenerator.class);

	public static HashMap<String, String> randomStringGenerator(int tokenLength) {
		logger.debug("randomStringGenerator");
		CharacterPredicate charPredicate = i -> i != 34 && i != 35 && i != 37 && i != 38 && i != 39 && i != 59
				&& i != 61 && i != 63 && i != 92 && i != 94 && i != 96;
		RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange(33, 122)
				.filteredBy(charPredicate).build();
		logger.info("Random string generated.");
		String token = generator.generate(tokenLength);
		HashMap<String, String> tokenMap = new HashMap<String, String>();
		tokenMap.put("token", token);
		return tokenMap;
	}

}