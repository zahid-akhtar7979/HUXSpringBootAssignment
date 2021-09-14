package com.deloitte.hux.netflix.handler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import com.deloitte.hux.manager.NetflixDataOperations;
import com.deloitte.hux.middleware.APIRequest;
import com.deloitte.hux.utilities.NetflixUtilities;

public class RecordsWithTVshowsTypeHandler implements APIRequest{
	
	private final int count;

	public RecordsWithTVshowsTypeHandler(int count) {
		this.count = count;
	}

	@Override
	public ResponseEntity<?> doProcess() throws Exception {
		
		long start = System.currentTimeMillis();
		HashMap<String, Object> resultBody = new HashMap<>();
		
		Resource resource = new ClassPathResource("/static/netflix_titles.csv");
		Map<String, Integer>keyToIndexMap = new HashMap<String, Integer>();
		BufferedReader br = new BufferedReader(new FileReader(resource.getURI().getPath()));

		String firstLine = br.readLine();
		List<String> dataFields = NetflixUtilities.splitString(firstLine);

		for(int i = 0; i < dataFields.size(); i++) {
			keyToIndexMap.put(dataFields.get(i), i);
		}
		
		List<String>recordList = NetflixDataOperations.recordsWithTVtype(br,keyToIndexMap,count);
		br.close();
		
		long end = System.currentTimeMillis();
		long executionTime = end - start;
		
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.set("Execution-Time", String.valueOf(executionTime));
		
		resultBody.put("recordList", recordList);
		return ResponseEntity.ok().headers(responseHeaders).body(resultBody);
		
	}

}
