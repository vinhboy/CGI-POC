package com.cgi.poc.dw.rest.model.error;

public enum ErrorMessage {
  LOGIN_FAIL_NO_CREDENTIALS(ErrorCode.BAD_REQUEST.getValue()), //
  LOGIN_FAIL_NO_EMAIL(ErrorCode.BAD_REQUEST.getValue()), //
  LOGIN_FAIL_NO_PASSWORD(ErrorCode.BAD_REQUEST.getValue()), //
  LOGIN_FAIL_USER_NOT_FOUND_OR_WRONG_PASSWORD(ErrorCode.UNAUTHORIZED.getValue());
  private final int value;

  private ErrorMessage(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
