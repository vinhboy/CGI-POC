package com.cgi.poc.dw.api.service;

import com.cgi.poc.dw.api.service.impl.EventFloodAPICallerServiceImpl;
import com.cgi.poc.dw.api.service.impl.EventWeatherAPICallerServiceImpl;
import com.cgi.poc.dw.api.service.impl.FireEventAPICallerServiceImpl;
import com.cgi.poc.dw.dao.EventFloodDAO;
import javax.ws.rs.client.Client;

import com.cgi.poc.dw.dao.FireEventDAO;
import com.cgi.poc.dw.dao.EventWeatherDAO;

public interface APIServiceFactory {
	
	FireEventAPICallerServiceImpl create(Client client, String eventUrl, FireEventDAO eventDAO);
	EventWeatherAPICallerServiceImpl create(Client client, String eventUrl, EventWeatherDAO eventDAO);
	EventFloodAPICallerServiceImpl create(Client client, String eventUrl, EventFloodDAO eventDAO);
}
