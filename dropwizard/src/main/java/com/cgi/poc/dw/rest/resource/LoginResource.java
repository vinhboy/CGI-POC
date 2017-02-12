package com.cgi.poc.dw.rest.resource;

import com.cgi.poc.dw.auth.model.Role;
import com.cgi.poc.dw.auth.service.JwtBuilderService;
import com.cgi.poc.dw.auth.service.PasswordHash;
import com.cgi.poc.dw.dao.UserDao;
import com.cgi.poc.dw.dao.model.User;
import com.cgi.poc.dw.rest.model.AuthTokenResponseDto;
import com.cgi.poc.dw.rest.model.LoginUserDto;
import com.cgi.poc.dw.rest.model.validator.LoginUserDtoValidator;
import com.cgi.poc.dw.service.LoginService;
import com.cgi.poc.dw.service.LoginServiceImpl;
import com.cgi.poc.dw.util.LoginValidationGroup;
import com.cgi.poc.dw.util.RestValidationGroup;
import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "/login", basePath = "/")
public class LoginResource extends BaseResource{

  private final static Logger LOG = LoggerFactory.getLogger(LoginServiceImpl.class);
  private final UserDao userDao;
  private final JwtBuilderService jwtBuilderService;
  private final PasswordHash passwordHash;

  public LoginResource(UserDao userDao, JwtBuilderService jwtBuilderService, PasswordHash passwordHash,Validator validator) {
    super(validator);
    this.userDao = userDao;
    this.jwtBuilderService = jwtBuilderService;
    this.passwordHash = passwordHash;
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "User login",
      notes = "Allows the user to login by returning a JWT token on success")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success"),
      @ApiResponse(code = 401, message = "Authentication failed."),
      @ApiResponse(code = 500, message = "System Error")
  })
  @UnitOfWork
  @Timed(name = "User.login")
  public Response login( User user) {
    if (user == null) {
      throw new BadRequestException("Missing credentials.");
    }

    validate(user, "rest", LoginValidationGroup.class);

    User retUser = userDao.findUserByEmail(user.getEmail());
    if (retUser == null) {
      LOG.warn("User not found.");
      throw new NotFoundException("Invalid username or password.");
    }

    if (hasValidPassword(user, retUser)) {
      String authToken;
      try {
        authToken = jwtBuilderService.createJwt(user);
      } catch (JoseException e) {
        LOG.error("Unable to create authToken.", e);
        throw new InternalServerErrorException("Unable to issue authToken.");
      }
      AuthTokenResponseDto authTokenResponseDto = new AuthTokenResponseDto(authToken,
          Role.valueOf(retUser.getRole()));
      return Response.ok().entity(authTokenResponseDto).build();
    } else {
      LOG.warn("Invalid password.");
      throw new NotFoundException("Invalid username or password.");
    }
  }

  private boolean hasValidPassword(User inUser, User retUser) {
    boolean hasValidPassword = false;
    try {
      hasValidPassword = passwordHash
          .validatePassword(inUser.getPassword(), retUser.getPassword());
    } catch (Exception e) {
      LOG.error("Unable to compute password hash.", e);
      throw new InternalServerErrorException("Unable to compute password hash.");
    }
    return hasValidPassword;
  }
}
