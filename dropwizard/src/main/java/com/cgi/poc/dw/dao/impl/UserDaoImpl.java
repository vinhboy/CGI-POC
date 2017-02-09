package com.cgi.poc.dw.dao.impl;

import com.cgi.poc.dw.dao.UserDao;
import com.cgi.poc.dw.dao.mapper.UserMapper;
import com.cgi.poc.dw.dao.model.User;
import java.util.List;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

//@LogSqlFactory
@RegisterMapper(UserMapper.class)
public interface UserDaoImpl extends UserDao {

  @Override
  @SqlUpdate("INSERT INTO users (email, hash,role) values (:email, :hash, :role)")
  void createUser(@BindBean User user) throws Exception;

  @Override
  @SqlQuery("SELECT * FROM users WHERE email = :userEmail")
  User findUserByEmail(@Bind("userEmail") String userEmail);

  @Override
  @SqlQuery("SELECT * FROM users WHERE role = 'NORMAL'")
  List<User> getAllNormalUsers();
}
