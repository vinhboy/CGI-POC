package com.cgi.poc.dw.service;

import com.cgi.poc.dw.api.service.MapsApiService;
import com.cgi.poc.dw.api.service.data.GeoCoordinates;
import com.cgi.poc.dw.dao.EventNotificationDAO;
import com.cgi.poc.dw.dao.UserDao;
import com.cgi.poc.dw.dao.model.EventNotification;
import com.cgi.poc.dw.dao.model.EventNotificationZipcode;
import com.cgi.poc.dw.dao.model.NotificationType;
import com.cgi.poc.dw.dao.model.User;
import com.cgi.poc.dw.dao.model.UserNotificationType;
import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Validator;
import javax.validation.groups.Default;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventNotificationServiceImpl extends BaseServiceImpl implements
    EventNotificationService {

  private final static Logger LOG = LoggerFactory.getLogger(EventNotificationServiceImpl.class);

  private UserDao userDao;

  private EventNotificationDAO eventNotificationDAO;

  private MapsApiService mapsApiService;

  private EmailService emailService;

  private TextMessageService textMessageService;

  @Inject
  public EventNotificationServiceImpl(UserDao userDao, EventNotificationDAO eventNotificationDAO,
      Validator validator, MapsApiService mapsApiService, EmailService emailService,
      TextMessageService textMessageService) {
    super(validator);
    this.userDao = userDao;
    this.eventNotificationDAO = eventNotificationDAO;
    this.mapsApiService = mapsApiService;
    this.emailService = emailService;
    this.textMessageService = textMessageService;
  }

  @Override
  public Response retrieveAllNotifications(User user) {
    List<EventNotification> resultList = eventNotificationDAO.retrieveAll();
    Response.ResponseBuilder respBuilder = Response.noContent().status(Response.Status.OK);
    respBuilder.header("X-Total-Count", Integer.valueOf(resultList.size()).toString());
    return respBuilder.entity(resultList).build();
  }

  @Override
  public Response retrieveNotificationsForUser(User user) {
    List<EventNotification> resultList = eventNotificationDAO.retrieveAllForUser(user);
    Response.ResponseBuilder respBuilder = Response.noContent().status(Response.Status.OK);
    respBuilder.header("X-Total-Count", Integer.valueOf(resultList.size()).toString());
    return respBuilder.entity(resultList).build();
  }

  @Override
  public Response publishNotification(EventNotification eventNotification) {
    validate(eventNotification, "eventNotification validation", Default.class);
    eventNotification = eventNotificationDAO.save(eventNotification);

    List<GeoCoordinates> geoCoordinates = new ArrayList<>();
    for (EventNotificationZipcode zipcode : eventNotification.getEventNotificationZipcodes()) {
      geoCoordinates.add(mapsApiService.getGeoCoordinatesByZipCode(zipcode.getZipCode()));
    }

    List<User> affectedUsers = userDao.getGeoWithinRadius(geoCoordinates, 50.00);

    List<String> emailAddresses = new ArrayList<>(); //subscribed users by email
    List<String> phoneNumbers = new ArrayList<>(); //subscribed users by sms

    for (User affectedUser : affectedUsers) {
      for (UserNotificationType notificationType : affectedUser.getNotificationType()) {
        if (notificationType.getNotificationId() == NotificationType.EMAIL.ordinal()) {
          emailAddresses.add(affectedUser.getEmail());
        } else if (notificationType.getNotificationId() == NotificationType.SMS.ordinal()) {
          phoneNumbers.add(affectedUser.getPhone());
        }
      }
    }

    if (emailAddresses.size() > 0) {
      String subject = "ADMIN_E".equals(eventNotification.getType()) ? "Emergency notification"
          : "Non-emergency notification";
      LOG.info("Admin: {} sending {} to: {}", eventNotification.getUserId().getEmail(), subject,
          emailAddresses.toString());
      emailService.send(null, emailAddresses, subject, eventNotification.getDescription());
    }

    if (phoneNumbers.size() > 0) {
      LOG.info("Admin: {} sending SMS to: {}", eventNotification.getUserId().getEmail(),
          phoneNumbers.toString());
      for (String phoneNumber : phoneNumbers) {
        textMessageService.send(phoneNumber, eventNotification.getDescription());
      }
    }

    return Response.ok().entity(eventNotification).build();
  }
}
