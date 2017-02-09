package com.cgi.poc.dw.rest.model.error;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class ErrorMessageWebException extends WebApplicationException {

  private static final long serialVersionUID = 1L;

  public ErrorMessageWebException(ErrorMessage errorMessage) {
    super(Response.status(errorMessage.getValue()).entity(errorMessage.toString()).build());
  }

  public ErrorMessageWebException(int statusCode, String msg) {
    super(Response.status(statusCode).entity(msg).build());
  }
}
