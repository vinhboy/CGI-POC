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
    String errorString = GeneralErrors.INVALID_INPUT.getMessage().replace("REPLACE", exception.getMessage());
    errRet.addError(GeneralErrors.INVALID_INPUT.getCode(), errorString);
    Response response = Response.status(Status.BAD_REQUEST).entity(errRet).build();
    LOG.error("Exception: ", exception);
    return response;
  }
}
