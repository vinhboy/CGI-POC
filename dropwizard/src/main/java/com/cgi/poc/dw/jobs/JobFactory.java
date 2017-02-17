package com.cgi.poc.dw.jobs;

import com.cgi.poc.dw.api.service.APICallerService;
import com.cgi.poc.dw.api.service.impl.EventWeatherAPICallerServiceImpl;
import com.cgi.poc.dw.api.service.impl.FireEventAPICallerServiceImpl;

public interface JobFactory {
	
	PollingDataJob create(FireEventAPICallerServiceImpl apiCallerService);
	PollingDataJob create(EventWeatherAPICallerServiceImpl apiCallerService);
}
