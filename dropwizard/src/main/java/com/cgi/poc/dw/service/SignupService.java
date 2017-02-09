package com.cgi.poc.dw.service;

import com.cgi.poc.dw.rest.model.SignupUserDto;
import javax.ws.rs.core.Response;

public interface SignupService {

  Response signup(SignupUserDto userDto);
}
