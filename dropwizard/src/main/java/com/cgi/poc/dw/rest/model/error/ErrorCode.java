package com.cgi.poc.dw.rest.model.error;

public enum ErrorCode {
  /*
   * we never return 500, instead 500 should be logged and 400 should be
   * returned
   */
  BAD_REQUEST(400), UNAUTHORIZED(401), NOT_FOUND(404), CONFLICT(409);
  private final int value;

  private ErrorCode(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
