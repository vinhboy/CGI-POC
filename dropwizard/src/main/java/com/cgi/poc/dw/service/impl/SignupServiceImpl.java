package com.cgi.poc.dw.service.impl;

import com.cgi.poc.dw.auth.MyPasswordValidator;
import com.cgi.poc.dw.auth.PasswordHash;
import com.cgi.poc.dw.auth.model.Role;
import com.cgi.poc.dw.dao.UserDao;
import com.cgi.poc.dw.dao.model.User;
import com.cgi.poc.dw.rest.model.SignupUserDto;
import com.cgi.poc.dw.rest.model.error.ErrorMessage;
import com.cgi.poc.dw.rest.model.error.ErrorMessageWebException;
import com.cgi.poc.dw.rest.model.validator.SignupUserDtoValidator;
import com.cgi.poc.dw.service.SignupService;
import com.google.inject.Inject;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignupServiceImpl implements SignupService {

  private final static Logger LOG = LoggerFactory.getLogger(SignupServiceImpl.class);
  private final static MyPasswordValidator myPasswordValidator = new MyPasswordValidator();
  private final UserDao userDao;

  @Inject
  public SignupServiceImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  public Response signup(SignupUserDto signupUserDto) {
    SignupUserDtoValidator.validate(signupUserDto, myPasswordValidator);
    String hash = "";
    try {
      hash = PasswordHash.createHash(signupUserDto.getPassword());
    } catch (NoSuchAlgorithmException e) {
      LOG.error("Unable to create hash.", e);
      throw new ErrorMessageWebException(ErrorMessage.SIGNUP_FAIL);
    } catch (InvalidKeySpecException e) {
      LOG.error("Unable to create hash.", e);
      throw new ErrorMessageWebException(ErrorMessage.SIGNUP_FAIL);
    }
    User user = new User(signupUserDto.getEmail(), hash, Role.NORMAL);
    try {
      userDao.createUser(user);
    } catch (Exception exception) {
      LOG.warn("Unable to create user because user already exists.", exception);
      throw new ErrorMessageWebException(ErrorMessage.SIGNUP_FAIL_USER_ALREADY_EXISTS);
    }
    return Response.ok().build();
  }
}
