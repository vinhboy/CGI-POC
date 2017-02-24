package com.cgi.poc.dw.rest.model;

import com.cgi.poc.dw.validator.StringListPattern;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EventNotificationDto {

  @Basic(optional = false)
  @NotNull
  @Size(min = 5, max = 2048)
  private String description;

  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 10)
  @Column(name = "type")
  private String type;
  
  @StringListPattern(regexp = "\\d{5}", message = "invalid zipcode found.")
  private Set<String> zipCodes;


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Set<String> getZipCodes() {
    return zipCodes;
  }

  public void setZipCodes(Set<String> zipCodes) {
    this.zipCodes = zipCodes;
  }
}
