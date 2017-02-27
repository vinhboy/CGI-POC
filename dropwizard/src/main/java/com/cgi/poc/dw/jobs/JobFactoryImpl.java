/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgi.poc.dw.jobs;

import com.cgi.poc.dw.api.service.impl.EventFloodAPICallerServiceImpl;
import com.cgi.poc.dw.api.service.impl.EventWeatherAPICallerServiceImpl;
import com.cgi.poc.dw.api.service.impl.FireEventAPICallerServiceImpl;

/**
 *
 * @author dawna.floyd
 */
public class JobFactoryImpl implements JobFactory{
    
    @Override
    public PollingDataJob create(FireEventAPICallerServiceImpl apiCallerService) {
        return  new PollingDataJob(apiCallerService);
    }

    @Override
    public PollingDataJob create(EventWeatherAPICallerServiceImpl apiCallerService) {
        return  new PollingDataJob(apiCallerService);
    }

    @Override
    public PollingDataJob create(EventFloodAPICallerServiceImpl apiCallerService) {
        return  new PollingDataJob(apiCallerService);
    }
    
}
