package com.deloitte.hux.middleware;

import org.springframework.http.ResponseEntity;

public interface APIRequest {
	public abstract ResponseEntity<?> doProcess() throws Exception;
}
