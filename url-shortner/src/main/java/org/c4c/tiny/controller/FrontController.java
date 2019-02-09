package org.c4c.tiny.controller;

import org.c4c.tiny.domain.UrlData;
import org.c4c.tiny.services.UrlDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class FrontController extends BaseController{
	
	@Autowired
	private UrlDataService _service;
	
	@GetMapping(value = "/{id}")
	public @ResponseBody ResponseEntity<?>redirect(@PathVariable("id") String id){
		UrlData data = this._service.findById(id);
		if(data == null)
		{
			return sendNotFound();
		}else {
			return sendRedirect(data.getUrl());
		}
	}

}
