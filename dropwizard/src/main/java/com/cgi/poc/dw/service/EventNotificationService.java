package com.cgi.poc.dw.service;

import com.cgi.poc.dw.dao.model.User;
import com.cgi.poc.dw.rest.model.EventNotificationDto;
import javax.ws.rs.core.Response;

public interface EventNotificationService {

  Response publishNotification(User principal, EventNotificationDto eventNotificationDto);
  Response retrieveAllNotifications(User user);
  Response retrieveNotificationsForUser(User user);

}
