package com.cgi.poc.dw.dao;

import com.cgi.poc.dw.dao.model.User;
import java.util.List;

public interface UserDao {

  void createUser(User user) throws Exception;

  User findUserByEmail(String email);

  List<User> getAllNormalUsers();
}
