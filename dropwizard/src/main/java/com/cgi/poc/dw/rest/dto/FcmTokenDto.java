package com.cgi.poc.dw.rest.dto;

import com.cgi.poc.dw.validator.ValidationErrors;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class FcmTokenDto {

  @NotNull
  @Size(message = ValidationErrors.INVALID_FCM_TOKEN, max = 500)
  private String fcmtoken;

  public String getFcmtoken() {
    return fcmtoken;
  }

  public void setFcmtoken(String fcmtoken) {
    this.fcmtoken = fcmtoken;
  }
}
