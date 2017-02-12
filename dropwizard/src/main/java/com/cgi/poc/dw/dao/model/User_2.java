package com.cgi.poc.dw.dao.model;

import com.cgi.poc.dw.auth.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.security.Principal;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class User_2 implements Principal {

  private long id;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  @NotNull
  @Size(min = 10, max = 10)
  private String phone;
  private String zipCode;
  private Role role;
  private List<NotificationType> notificationType;
  private double latitude;
  private double longitude;

  public User_2() {
  }
  
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public List<NotificationType> getNotificationType() {
    return notificationType;
  }

  public void setNotificationType(
      List<NotificationType> notificationType) {
    this.notificationType = notificationType;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  /*
     * dropwizard requires to implement principal for authentication which
     * implements getName()
     */
  @JsonIgnore
  @Override
  public String getName() {
    return email;
  }
}
