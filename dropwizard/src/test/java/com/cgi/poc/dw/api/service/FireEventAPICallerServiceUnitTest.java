package com.cgi.poc.dw.api.service;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import com.cgi.poc.dw.api.service.impl.FireEventAPICallerServiceImpl;
import com.cgi.poc.dw.api.service.impl.FireEventAPICallerServiceImpl;
import com.cgi.poc.dw.auth.model.Role;
import com.cgi.poc.dw.dao.FireEventDAO;
import com.cgi.poc.dw.dao.EventNotificationDAO;
import com.cgi.poc.dw.dao.FireEventDAO;
import com.cgi.poc.dw.dao.UserDao;
import com.cgi.poc.dw.dao.model.FireEvent;
import com.cgi.poc.dw.dao.model.FireEvent;
import com.cgi.poc.dw.dao.model.User;
import com.cgi.poc.dw.service.EmailService;
import com.cgi.poc.dw.service.TextMessageService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.LoggerFactory;


@RunWith(MockitoJUnitRunner.class)
public class FireEventAPICallerServiceUnitTest {

  private FireEventAPICallerServiceImpl underTest;

  @Mock
  private FireEventDAO eventDAO;

  @Mock
  private TextMessageService textMessageService;

  @Mock
  private EmailService emailService;

  @Mock
  private UserDao userDao;

  @Mock
  private EventNotificationDAO eventNotificationDAO;

  @Mock
  protected SessionFactory sessionFactory;

  @Mock
  private Appender mockAppender;

  @Captor
  private ArgumentCaptor<LoggingEvent> captorLoggingEvent;

  @Before
  public void setup() throws IOException {

    JsonNode jsonRespone = new ObjectMapper().
        readTree(getClass().getResource("/exampleFireEvent.json"));

    Client client = mock(Client.class);
    //mocking the Jersey Client
    WebTarget mockWebTarget = mock(WebTarget.class);
    when(client.target(anyString())).thenReturn(mockWebTarget);
    when(mockWebTarget.queryParam(anyString(), anyString())).thenReturn(mockWebTarget);
    Invocation.Builder mockBuilder = mock(Invocation.Builder.class);
    when(mockWebTarget.request(anyString())).thenReturn(mockBuilder);
    when(mockBuilder.get(String.class)).thenReturn(jsonRespone.toString());

    String eventUrl = "http://events.com";
    underTest = new FireEventAPICallerServiceImpl(
        eventUrl, client, eventDAO, sessionFactory, textMessageService, emailService, userDao,
        eventNotificationDAO);

    final Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    logger.addAppender(mockAppender);
  }

  @After
  public void teardown() {
    final Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    logger.detachAppender(mockAppender);
  }

  @Test
  public void callServiceAPI_Failure() throws IOException {
    Client client = mock(Client.class);
    //mocking the Jersey Client
    WebTarget mockWebTarget = mock(WebTarget.class);
    when(client.target(anyString())).thenReturn(mockWebTarget);
    when(mockWebTarget.queryParam(anyString(), anyString())).thenReturn(mockWebTarget);
    Invocation.Builder mockBuilder = mock(Invocation.Builder.class);
    when(mockWebTarget.request(anyString())).thenReturn(mockBuilder);
    doThrow(new ProcessingException("Processing failed.")).when(mockBuilder).get(String.class);

    String eventUrl = "http://events.com";
    FireEventAPICallerServiceImpl underTest = new FireEventAPICallerServiceImpl(
        eventUrl, client, eventDAO, sessionFactory, textMessageService, emailService, userDao,
        eventNotificationDAO);

    underTest.callServiceAPI();

    //verify logging interactions
    verify(mockAppender, times(1)).doAppend(captorLoggingEvent.capture());
    final LoggingEvent loggingEvent = captorLoggingEvent.getValue();
    //Check log level is correct
    assertThat(loggingEvent.getLevel(), is(Level.ERROR));
    //Check the message being logged is correct
    assertThat(loggingEvent.getFormattedMessage().toLowerCase(),
        containsString("unable to parse the result for the url event : " + eventUrl));
  }

