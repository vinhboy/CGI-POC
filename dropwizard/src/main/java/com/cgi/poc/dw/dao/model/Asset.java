package com.cgi.poc.dw.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Asset implements Serializable {

  private static final long serialVersionUID = 1L;

  private long id;

  @NotNull
  @Size(min = 1, max = 255)
  private String url;

  @Size(max = 2048)
  private String description;

  /**
   * The owner of the asset.
   */
  @JsonIgnore
  private User user;

  public Asset() {
  }

  public Asset(String url, String description) {
    this.url = url;
    this.description = description;
  }

  public Asset(String url, String description, User user) {
    this.url = url;
    this.description = description;
    this.user = user;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  //Required for the JDBI BindBean
  public long getUserId() {
    return this.user.getId();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id,
        this.url,
        this.description,
        this.user);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Asset other = (Asset) obj;
    return Objects.equals(this.user, other.user)
        && Objects.equals(this.url, other.url)
        && Objects.equals(this.description, other.description)
        && Objects.equals(this.id, other.id);
  }

  @Override
  public String toString() {
    return "Asset{" + "id=" + id + ", url=" + url
        + ", description=" + description
        + ", user=" + Objects.toString(user) + '}';
  }

}
