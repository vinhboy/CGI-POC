package com.cgi.poc.dw.service;

import javax.ws.rs.core.Response;

import com.cgi.poc.dw.dao.model.User;

public interface LocalizationService {
	
	Response setLocalization(User user);
	
}
