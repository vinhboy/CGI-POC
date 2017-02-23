package com.cgi.poc.dw.dao;

import com.cgi.poc.dw.dao.model.PasswordlessUser;
import com.cgi.poc.dw.dao.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

public class PasswordlessUserTest {
  private User source;

  @Before
  public void beforeEach() {
    source = new User(1L, "firstName", "lastName", "email", "password", "phone", "zipCode", "role", 0, 0, "address1", "address2", "city", "state");
  }

  @Test
  public void copiesAllButPasswordBeansFromUser() throws InvocationTargetException, IllegalAccessException {
    PasswordlessUser passwordless = new PasswordlessUser();
    passwordless.copyFrom(source);
    assertEquals(source.getId(), passwordless.getId());
    assertEquals(source.getFirstName(), passwordless.getFirstName());
    assertEquals(source.getLastName(), passwordless.getLastName());
    assertEquals(source.getEmail(), passwordless.getEmail());
    assertEquals(source.getPhone(), passwordless.getPhone());
    assertEquals(source.getZipCode(), passwordless.getZipCode());
    assertEquals(source.getRole(), passwordless.getRole());
    assertEquals(source.getLatitude(), passwordless.getLatitude());
    assertEquals(source.getLongitude(), passwordless.getLongitude());
    assertEquals(source.getAddress1(), passwordless.getAddress1());
    assertEquals(source.getAddress2(), passwordless.getAddress2());
    assertEquals(source.getCity(), passwordless.getCity());
    assertEquals(source.getState(), passwordless.getState());
  }

  @Test
  public void doesNotCopyPasswordBean() throws InvocationTargetException, IllegalAccessException {
    PasswordlessUser passwordless = new PasswordlessUser();
    passwordless.copyFrom(source);
    assertEquals(null, passwordless.getPassword());
  }
}
