/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgi.poc.dw.api.service.impl;

 import com.cgi.poc.dw.dao.EventWeatherDAO;
import com.cgi.poc.dw.dao.model.EventWeather;
 import com.cgi.poc.dw.dao.model.FireEvent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
 import java.io.IOException;
 import javax.ws.rs.client.Client;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.context.internal.ManagedSessionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dawna.floyd
 */
public class EventWeatherAPICallerServiceImpl extends APICallerServiceImpl {

    private static final Logger LOG = LoggerFactory.getLogger(EventWeatherAPICallerServiceImpl.class);

    private EventWeatherDAO eventDAO;

    @Inject
    public EventWeatherAPICallerServiceImpl(String eventUrl, Client client, EventWeatherDAO weatherEventDAO, SessionFactory sessionFactory) {
        super(eventUrl, client, sessionFactory);
                eventDAO = weatherEventDAO;

    }

    public void mapAndSave(JsonNode eventJson, JsonNode geoJson) {
        ObjectMapper mapper = new ObjectMapper();

        Session session = sessionFactory.openSession();
        try {
            EventWeather event = mapper.readValue(eventJson.toString(), EventWeather.class);

            event.setGeometry(geoJson.toString());
            ManagedSessionContext.bind(session);
            Transaction transaction = session.beginTransaction();
            try {
                LOG.info("Event to save : {}", event.toString());
                // Archive users based on last login date
                ((EventWeatherDAO) eventDAO).update(event);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException(e);
            }
        } catch (IOException ex) {
            LOG.error("Unable to parse the result for the weather event : error: {}", ex.getMessage());
        } finally {
            session.close();
            ManagedSessionContext.unbind(sessionFactory);
        }

    }


}
