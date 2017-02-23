package com.cgi.poc.dw.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cgi.poc.dw.api.service.MapsApiService;
import com.cgi.poc.dw.api.service.data.GeoCoordinates;
import com.cgi.poc.dw.auth.model.Role;
import com.cgi.poc.dw.dao.EventNotificationDAO;
import com.cgi.poc.dw.dao.UserDao;
import com.cgi.poc.dw.dao.model.EventNotification;
import com.cgi.poc.dw.dao.model.EventNotificationZipcode;
import com.cgi.poc.dw.dao.model.NotificationType;
import com.cgi.poc.dw.dao.model.User;
import com.cgi.poc.dw.dao.model.UserNotificationType;
import com.cgi.poc.dw.rest.model.EventNotificationDto;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.ws.rs.core.Response;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EventNotificationServiceUnitTest {

  @Mock
  private UserDao userDao;

  @Mock
  private EventNotificationDAO eventNotificationDAO;

  @Mock
  private EmailService emailService;

  @Mock
  private TextMessageService textMessageService;

  @Mock
  private MapsApiService mapsApiService;

  @Spy
  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @InjectMocks
  private EventNotificationServiceImpl underTest;

  private User user;

  private EventNotificationDto eventNotificationDto;

  private EventNotification eventNotification;

  @Before
  public void createEventNotification() throws IOException {
    user = new User();
    user.setEmail("success@gmail.com");
    user.setPassword("test123");
    user.setFirstName("john");
    user.setLastName("smith");
    user.setRole(Role.RESIDENT.name());
    user.setPhone("1234567890");
    user.setZipCode("98765");
    user.setLatitude(0.0);
    user.setLongitude(0.0);
    UserNotificationType selNot = new UserNotificationType(
        Long.valueOf(NotificationType.SMS.ordinal()));
    Set<UserNotificationType> notificationType = new HashSet<>();
    notificationType.add(selNot);
    user.setNotificationType(notificationType);

    Set<EventNotificationZipcode> eventNotificationZipcodes = new LinkedHashSet<>();
    EventNotificationZipcode eventNotificationZipcode1 = new EventNotificationZipcode();
    eventNotificationZipcode1.setZipCode("92105");
    EventNotificationZipcode eventNotificationZipcode2 = new EventNotificationZipcode();
    eventNotificationZipcode2.setZipCode("92106");
    eventNotificationZipcodes.add(eventNotificationZipcode1);
    eventNotificationZipcodes.add(eventNotificationZipcode2);

    eventNotification = new EventNotification();
    eventNotification.setType("ADMIN_E");
    eventNotification.setDescription("some description");
    eventNotification.setEventNotificationZipcodes(eventNotificationZipcodes);
    eventNotification.setUserId(user);

    eventNotificationDto = new EventNotificationDto();
    eventNotificationDto.setType("ADMIN_E");
    eventNotificationDto.setDescription("some description");
    eventNotificationDto.setZipCodes(Sets.newHashSet("92105", "92106"));
    
    doNothing().when(emailService).send(anyString(), anyList(), anyString(), anyString());
    when(textMessageService.send(anyString(), anyString())).thenReturn(true);
  }


  @Test
  public void publishNotification_publishesNotificationWithValidInput() {

    List<User> affectedUsers = new ArrayList<>();
    affectedUsers.add(user);

    GeoCoordinates geoCoordinates = new GeoCoordinates();
    geoCoordinates.setLongitude(10.00);
    geoCoordinates.setLongitude(20.00);
    
    Double radius = 50.00;
    
    when(userDao.getGeoWithinRadius(anyList(), eq(radius))).thenReturn(affectedUsers);
    when(eventNotificationDAO.save(eq(eventNotification))).thenReturn(eventNotification);
    when(mapsApiService.getGeoCoordinatesByZipCode(anyString())).thenReturn(geoCoordinates);
    Response actual = underTest.publishNotification(user, eventNotificationDto);

    assertEquals(200, actual.getStatus());
    assertEquals(eventNotification, actual.getEntity());
    //verify that the email notification was never called
    verify(emailService, never()).send(anyString(), anyList(), anyString(), anyString());
    //verify that the sms notification was called once
    verify(textMessageService, times(1)).send(anyString(), anyString());
  }

  @Test
  public void publishNotification_InvalidZipCode() {

    eventNotificationDto.getZipCodes().add("987");
    try {
      Response actual = underTest.publishNotification(user, eventNotificationDto);
      fail("Expected an exception to be thrown");

    } catch (ConstraintViolationException exception) {
      Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
      for (ConstraintViolation violation : constraintViolations) {
        String tmp = ((PathImpl) violation.getPropertyPath()).getLeafNode().getName();
        String annotation = violation.getConstraintDescriptor().getAnnotation().annotationType()
            .getCanonicalName();

        if (tmp.equals("zipCode") && annotation.equals("javax.validation.constraints.Pattern")) {
          assertThat(violation.getMessageTemplate())
              .isEqualTo("is invalid.");
        } else {
          fail("not an expected constraint violation");
        }
      }
    }
  }
 
  @Test
  public void publishNotification_InvalidDescription() {

    eventNotificationDto.setDescription("abc");
    try {
      Response actual = underTest.publishNotification(user, eventNotificationDto);
      fail("Expected an exception to be thrown");

    } catch (ConstraintViolationException exception) {
      Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
      for (ConstraintViolation violation : constraintViolations) {
        String tmp = ((PathImpl) violation.getPropertyPath()).getLeafNode().getName();
        String annotation = violation.getConstraintDescriptor().getAnnotation().annotationType()
            .getCanonicalName();

        if (tmp.equals("description") && annotation.equals("javax.validation.constraints.Size")) {
          assertThat(violation.getMessage())
              .isEqualTo("size must be between 5 and 2048");
        } else {
          fail("not an expected constraint violation");
        }
      }
    }
  }

    @Test
    public void retrievehNotificationNoEntries() throws Exception {
        Response response = underTest.retrieveAllNotifications(user);
        assertEquals(200, response.getStatus());
        List<EventNotification> list = (List<EventNotification> ) response.getEntity( );
        String count = response.getHeaderString("x-total-count");
        int rows = Integer.decode(count);
        assertThat(rows).isEqualTo(0);
        assertThat(list.size()).isEqualTo(0);
    }
    @Test
    public void retrievehNotificationEntries() throws Exception {
        List<EventNotification> resultList = new ArrayList<>();
        resultList.add(eventNotification);
        
        when(eventNotificationDAO.retrieveAll()).thenReturn(resultList);

        Response response = underTest.retrieveAllNotifications(user);
        assertEquals(200, response.getStatus());
        List<EventNotification> list = (List<EventNotification> ) response.getEntity( );
        String count = response.getHeaderString("x-total-count");
        int rows = Integer.decode(count);
        assertThat(rows).isEqualTo(1);
        assertThat(list.size()).isEqualTo(1);
    }

}
