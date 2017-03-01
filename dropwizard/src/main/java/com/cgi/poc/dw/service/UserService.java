package com.cgi.poc.dw.service;

import com.cgi.poc.dw.dao.model.User;
import com.cgi.poc.dw.rest.dto.FcmTokenDto;
import javax.ws.rs.core.Response;

public interface UserService {
  Response registerUser(User userDto);

	Response updateUser(User user, User modifiedUser);

  Response updateFcmToken(User user, FcmTokenDto fcmTokenDto);
}
