package pl.edu.uwb.server.util;

import java.util.HashMap;
import java.util.Map;

public class Helper {

	public static Map<String, Integer> createMapFromDate(String date) {
		Map<String, Integer> dateParams = new HashMap<>();
		String[] splittedDate = date.split("\\.");
		dateParams.put("day", Integer.parseInt(splittedDate[0]));
		dateParams.put("month", Integer.parseInt(splittedDate[1]));
		dateParams.put("year", Integer.parseInt(splittedDate[2]));
		return dateParams;
	}

}
