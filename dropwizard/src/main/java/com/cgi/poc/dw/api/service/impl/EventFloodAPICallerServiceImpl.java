/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgi.poc.dw.api.service.impl;

import com.cgi.poc.dw.dao.EventFloodDAO;
 import com.cgi.poc.dw.dao.model.EventFlood;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
 import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.context.internal.ManagedSessionContext;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dawna.floyd
 */
public class EventFloodAPICallerServiceImpl extends APICallerServiceImpl {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(EventFloodAPICallerServiceImpl.class);

    private EventFloodDAO eventDAO;

    @Inject
    public EventFloodAPICallerServiceImpl(String eventUrl, Client client, EventFloodDAO weatherEventDAO, SessionFactory sessionFactory) {
        super(eventUrl, client, sessionFactory);
                eventDAO = weatherEventDAO;

    }

    public void mapAndSave(JsonNode eventJson, JsonNode geoJson) {
        ObjectMapper mapper = new ObjectMapper();

        Session session = sessionFactory.openSession();
        try {
            EventFlood event = mapper.readValue(eventJson.toString(), EventFlood.class);

            event.setGeometry(geoJson.toString());
            ManagedSessionContext.bind(session);
            Transaction transaction = session.beginTransaction();
            try {
                LOGGER.info("Event to save : {}", event.toString());
                // Archive users based on last login date
                ((EventFloodDAO) eventDAO).save(event);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException(e);
            }
        } catch (IOException ex) {
            Logger.getLogger(EventFloodAPICallerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            session.close();
            ManagedSessionContext.unbind(sessionFactory);
        }

    }
;

}
