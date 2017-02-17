package com.cgi.poc.dw.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cgi.poc.dw.api.service.APICallerService;
import com.cgi.poc.dw.api.service.impl.EventWeatherAPICallerServiceImpl;
import com.cgi.poc.dw.api.service.impl.FireEventAPICallerServiceImpl;
import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * Job to call the rest API and get the events for specific types
 * 
 * @author vincent baylly
 *
 */
public class PollingDataJob implements Runnable{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PollingDataJob.class);
	
	private APICallerService apiCallerService;
	
	@Inject
	public PollingDataJob(@Named("fireService")FireEventAPICallerServiceImpl apiCallerService){
		this.apiCallerService = apiCallerService;
	}
	@Inject
	public PollingDataJob(@Named("weatherService")EventWeatherAPICallerServiceImpl apiCallerService){
		this.apiCallerService = apiCallerService;
	}
	
	/**
	 * run the job
	 */
	@Override
	public void run() {
		try{
			apiCallerService.callServiceAPI();
		}catch(Exception e){
			LOGGER.error("the job get an error {}", e.getMessage());
		}
	}

}
