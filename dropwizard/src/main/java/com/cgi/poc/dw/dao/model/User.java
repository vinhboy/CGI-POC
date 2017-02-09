package com.cgi.poc.dw.dao.model;

import com.cgi.poc.dw.auth.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.security.Principal;

public class User implements Principal {

  private String email;
  private String hash;
  private long id;
  private Role role;

  public User() {
  }

  public User(String email, String hash, Role role) {
    this.email = email;
    this.hash = hash;
    this.role = role;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getHash() {
    return hash;
  }

  public void setHash(String hash) {
    this.hash = hash;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }
}
