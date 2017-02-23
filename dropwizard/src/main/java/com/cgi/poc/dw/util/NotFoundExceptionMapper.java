package com.cgi.poc.dw.util;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

  private final static Logger LOG = LoggerFactory.getLogger(NotFoundExceptionMapper.class);

  @Override
  public Response toResponse(NotFoundException exception) {
    ErrorInfo errRet = new ErrorInfo();
    String errorString = GeneralErrors.NOT_FOUND.getMessage().replace("REPLACE", exception.getMessage());
    errRet.addError(GeneralErrors.NOT_FOUND.getCode(), errorString);
    Response response = Response.status(Status.NOT_FOUND).entity(errRet).build();
    LOG.error("Exception: ", exception);
    return response;
  }
}
