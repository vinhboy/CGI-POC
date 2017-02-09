package com.cgi.poc.dw.rest.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AssetDto {

  @NotNull
  @Size(min = 1, max = 255)
  private String url;

  @Size(max = 2048)
  private String description;

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
}
