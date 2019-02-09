package org.c4c.tiny.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

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
	
	protected String getLoggedinUser() {
		String username = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
		  username = ((UserDetails)principal).getUsername();
		} else {
		  username = principal.toString();
		}
		return username;
	}

}
