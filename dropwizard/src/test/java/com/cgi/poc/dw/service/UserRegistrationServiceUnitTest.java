package com.cgi.poc.dw.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.cgi.poc.dw.auth.model.Role;
import com.cgi.poc.dw.auth.service.PasswordHash;
import com.cgi.poc.dw.dao.UserDao;
import com.cgi.poc.dw.dao.model.NotificationType;
import com.cgi.poc.dw.dao.model.User;
import com.cgi.poc.dw.dao.model.UserNotification;
import com.cgi.poc.dw.util.ErrorInfo;
import java.security.spec.InvalidKeySpecException;
import java.util.HashSet;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.ws.rs.WebApplicationException;
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
public class UserRegistrationServiceUnitTest {

  @InjectMocks
  UserRegistrationServiceImpl underTest;

  @Mock
  private UserDao userDao;

  @Mock
  private PasswordHash passwordHash;

  @Spy
  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  private User user;

  @Before
  public void createUser() {
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
    UserNotification selNot = new UserNotification(Long.valueOf(NotificationType.SMS.ordinal()));
    Set<UserNotification> notificationType = new HashSet<>();
    notificationType.add(selNot);
    user.setNotificationType(notificationType);
  }

  @Test
  public void registerUser_shouldRegisterUserWhenGivenValidInput() throws Exception {

    User newUser = null;
    String saltedHash = "518bd5283161f69a6278981ad00f4b09a2603085f145426ba8800c:"
        + "8bd85a69ed2cb94f4b9694d67e3009909467769c56094fc0fce5af";
    when(passwordHash.createHash(user.getPassword())).thenReturn(saltedHash);
    when(userDao.save(any(User.class))).thenReturn(newUser);
    Response actual = underTest.registerUser(user);

    assertEquals(200, actual.getStatus());
  }


  @Test
  public void registerUser_shouldThrowAnExceptionWhenEmailValidationFails() throws Exception {

    user.setPassword("a"); //one character password

    try {
      underTest.registerUser(user);
      fail("Expected an exception to be thrown");
    } catch (ConstraintViolationException exception) {
      Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
      for (ConstraintViolation violation : constraintViolations) {
        String tmp = ((PathImpl) violation.getPropertyPath()).getLeafNode().getName();
        String annotation = violation.getConstraintDescriptor().getAnnotation().annotationType()
            .getCanonicalName();

        if (tmp.equals("password") && annotation.equals("javax.validation.constraints.Size")) {
          assertThat(violation.getMessageTemplate())
              .isEqualTo("must be at least 2 characters in length.");
        } else if (tmp.equals("password") && annotation
            .equals("com.cgi.poc.dw.util.PasswordType")) {
          assertThat(violation.getMessageTemplate())
              .isEqualTo(
                  "must be greater that 2 character, contain no whitespace, and have at least one number and one letter.");
        } else {
          fail("not an expected constraint violation");
        }
      }
    }
  }

  @Test
  public void registerUser_shouldThrowExceptionWhenUserAlreadyExists() throws Exception {

    String saltedHash = "518bd5283161f69a6278981ad00f4b09a2603085f145426ba8800c:"
        + "8bd85a69ed2cb94f4b9694d67e3009909467769c56094fc0fce5af";
    when(passwordHash.createHash(user.getPassword())).thenReturn(saltedHash);

    when(userDao.findUserByEmail(user.getEmail())).thenReturn(user);
    try {
      underTest.registerUser(user);
      fail("Expected an exception to be thrown");
    } catch (WebApplicationException exception) {
      assertEquals(400, exception.getResponse().getStatus());
      ErrorInfo errorInfo = (ErrorInfo) exception.getResponse().getEntity();
      String actualMessage = errorInfo.getErrors().get(0).getMessage();
      String actualCode = errorInfo.getErrors().get(0).getCode();

      assertEquals("ERR4", actualCode);
      assertEquals("Duplicate entry for key '<email>'", actualMessage);
    }
  }

  @Test
  public void registerUser_shouldThrowExceptionWhenCreateUserFails() throws Exception {

    String saltedHash = "518bd5283161f69a6278981ad00f4b09a2603085f145426ba8800c:"
        + "8bd85a69ed2cb94f4b9694d67e3009909467769c56094fc0fce5af";
    when(passwordHash.createHash(user.getPassword())).thenReturn(saltedHash);

    doThrow(new HibernateException("Something went wrong.")).when(userDao).save(any(User.class));
    try {
      underTest.registerUser(user);
      fail("Expected an exception to be thrown");
    } catch (WebApplicationException exception) {
      assertEquals(500, exception.getResponse().getStatus());
      ErrorInfo errorInfo = (ErrorInfo) exception.getResponse().getEntity();
      String actualMessage = errorInfo.getErrors().get(0).getMessage();
      String actualCode = errorInfo.getErrors().get(0).getCode();

      assertEquals("ERR1", actualCode);
      assertEquals(
          "An Unknown exception has occured. Type: <org.hibernate.HibernateException>. Message: <Something went wrong.>",
          actualMessage);
    }
  }

  @Test
  public void registerUser_shouldThrowExceptionWhenPasswordHashingFails() throws Exception {

    doThrow(new InvalidKeySpecException("Something went wrong.")).when(passwordHash).createHash(user.getPassword());
    try {
      underTest.registerUser(user);
      fail("Expected ConflictException");
    } catch (WebApplicationException exception) {
      assertEquals(500, exception.getResponse().getStatus());
      ErrorInfo errorInfo = (ErrorInfo) exception.getResponse().getEntity();
      String actualMessage = errorInfo.getErrors().get(0).getMessage();
      String actualCode = errorInfo.getErrors().get(0).getCode();

      assertEquals("ERR1", actualCode);
      assertEquals(
          "An Unknown exception has occured. Type: <java.security.spec.InvalidKeySpecException>. Message: <Something went wrong.>",
          actualMessage);
    }
  }

}
