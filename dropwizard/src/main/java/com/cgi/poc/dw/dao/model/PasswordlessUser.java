/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgi.poc.dw.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

public class PasswordlessUser extends User {

  public PasswordlessUser() {
  }

  public PasswordlessUser(Long id) {
    super(id);
  }

  public PasswordlessUser(Long id, String firstName, String lastName, String email,
                          String phone, String zipCode, String role, double latitude, double longitude, String address1,
                          String address2, String city, String state, Boolean emailNotification, Boolean pushNotification, Boolean smsNotification) {
    super(id, firstName, lastName, email, null, phone, zipCode, role, latitude, longitude, address1, address2, city, state, emailNotification, pushNotification, smsNotification);
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

  public void copyFrom(User user) throws InvocationTargetException, IllegalAccessException {
    BeanUtils.copyProperties(this, user);
  }
}
