package com.cgi.poc.dw.exception.mapper;

import com.cgi.poc.dw.exception.ErrorInfo;
import com.cgi.poc.dw.exception.GeneralErrors;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {
  private final static Logger LOG = LoggerFactory.getLogger(RuntimeExceptionMapper.class);

  @Override
  public Response toResponse(RuntimeException runtime) {
    ErrorInfo errRet = new ErrorInfo();
    LOG.error("Exception intercepted", runtime);
    // Build default response
    Response defaultResponse = Response
        .serverError()
        .entity(errRet)
        .build();
    // Check for any specific handling
    if (runtime instanceof WebApplicationException) {
      errRet.addError("UNKNOWN ERROR", runtime.getMessage());
      return handleWebApplicationException(runtime, defaultResponse);
    }else if (runtime instanceof org.hibernate.exception.ConstraintViolationException){
      org.hibernate.exception.ConstraintViolationException tmp = (org.hibernate.exception.ConstraintViolationException)runtime;
      String errorString = GeneralErrors.CONSTRAINT_VIOLATION.getMessage().replace("REPLACE",  tmp.getConstraintName());
      errRet.addError(GeneralErrors.CONSTRAINT_VIOLATION.getCode(), errorString + ": " + tmp.getMessage());
    }else  if (runtime instanceof javax.validation.ConstraintViolationException){
      javax.validation.ConstraintViolationException tmp = (javax.validation.ConstraintViolationException)runtime;
      tmp.getConstraintViolations().forEach(p -> {
        errRet.addError(GeneralErrors.CONSTRAINT_VIOLATION.getCode(), p.getInvalidValue().toString() +" " +  p.getMessage());
      });
    }
    else
    {
      // default
      String errorString = GeneralErrors.UNKNOWN_EXCEPTION.getMessage().replace("REPLACE2", runtime.getMessage());
      errorString = errorString.replace("REPLACE1", runtime.getClass().getName());
      errRet.addError(GeneralErrors.UNKNOWN_EXCEPTION.getCode(), errorString);
    }
    // Use the default
    LOG.error(runtime.getMessage());
    return defaultResponse;
  }
  private Response handleWebApplicationException(RuntimeException exception, Response defaultResponse) {
    WebApplicationException webAppException = (WebApplicationException) exception;
    ErrorInfo errRet = new ErrorInfo();
    String errorString = GeneralErrors.UNKNOWN_EXCEPTION.getMessage().replace("REPLACE2", exception.getMessage());
    errorString = errorString.replace("REPLACE1", exception.getClass().getName());
    errRet.addError(GeneralErrors.UNKNOWN_EXCEPTION.getCode(), errorString);

    // No logging
    if (webAppException.getResponse().getStatus() == 401) {
      return Response
          .status(Response.Status.UNAUTHORIZED)
          .entity(errRet)
          .build();
    }
    if (webAppException.getResponse().getStatus() == 404) {
      return Response
          .status(Response.Status.NOT_FOUND)
          .entity(errRet)
          .build();
    }

    LOG.error(exception.getMessage());
    return defaultResponse;
  }
}
