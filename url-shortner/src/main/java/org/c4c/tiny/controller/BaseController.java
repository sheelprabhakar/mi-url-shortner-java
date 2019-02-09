package org.c4c.tiny.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {
	protected HttpHeaders responseHeaders = new HttpHeaders();
	
	protected ResponseEntity<?> sendBadRequest() {
		HttpHeaders headers = new HttpHeaders();
		 return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
	}
	
	protected ResponseEntity<?> sendNotFound() {
		HttpHeaders headers = new HttpHeaders();
		 return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
	}
	
	protected ResponseEntity<?> sendRedirect(String url) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", url);
		return new ResponseEntity<>(headers, HttpStatus.PERMANENT_REDIRECT);
	}

}
