package com.cgi.poc.dw.rest.model.validator;

import com.cgi.poc.dw.rest.model.LoginUserDto;
import com.cgi.poc.dw.rest.model.error.ErrorMessage;
import com.cgi.poc.dw.rest.model.error.ErrorMessageWebException;
import org.apache.commons.lang3.StringUtils;

public class LoginUserDtoValidator {

  public static void validate(LoginUserDto loginUserDto) {
    if (loginUserDto == null) {
      throw new ErrorMessageWebException(ErrorMessage.LOGIN_FAIL_NO_CREDENTIALS);
    }
    if (StringUtils.isBlank(loginUserDto.getEmail())) {
      throw new ErrorMessageWebException(ErrorMessage.LOGIN_FAIL_NO_EMAIL);
    } else if (StringUtils.isBlank(loginUserDto.getPassword())) {
      throw new ErrorMessageWebException(ErrorMessage.LOGIN_FAIL_NO_PASSWORD);
    }
  }
}
