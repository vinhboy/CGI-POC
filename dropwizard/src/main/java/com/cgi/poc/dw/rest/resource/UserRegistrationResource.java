package com.cgi.poc.dw.rest.resource;

import com.cgi.poc.dw.auth.service.PasswordHash;
import com.cgi.poc.dw.dao.UserDao;
import com.cgi.poc.dw.dao.model.NotificationType;
import com.cgi.poc.dw.dao.model.User;
import com.cgi.poc.dw.service.UserRegistrationServiceImpl;
import com.cgi.poc.dw.util.PersistValidationGroup;
import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cgi.poc.dw.dao.UserNotificationDao2;
import com.cgi.poc.dw.dao.model.UserNotification;
import com.cgi.poc.dw.util.ErrorInfo;
import com.cgi.poc.dw.util.GeneralErrors;
import com.cgi.poc.dw.util.RestValidationGroup;

@Path("/register")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api(value = "/register", basePath = "/")
public class UserRegistrationResource extends BaseResource{

  private final static Logger LOG = LoggerFactory.getLogger(UserRegistrationServiceImpl.class);
  private final UserDao userDao;
  //private final UserNotificationDao2 userNotificationDao;
  private final PasswordHash passwordHash;
  
  
  public UserRegistrationResource( UserDao userDao, PasswordHash passwordHash,Validator validator) {
    super(validator);
    this.userDao = userDao;
    //this.userNotificationDao = userNotificationDao;
    this.passwordHash = passwordHash;
  }
  
  @POST
  @UnitOfWork
  @ApiOperation(value = "User profile registration",
      notes = "Allows a user to register.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success"),
      @ApiResponse(code = 500, message = "System Error")
  })
  @Timed(name = "User.create")
  public Response signup( @NotNull User user) {
      validate(user, "rest", RestValidationGroup.class, Default.class);
      // check if the email already exists.
    User findUserByEmail = userDao.findUserByEmail(user.getEmail());
    if (findUserByEmail != null){
        ErrorInfo errRet = new ErrorInfo();
        String errorString = GeneralErrors.DUPLICATE_ENTRY.getMessage().replace("REPLACE",  "email");
        errRet.addError(GeneralErrors.DUPLICATE_ENTRY.getCode(), errorString );
        throw new WebApplicationException(Response.noContent().status(Response.Status.BAD_REQUEST).entity(errRet).build());
    }
    String hash = null;
    try {
      hash = passwordHash.createHash(user.getPassword());
      user.setPassword(hash);
    } catch (Exception e) {
      LOG.error("Unable to create a password hash.", e);
      throw new InternalServerErrorException("Unable to create a password hash.");
    }

    try {
      validate(user, "create", Default.class, PersistValidationGroup.class);
      for(UserNotification notificationType : user.getNotificationType()){
          notificationType.setUserId(user);
      }
      User retUser= userDao.create(user); 

    }
    catch (ConstraintViolationException exception) {
        throw exception;
    }
    return Response.ok().build();

  }
}
