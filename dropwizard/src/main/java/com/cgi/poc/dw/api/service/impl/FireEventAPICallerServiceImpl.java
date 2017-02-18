/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgi.poc.dw.api.service.impl;

import com.cgi.poc.dw.dao.FireEventDAO;
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

public class FireEventAPICallerServiceImpl extends APICallerServiceImpl {

    private static final Logger LOG = LoggerFactory.getLogger(FireEventAPICallerServiceImpl.class);

    private FireEventDAO eventDAO;

    @Inject
    public FireEventAPICallerServiceImpl(String eventUrl, Client client, FireEventDAO fireEventDAO, SessionFactory sessionFactory) {
        super(eventUrl, client, sessionFactory);
        eventDAO = fireEventDAO;
    }

    public void mapAndSave(JsonNode eventJson, JsonNode geoJson) {
        ObjectMapper mapper = new ObjectMapper();

        Session session = sessionFactory.openSession();
        try {
            FireEvent event = mapper.readValue(eventJson.toString(), FireEvent.class);

            event.setGeometry(geoJson.toString());
            ManagedSessionContext.bind(session);
            Transaction transaction = session.beginTransaction();
            try {
                LOG.info("Event to save : {}", event.toString());
                // Archive users based on last login date
                ((FireEventDAO) eventDAO).save(event);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException(e);
            }
        } catch (IOException ex) {
            LOG.error("Unable to parse the result for the fire event : error: {}", ex.getMessage());
        } finally {
            session.close();
            ManagedSessionContext.unbind(sessionFactory);
        }

    }


}
