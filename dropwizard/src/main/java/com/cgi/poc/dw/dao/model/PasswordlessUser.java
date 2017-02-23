package com.cgi.poc.dw.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

public class PasswordlessUser extends User {

  public PasswordlessUser() {
  }

  @Override
  @JsonIgnore
  public String getPassword() {
    return null;
  }

  @Override
  @JsonIgnore
  public void setPassword(String password) {
  }

  public PasswordlessUser copyFrom(User user) throws InvocationTargetException, IllegalAccessException {
    BeanUtils.copyProperties(this, user);
    return this;
  }
}
