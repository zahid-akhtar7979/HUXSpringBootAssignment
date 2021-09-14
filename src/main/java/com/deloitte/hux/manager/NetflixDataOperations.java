package com.deloitte.hux.manager;

import static com.deloitte.hux.utilities.Constant.COUNTRY;
import static com.deloitte.hux.utilities.Constant.DATE_ADDED;
import static com.deloitte.hux.utilities.Constant.LISTED_IN;
import static com.deloitte.hux.utilities.Constant.TV_SHOW;
import static com.deloitte.hux.utilities.Constant.TYPE;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.deloitte.hux.utilities.NetflixUtilities;

public class NetflixDataOperations {

	/*
	 * This method will return list of records from csv where type is TV Show
	 */
	public static List<String> recordsWithTVtype(BufferedReader br, Map<String, Integer> keyToIndexMap, Integer n)
			throws IOException, ParseException {

		long start = System.currentTimeMillis();
		String line = null;
		List<String> records = new ArrayList<String>();
		int noOfRecordsAdded = 0;

		while ((line = br.readLine()) != null) {

			List<String> list = NetflixUtilities.splitString(line);
			if (list.get(keyToIndexMap.get(TYPE)).equals(TV_SHOW) && noOfRecordsAdded <= n) {
				records.add(line);
				noOfRecordsAdded++;
			}
		}

		long end = System.currentTimeMillis();
		long executionTime = end - start;

		System.out.println("executionTime:" + executionTime);
		System.out.println(records.size());

		return records;
	}

	/*
	 * This method will return list of records from csv where movie is listed_in in
	 * horror category
	 */

	public static List<String> listedHorrorMovies(BufferedReader br, Map<String, Integer> keyToIndexMap,
			String movieType) throws IOException, ParseException {

		long start = System.currentTimeMillis();
		String line = null;
		List<String> records = new ArrayList<String>();

		while ((line = br.readLine()) != null) {

			List<String> list = NetflixUtilities.splitString(line);
			System.out.println(list);
			Set<String> recordSet = new HashSet<String>(
					Arrays.asList(list.get(keyToIndexMap.get(LISTED_IN)).split(",")));

			if (list.get(keyToIndexMap.get(TYPE)).equals(TV_SHOW) && recordSet.contains(movieType)) {
				records.add(line);
			}
		}

		long end = System.currentTimeMillis();
		long executionTime = end - start;

		System.out.println("executionTime:" + executionTime);
		System.out.println(records.size());

		return records;
	}

	/*
	 * This method will return list of records from csv where type is movie and
	 * country is India
	 */
	public static List<String> recordWithTypeMovieInCountry(BufferedReader br, Map<String, Integer> keyToIndexMap,
			String country) throws IOException, ParseException {

		long start = System.currentTimeMillis();
		String line = null;
		List<String> records = new ArrayList<String>();

		while ((line = br.readLine()) != null) {

			List<String> list = NetflixUtilities.splitString(line);
			Set<String> recordSet = new HashSet<String>(Arrays.asList(list.get(keyToIndexMap.get(COUNTRY)).split(",")));

			if (list.get(keyToIndexMap.get(TYPE)).equals(TV_SHOW) && recordSet.contains(country)) {
				records.add(line);
			}
		}

		long end = System.currentTimeMillis();
		long executionTime = end - start;

		System.out.println("executionTime:" + executionTime);

		System.out.println(records.size());

		return records;
	}

	public static List<String> recordWithinDateRange(BufferedReader br, Map<String, Integer> keyToIndexMap,
			String startDateString, String endDateString) throws IOException, ParseException {

		long start = System.currentTimeMillis();
		String line = null;
		List<String> records = new ArrayList<String>();

		Date startDate = new SimpleDateFormat("dd-MMM-yyyy").parse(startDateString);
		Date endDate = new SimpleDateFormat("dd-MMM-yyyy").parse(endDateString);

		while ((line = br.readLine()) != null) {

			List<String> list = NetflixUtilities.splitString(line);
			String dateString = list.get(keyToIndexMap.get(DATE_ADDED));

			if (!dateString.trim().isEmpty() && dateString != null) {
				String processedDateString = NetflixUtilities
						.getDateStringInStandardFormat(list.get(keyToIndexMap.get(DATE_ADDED)));
				Date date = new SimpleDateFormat("dd-MMM-yyyyy").parse(processedDateString);

				if (list.get(keyToIndexMap.get(TYPE)).equals(TV_SHOW)
						&& (!startDate.after(date) && !endDate.before(date))) {
					records.add(line);
				}
			}

		}
		long end = System.currentTimeMillis();
		long executionTime = end - start;

		System.out.println("executionTime:" + executionTime);

		System.out.println(records.size());

		return records;
	}

}