  @Test
  public void newEventWithNoAffectedUsers() throws IOException {
    Session session = mock(Session.class);
    when(sessionFactory.openSession()).thenReturn(session);

    Transaction transaction = mock(Transaction.class);
    when(session.beginTransaction()).thenReturn(transaction);

    FireEvent eventFromDB = mock(FireEvent.class);
    when(eventDAO.selectForUpdate(any(FireEvent.class))).thenReturn(eventFromDB);

    doThrow(new ObjectNotFoundException(eventFromDB, "record not found")).when(eventFromDB)
        .getLastModified();

    //return empty list to indicate no affected users
    when(userDao.getGeoWithinRadius(anyList(), anyDouble())).thenReturn(Collections.emptyList());

    underTest.callServiceAPI();

    verify(userDao, times(1)).getGeoWithinRadius(anyList(), anyDouble());
    //verify that the email notification was never called
    verify(emailService, never()).send(anyString(), anyList(), anyString(), anyString());
    //verify that the sms notification was never called
    verify(textMessageService, never()).send(anyString(), anyString());
  }

  @Test
  public void newEventWithAffectedUsers() throws IOException {
    Session session = mock(Session.class);
    when(sessionFactory.openSession()).thenReturn(session);

    Transaction transaction = mock(Transaction.class);
    when(session.beginTransaction()).thenReturn(transaction);

    FireEvent eventFromDB = mock(FireEvent.class);
    when(eventDAO.selectForUpdate(any(FireEvent.class))).thenReturn(eventFromDB);

    doThrow(new ObjectNotFoundException(eventFromDB, "record not found")).when(eventFromDB)
        .getLastModified();

    User user1 = createUser();
    user1.setEmailNotification(true);
    User user2 = createUser();
    user2.setEmailNotification(true);
    User user3 = createUser();
    user3.setSmsNotification(true);

    List<User> affectedUsers = Arrays.asList(user1, user2, user3);
    //return empty list to indicate no affected users
    when(userDao.getGeoWithinRadius(anyList(), anyDouble())).thenReturn(affectedUsers);

    underTest.callServiceAPI();

    verify(userDao, times(1)).getGeoWithinRadius(anyList(), anyDouble());
    //verify that the email notification was never called
    verify(emailService, times(2)).send(eq(null), anyList(), anyString(), anyString());
    //verify that the sms notification was never called
    verify(textMessageService, times(1)).send(anyString(), anyString());
  }

  @Test
  public void changedEventWithNoAffectedUsers() throws IOException {
    Session session = mock(Session.class);
    when(sessionFactory.openSession()).thenReturn(session);

    Transaction transaction = mock(Transaction.class);
    when(session.beginTransaction()).thenReturn(transaction);

    FireEvent eventFromDB = mock(FireEvent.class);
    when(eventDAO.selectForUpdate(any(FireEvent.class))).thenReturn(eventFromDB);
    when(eventFromDB.getLastModified()).thenReturn(new Date(2017, 2, 27));

    FireEvent retEvent = mock(FireEvent.class);
    when(eventDAO.save(any(FireEvent.class))).thenReturn(retEvent);
    when(retEvent.getLastModified()).thenReturn(new Date(2017, 2, 28));

    //return empty list to indicate no affected users
    when(userDao.getGeoWithinRadius(anyList(), anyDouble())).thenReturn(Collections.emptyList());

    underTest.callServiceAPI();

    verify(userDao, times(1)).getGeoWithinRadius(anyList(), anyDouble());
    //verify that the email notification was never called
    verify(emailService, never()).send(anyString(), anyList(), anyString(), anyString());
    //verify that the sms notification was never called
    verify(textMessageService, never()).send(anyString(), anyString());
  }

