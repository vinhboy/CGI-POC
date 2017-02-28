package com.cgi.poc.dw.exception.mapper;

import com.cgi.poc.dw.exception.ErrorInfo;
import com.cgi.poc.dw.exception.GeneralErrors;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class InternalServerExceptionMapper implements ExceptionMapper<InternalServerErrorException> {

  private final static Logger LOG = LoggerFactory.getLogger(InternalServerExceptionMapper.class);

  @Override
  public Response toResponse(InternalServerErrorException exception) {
    ErrorInfo errRet = new ErrorInfo();
    String errorString = GeneralErrors.UNKNOWN_EXCEPTION.getMessage().replace("REPLACE2", exception.getMessage());
    errorString = errorString.replace("REPLACE1", exception.getClass().getName());
    errRet.addError(GeneralErrors.UNKNOWN_EXCEPTION.getCode(), errorString);
    Response response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(errRet).build();
    LOG.error("Exception: ", exception);

    return response;
  }
}
