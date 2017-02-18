package com.cgi.poc.dw.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.cgi.poc.dw.auth.model.Role;
import com.cgi.poc.dw.dao.AdminDAO;
import com.cgi.poc.dw.dao.model.EventNotification;
import com.cgi.poc.dw.dao.model.EventNotificationZipcode;
import com.cgi.poc.dw.dao.model.NotificationType;
import com.cgi.poc.dw.dao.model.User;
import com.cgi.poc.dw.dao.model.UserNotificationType;
import com.cgi.poc.dw.util.ErrorInfo;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.ws.rs.core.Response;
import org.hibernate.HibernateException;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AdminServiceUnitTest {

  @Mock
  private AdminDAO adminDAO;

  @Spy
  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @InjectMocks
  private AdminServiceImpl underTest;

  private User user;

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
    eventNotification.setIsEmergency("y");
    eventNotification.setDescription("some description");
    eventNotification.setEventNotificationZipcodes(eventNotificationZipcodes);
  }


  @Test
  public void publishNotification_publishesNotificationWithValidInput() {

    when(adminDAO.save(eq(eventNotification))).thenReturn(eventNotification);
    Response actual = underTest.publishNotification(user, eventNotification);

    assertEquals(200, actual.getStatus());
    assertEquals(eventNotification, actual.getEntity());
  }

  @Test
  public void publishNotification_InvalidZipCode() {

    eventNotification.getEventNotificationZipcodes().iterator().next().setZipCode("998");
    try {
      underTest.publishNotification(user, eventNotification);
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
  public void publishNotification_InvalidIsEmergencyFlag() {

    eventNotification.setIsEmergency("Yes");
    try {
      underTest.publishNotification(user, eventNotification);
      fail("Expected an exception to be thrown");

    } catch (ConstraintViolationException exception) {
      Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
      for (ConstraintViolation violation : constraintViolations) {
        String tmp = ((PathImpl) violation.getPropertyPath()).getLeafNode().getName();
        String annotation = violation.getConstraintDescriptor().getAnnotation().annotationType()
            .getCanonicalName();

        if (tmp.equals("isEmergency") && annotation.equals("javax.validation.constraints.Pattern")) {
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

    eventNotification.setDescription("abc");
    try {
      underTest.publishNotification(user, eventNotification);
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
  public void publishNotification_CreateNotificationFails() throws Exception {

    doThrow(new HibernateException("Something went wrong.")).when(adminDAO)
        .save(any(EventNotification.class));
    Response registerUser = underTest.publishNotification(user, eventNotification);

    assertEquals(500, registerUser.getStatus());
    ErrorInfo errorInfo = (ErrorInfo) registerUser.getEntity();
    String actualMessage = errorInfo.getErrors().get(0).getMessage();
    String actualCode = errorInfo.getErrors().get(0).getCode();

    assertEquals("ERR1", actualCode);
    assertEquals(
        "An Unknown exception has occured. Type: <org.hibernate.HibernateException>. Message: <Something went wrong.>",
        actualMessage);
  }


}
