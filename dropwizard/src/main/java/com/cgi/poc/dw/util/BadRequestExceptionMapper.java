package com.cgi.poc.dw.util;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {

  private final static Logger LOG = LoggerFactory.getLogger(BadRequestExceptionMapper.class);

  @Override
  public Response toResponse(BadRequestException exception) {
    ErrorInfo errRet = new ErrorInfo();
    String message = exception.getMessage();
    errRet.addError(Integer.toString(Response.Status.BAD_REQUEST.getStatusCode()), message);
    Response response = Response.status(Status.BAD_REQUEST).entity(errRet).build();
    LOG.error("Exception: ", exception);
    return response;
  }
}
