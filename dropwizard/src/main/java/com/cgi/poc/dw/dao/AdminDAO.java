package com.cgi.poc.dw.dao;

import com.cgi.poc.dw.dao.model.EventNotification;
import com.google.inject.Inject;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

public class AdminDAO extends AbstractDAO<EventNotification> {

  @Inject
  public AdminDAO(SessionFactory factory) {
    super(factory);
  }

  public EventNotification save(EventNotification eventNotification) {
    eventNotification = this.persist(eventNotification);
    return eventNotification;
  }

}
