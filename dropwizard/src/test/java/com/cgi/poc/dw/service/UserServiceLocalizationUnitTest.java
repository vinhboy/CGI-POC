package com.cgi.poc.dw.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.HashSet;
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

import com.cgi.poc.dw.auth.model.Role;
import com.cgi.poc.dw.dao.UserDao;
import com.cgi.poc.dw.dao.model.NotificationType;
import com.cgi.poc.dw.dao.model.User;
import com.cgi.poc.dw.dao.model.UserNotificationType;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceLocalizationUnitTest {

	@InjectMocks
	UserServiceImpl underTest;

	@Mock
	private UserDao userDao;

	@Spy
	private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	private User user;

	@Before
	public void createUser() throws IOException {
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
		UserNotificationType selNot = new UserNotificationType(Long.valueOf(NotificationType.SMS.ordinal()));
		Set<UserNotificationType> notificationType = new HashSet<>();
		notificationType.add(selNot);
		user.setNotificationType(notificationType);
	}

	@Test
	public void localizerUser_Success() throws Exception {

		user.setGeoLocLatitude(38.5824933);
		user.setGeoLocLongitude(-121.4941738);

		Response actual = underTest.setLocalization(user);

		assertEquals(200, actual.getStatus());
	}
	
	@Test
	public void localizerUser_NOGeoLocalizationValues() throws Exception {

		user.setGeoLocLatitude(null);
		user.setGeoLocLongitude(null);

	    try {
	        underTest.setLocalization(user);
	        fail("Expected an exception to be thrown");
	      } catch (ConstraintViolationException exception) {
	        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
	        for (ConstraintViolation violation : constraintViolations) {
	          String tmp = ((PathImpl) violation.getPropertyPath()).getLeafNode().getName();
	          String annotation = violation.getConstraintDescriptor().getAnnotation().annotationType()
	              .getCanonicalName();

	          if (tmp.equals("geoLocLongitude") && annotation.equals("javax.validation.constraints.NotNull")) {
	            assertThat(violation.getMessageTemplate())
	                .isEqualTo("{javax.validation.constraints.NotNull.message}");
	          } else if (tmp.equals("geoLocLatitude") && annotation
	              .equals("javax.validation.constraints.NotNull")) {
	            assertThat(violation.getMessageTemplate())
	                .isEqualTo("{javax.validation.constraints.NotNull.message}");
	          } else {
	            fail("not an expected constraint violation");
	          }
	        }
	      }
	}

}
