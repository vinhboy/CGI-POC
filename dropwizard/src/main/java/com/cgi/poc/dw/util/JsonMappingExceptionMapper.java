package com.cgi.poc.dw.util;

import com.fasterxml.jackson.databind.JsonMappingException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class JsonMappingExceptionMapper implements ExceptionMapper<JsonMappingException> {

  private final static Logger LOG = LoggerFactory.getLogger(JsonMappingExceptionMapper.class);

  @Override
  public Response toResponse(JsonMappingException exception) {
    ErrorInfo errRet = new ErrorInfo();
    String errorString = GeneralErrors.INVALID_INPUT.getMessage().replace("REPLACE", exception.getMessage());
    errRet.addError(GeneralErrors.INVALID_INPUT.getCode(), errorString);
    Response response = Response.status(Response.Status.BAD_REQUEST).entity(errRet).build();
    LOG.error("Exception: ", exception);

    return response;
  }
}
