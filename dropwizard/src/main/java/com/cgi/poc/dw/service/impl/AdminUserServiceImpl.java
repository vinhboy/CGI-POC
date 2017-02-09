package com.cgi.poc.dw.service.impl;

import com.cgi.poc.dw.dao.UserDao;
import com.cgi.poc.dw.dao.model.User;
import com.cgi.poc.dw.service.AdminUserService;
import com.google.inject.Inject;
import java.util.List;
import javax.ws.rs.core.Response;

public class AdminUserServiceImpl implements AdminUserService {

  private final UserDao userDao;

  @Inject
  public AdminUserServiceImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public Response getUsers(User principal) {
    List<User> users = userDao.getAllNormalUsers();
    return Response.ok(users).build();
  }
}
