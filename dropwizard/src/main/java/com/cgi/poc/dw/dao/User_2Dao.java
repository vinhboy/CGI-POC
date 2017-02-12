package com.cgi.poc.dw.dao;

import com.cgi.poc.dw.dao.model.User_2;
import java.util.Iterator;
import java.util.List;

public interface User_2Dao {

  long createUser(User_2 user) throws Exception;

  User_2 findUserByEmail(String email);
  
  List<User_2> getAllNormalUsers();

  void createUserNotification(List<Integer> id, Iterator<String> notificationTypes);
}
