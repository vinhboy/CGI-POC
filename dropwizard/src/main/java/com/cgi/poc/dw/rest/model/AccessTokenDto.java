package com.cgi.poc.dw.rest.model;


import com.cgi.poc.dw.auth.model.Role;
import com.cgi.poc.dw.dao.model.User;

public class AccessTokenDto {

  private String authToken;
  private Role role = Role.RESIDENT;
  private User user;

  public AccessTokenDto() {
  }

  public AccessTokenDto(String authToken, Role role, User user) {
    this.authToken = authToken;
    this.role = role;
    this.user = user;
  }

  public String getAuthToken() {
    return authToken;
  }

  public void setAuthToken(String authToken) {
    this.authToken = authToken;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public User getUser() {
	return user;
  }

  public void setUser(User user) {
	this.user = user;
  }
}
