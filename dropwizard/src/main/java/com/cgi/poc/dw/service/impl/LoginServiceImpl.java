package com.cgi.poc.dw.service.impl;

import com.cgi.poc.dw.auth.PasswordHash;
import com.cgi.poc.dw.auth.service.JwtBuilderService;
import com.cgi.poc.dw.dao.UserDao;
import com.cgi.poc.dw.dao.model.User;
import com.cgi.poc.dw.rest.model.AuthTokenResponseDto;
import com.cgi.poc.dw.rest.model.LoginUserDto;
import com.cgi.poc.dw.rest.model.error.ErrorMessage;
import com.cgi.poc.dw.rest.model.error.ErrorMessageWebException;
import com.cgi.poc.dw.rest.model.validator.LoginUserDtoValidator;
import com.cgi.poc.dw.service.LoginService;
import com.google.inject.Inject;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.ws.rs.core.Response;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginServiceImpl implements LoginService {

  private final static Logger LOG = LoggerFactory.getLogger(LoginServiceImpl.class);
  private final UserDao userDao;
  private final JwtBuilderService jwtBuilderService;

  @Inject
  public LoginServiceImpl(UserDao userDao, JwtBuilderService jwtBuilderService) {
    this.userDao = userDao;
    this.jwtBuilderService = jwtBuilderService;
  }

  @Override
  public Response login(LoginUserDto loginUserDto) {
    LoginUserDtoValidator.validate(loginUserDto);
    User user = userDao.findUserByEmail(loginUserDto.getEmail());
    if (user == null) {
      LOG.warn("User not found.");
      throw new ErrorMessageWebException(
          ErrorMessage.LOGIN_FAIL_USER_NOT_FOUND_OR_WRONG_PASSWORD);
    }

    if (hasValidPassword(loginUserDto, user)) {
      String authToken;
      try {
        authToken = jwtBuilderService.createJwt(user);
      } catch (JoseException e) {
        LOG.error("Unable to create authToken.", e);
        throw new ErrorMessageWebException(
            ErrorMessage.LOGIN_FAIL_USER_NOT_FOUND_OR_WRONG_PASSWORD);
      }
      AuthTokenResponseDto authTokenResponseDto = new AuthTokenResponseDto(authToken,
          user.getRole());
      return Response.ok().entity(authTokenResponseDto).build();
    } else {
      throw new ErrorMessageWebException(
          ErrorMessage.LOGIN_FAIL_USER_NOT_FOUND_OR_WRONG_PASSWORD);
    }
  }

  private boolean hasValidPassword(LoginUserDto loginUserDto, User user) {
    boolean hasValidPassword = false;
    try {
      hasValidPassword = PasswordHash.validatePassword(loginUserDto.getPassword(), user.getHash());
    } catch (NoSuchAlgorithmException e) {
      LOG.error("Unable to compute hash.", e);
      throw new ErrorMessageWebException(
          ErrorMessage.LOGIN_FAIL_USER_NOT_FOUND_OR_WRONG_PASSWORD);
    } catch (InvalidKeySpecException e) {
      LOG.warn("Unable to compute hash.", e);
      throw new ErrorMessageWebException(
          ErrorMessage.LOGIN_FAIL_USER_NOT_FOUND_OR_WRONG_PASSWORD);
    }
    return hasValidPassword;
  }
}
