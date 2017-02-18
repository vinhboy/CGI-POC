package com.cgi.poc.dw.jobs;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cgi.poc.dw.JobsConfiguration;
import com.cgi.poc.dw.api.service.APICallerService;
import com.cgi.poc.dw.api.service.APIServiceFactory;
import com.cgi.poc.dw.api.service.impl.APICallerServiceImpl;
import com.cgi.poc.dw.api.service.impl.FireEventAPICallerServiceImpl;
import com.cgi.poc.dw.dao.EventFloodDAO;
import com.cgi.poc.dw.dao.EventWeatherDAO;
import com.cgi.poc.dw.dao.FireEventDAO;
import com.google.inject.Inject;

import io.dropwizard.lifecycle.Managed;
import javax.ws.rs.InternalServerErrorException;

/**
 * 
 * @author vincent baylly
 *
 */
public class JobExecutionService implements Managed{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JobExecutionService.class);

    private final ScheduledExecutorService service;
    
    @Inject
    JobFactory jobFactory;
    @Inject
    APIServiceFactory aPIServiceFactory;
    @Inject
    JobsConfiguration jobsConfiguration;
    @Inject
    Client client;
    @Inject
    FireEventDAO fireEventDAO;
    @Inject
    EventWeatherDAO eventWeatherDAO;
    @Inject
    EventFloodDAO eventFloodDAO;
	/**
	 * @param conf the job configuration
	 */
    @Inject
	public JobExecutionService(JobsConfiguration jobsConfiguration) {
		super();
		this.service = Executors.newScheduledThreadPool(jobsConfiguration.getThread());
	}

	/**
     * start the scheduler for the launch the jobs
     */
    @Override
    public void start() throws Exception {
    	LOGGER.debug("Starting jobs");
    	if (jobsConfiguration != null && jobsConfiguration.getJobs() != null ) {
    	   for(JobParameter jobParam : jobsConfiguration.getJobs()){
    		LOGGER.debug("Instanciate job : {}", jobParam.toString());
                switch (jobParam.getEventType()) {
                   case "Fire":
                        service.scheduleAtFixedRate(
                                jobFactory.create(aPIServiceFactory.create(
                                        client, jobParam.getEventURL(), fireEventDAO)),
                                jobParam.getDelay(), jobParam.getPeriod(), 
                                TimeUnit.valueOf(jobParam.getTimeUnit()));
                        break;
                   case "Weather":
                        service.scheduleAtFixedRate(
                                jobFactory.create(aPIServiceFactory.create(
                                        client, jobParam.getEventURL(), eventWeatherDAO)),
                                jobParam.getDelay(), jobParam.getPeriod(), 
                                TimeUnit.valueOf(jobParam.getTimeUnit()));
                      break;
                   case "Flood":
                        service.scheduleAtFixedRate(
                                jobFactory.create(aPIServiceFactory.create(
                                        client, jobParam.getEventURL(), eventFloodDAO)),
                                jobParam.getDelay(), jobParam.getPeriod(), 
                                TimeUnit.valueOf(jobParam.getTimeUnit()));
                      break;
                   default:
                     throw new IllegalArgumentException("Unsupported Event type");
                }
                

           }
    	}

    }

    @Override
    public void stop() throws Exception {
    	LOGGER.debug("Shutting down");
        service.shutdown();
    }
	
}
