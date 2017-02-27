package com.cgi.poc.dw.dao.model;

public class UserDto {
  protected Long id;
  protected String firstName;
  protected String lastName;
  protected String email;
  protected String password;
  protected String phone;
  protected String zipCode;
  protected String role;
  protected Double latitude;
  protected Double longitude;
  protected String address1;
  protected String address2;
  protected String city;
  protected String state;
  protected Boolean emailNotification;
  protected Boolean pushNotification;
  protected Boolean smsNotification;

  public Long getId() {
      return id;
  }

  public void setId(Long id) {
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

  public String getRole() {
      return role;
  }

  public void setRole(String role) {
      this.role = role;
  }

  public Double getLatitude() {
      return latitude;
  }

  public void setLatitude(Double latitude) {
      this.latitude = latitude;
  }

  public Double getLongitude() {
      return longitude;
  }

  public void setLongitude(Double longitude) {
      this.longitude = longitude;
  }

  public String getAddress1() {
      return address1;
  }

  public void setAddress1(String address1) {
      this.address1 = address1;
  }

  public String getAddress2() {
      return address2;
  }

  public void setAddress2(String address2) {
      this.address2 = address2;
  }

  public String getCity() {
      return city;
  }

  public void setCity(String city) {
      this.city = city;
  }

  public String getState() {
      return state;
  }

  public void setState(String state) {
      this.state = state;
  }

  public Boolean getEmailNotification() {
      return emailNotification;
  }

  public void setEmailNotification(Boolean emailNotification) {
      this.emailNotification = emailNotification;
  }

  public Boolean getPushNotification() {
      return pushNotification;
  }

  public void setPushNotification(Boolean pushNotification) {
      this.pushNotification = pushNotification;
  }

  public Boolean getSmsNotification() {
      return smsNotification;
  }

  public void setSmsNotification(Boolean smsNotification) {
      this.smsNotification = smsNotification;
  }
}