  @Test
  public void changedEventWithAffectedUsers() throws IOException {
    Session session = mock(Session.class);
    when(sessionFactory.openSession()).thenReturn(session);

    Transaction transaction = mock(Transaction.class);
    when(session.beginTransaction()).thenReturn(transaction);

    FireEvent eventFromDB = mock(FireEvent.class);
    when(eventDAO.selectForUpdate(any(FireEvent.class))).thenReturn(eventFromDB);
    when(eventFromDB.getLastModified()).thenReturn(new Date(2017, 2, 27));

    FireEvent retEvent = mock(FireEvent.class);
    when(eventDAO.save(any(FireEvent.class))).thenReturn(retEvent);
    when(retEvent.getLastModified()).thenReturn(new Date(2017, 2, 28));

    User user1 = createUser();
    user1.setEmailNotification(true);
    User user2 = createUser();
    user2.setEmailNotification(true);
    User user3 = createUser();
    user3.setSmsNotification(true);

    List<User> affectedUsers = Arrays.asList(user1, user2, user3);
    //return empty list to indicate no affected users
    when(userDao.getGeoWithinRadius(anyList(), anyDouble())).thenReturn(affectedUsers);

    underTest.callServiceAPI();

    verify(userDao, times(1)).getGeoWithinRadius(anyList(), anyDouble());
    //verify that the email notification was never called
    verify(emailService, times(2)).send(eq(null), anyList(), anyString(), anyString());
    //verify that the sms notification was never called
    verify(textMessageService, times(1)).send(anyString(), anyString());
  }

  @Test
  public void logsTheErrorWhenParsingUnexpectedJson() throws IOException {

    JsonNode jsonRespone = new ObjectMapper().
        readTree(getClass().getResource("/exampleFireEventInvalidData.json"));

    Session session = mock(Session.class);
    when(sessionFactory.openSession()).thenReturn(session);

    Client client = mock(Client.class);
    //mocking the Jersey Client
    WebTarget mockWebTarget = mock(WebTarget.class);
    when(client.target(anyString())).thenReturn(mockWebTarget);
    when(mockWebTarget.queryParam(anyString(), anyString())).thenReturn(mockWebTarget);
    Invocation.Builder mockBuilder = mock(Invocation.Builder.class);
    when(mockWebTarget.request(anyString())).thenReturn(mockBuilder);
    when(mockBuilder.get(String.class)).thenReturn(jsonRespone.toString());

    String eventUrl = "http://events.com";
    FireEventAPICallerServiceImpl underTest = new FireEventAPICallerServiceImpl(
        eventUrl, client, eventDAO, sessionFactory, textMessageService, emailService, userDao,
        eventNotificationDAO);

    underTest.callServiceAPI();

    //verify logging interactions
    verify(mockAppender, atLeast(1)).doAppend(captorLoggingEvent.capture());
    final LoggingEvent loggingEvent = captorLoggingEvent.getValue();
    //Check log level is correct
    assertThat(loggingEvent.getLevel(), is(Level.ERROR));
    //Check the message being logged is correct
    assertThat(loggingEvent.getFormattedMessage().toLowerCase(),
        containsString("unable to parse the result for the fire event : error:"));
  }

  private User createUser() {
    User user = new User();
    user.setEmail("success@gmail.com");
    user.setPassword("test123");
    user.setFirstName("john");
    user.setLastName("smith");
    user.setRole(Role.RESIDENT.name());
    user.setPhone("1234567890");
    user.setZipCode("98765");
    user.setCity("Sacramento");
    user.setState("CA");
    user.setAddress1("required street");
    user.setAddress2("optional street");
    user.setEmailNotification(false);
    user.setSmsNotification(false);
    user.setPushNotification(false);
    user.setLatitude(0.0);
    user.setLongitude(0.0);

    return user;
  }
}
