package com.deloitte.hux.netflix.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deloitte.hux.middleware.APIRequest;
import com.deloitte.hux.netflix.handler.RecordWithMovieTypeHandler;
import com.deloitte.hux.netflix.handler.RecordsWithTVshowsTypeHandler;
import com.deloitte.hux.netflix.handler.TVshowsInACountryHandler;
import com.deloitte.hux.netflix.handler.TVshowsInDateRangeHandler;

@RestController
@RequestMapping("/api")
public class NetflixDataRestController {

	/*
	 * This method will return list of records from csv where type is TV Show
	 */
	@GetMapping(value = "/tvshows", produces = "application/json",params = "count")
	public ResponseEntity<?> getRecordWithTVshowsType(@Autowired HttpServletRequest servletRequest,@RequestParam Integer count) {
		try {
			APIRequest recordsWithTVshowsTypeHandler = new RecordsWithTVshowsTypeHandler(count);
			return recordsWithTVshowsTypeHandler.doProcess();

		} catch (Exception e) {
			System.out.println(e);
			return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		}
	}
	
	@GetMapping(value = "/tvshows", produces = "application/json",params = "movieType")
	public ResponseEntity<?> getRecordWithMovieType(@RequestParam String movieType) {
		try {
			
			APIRequest recordsWithTVshowsTypeHandler = new RecordWithMovieTypeHandler(movieType);
			return recordsWithTVshowsTypeHandler.doProcess();

		} catch (Exception e) {
			return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		}
	}
	
	@GetMapping(value = "/tvshows", produces = "application/json",params = {"startDate","endDate"})
	public ResponseEntity<?> getTVshowsInACountry(@RequestParam String startDate,@RequestParam String endDate) {
		try {
			
			APIRequest recordsWithTVshowsTypeHandler = new TVshowsInDateRangeHandler(startDate, endDate);
			return recordsWithTVshowsTypeHandler.doProcess();

		} catch (Exception e) {
			return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		}
	}
	
	@GetMapping(value = "/tvshows", produces = "application/json",params = "country")
	public ResponseEntity<?> getTVshowsInDateRange(@RequestParam String country) {
		try {
			
			APIRequest recordsWithTVshowsTypeHandler = new TVshowsInACountryHandler(country);
			return recordsWithTVshowsTypeHandler.doProcess();

		} catch (Exception e) {
			return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		}
	}
}
