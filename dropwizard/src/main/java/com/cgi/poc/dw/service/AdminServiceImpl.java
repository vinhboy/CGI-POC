package com.cgi.poc.dw.service;

import com.cgi.poc.dw.dao.AdminDAO;
import com.cgi.poc.dw.dao.model.EventNotification;
import com.cgi.poc.dw.dao.model.EventNotificationZipcode;
import com.cgi.poc.dw.dao.model.User;
import com.cgi.poc.dw.util.ErrorInfo;
import com.cgi.poc.dw.util.GeneralErrors;
import com.google.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.groups.Default;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdminServiceImpl extends BaseServiceImpl implements AdminService {

  private final static Logger LOG = LoggerFactory.getLogger(AdminServiceImpl.class);

  private AdminDAO adminDAO;

  @Inject
  public AdminServiceImpl(AdminDAO adminDAO, Validator validator) {
    super(validator);
    this.adminDAO = adminDAO;
  }

  @Override
  public Response publishNotification(User user, EventNotification eventNotification) {

    try {
      eventNotification.setUserId(user);
      for (EventNotificationZipcode eventNotificationZipcode : eventNotification
          .getEventNotificationZipcodes()) {
        eventNotificationZipcode.setEventNotificationId(eventNotification);
      }
      validate(eventNotification, "eventNotification validation", Default.class);
      eventNotification = adminDAO.save(eventNotification);
    } catch (ConstraintViolationException exception) {
      throw exception;
    } catch (Exception exception) {
      LOG.error("Unable to publish a notification for user {}.", user.getEmail(), exception);
      ErrorInfo errRet = getInternalErrorInfo(exception, GeneralErrors.UNKNOWN_EXCEPTION);
      return Response.noContent().status(Status.INTERNAL_SERVER_ERROR).entity(errRet).build();
    }
    return Response.ok().entity(eventNotification).build();
  }

  private ErrorInfo getInternalErrorInfo(Exception exception, GeneralErrors generalErrors) {
    ErrorInfo errRet = new ErrorInfo();
    String message = generalErrors.getMessage();
    String errorString = message.replace("REPLACE1", exception.getClass().getCanonicalName())
        .replace("REPLACE2", exception.getMessage());
    errRet.addError(generalErrors.getCode(), errorString);
    return errRet;
  }
}
