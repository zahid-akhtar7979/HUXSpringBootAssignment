package com.deloitte.hux.utilities;

import java.util.ArrayList;
import java.util.List;

public class NetflixUtilities {

	/*
	 * Utility method to return list of fields data fetched from a record in csv
	 */
	public static List<String> splitString(String inputString) {

		List<String> resultList = new ArrayList<String>();

		StringBuilder stringBuilder = new StringBuilder();
		boolean isLastSeenQuote = false;

		for (int i = 0; i < inputString.length(); i++) {

			if (inputString.charAt(i) == '"') {
				isLastSeenQuote = isLastSeenQuote == false ? true : false;
			}
			if (!isLastSeenQuote && (inputString.charAt(i) == ',' || i == inputString.length() - 1)) {

				resultList.add(stringBuilder.toString());
				stringBuilder.delete(0, stringBuilder.length());
			} else {
				stringBuilder.append(inputString.charAt(i));
			}
		}
		return resultList;
	}

	public static String getDateStringInStandardFormat(String inputString) {

		String[] dateStringArray = inputString.replaceAll("\"", "").split("\\s*,\\s*");
		String year = dateStringArray[1];
		String dayAndMonth = dateStringArray[0];

		String[] dayAndMonthArray = dayAndMonth.split(" ");

		String dateString = dayAndMonthArray[1] + "-" + dayAndMonthArray[0].substring(0, 3) + "-" + year;
		System.out.println("dateString " + dateString);

		return dateString;
	}
}
