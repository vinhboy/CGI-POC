package com.cgi.poc.dw.dao;

import com.cgi.poc.dw.dao.mapper.UserMapper;
import com.cgi.poc.dw.dao.model.User_2;
import java.util.List;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

//@LogSqlFactory
@RegisterMapper(UserMapper.class)
public interface User_2DaoImpl extends User_2Dao {

  @Override
  @SqlUpdate("INSERT INTO user (first_name, last_name, email, password, phone, zip_code, role, latitude, longitude) values " 
      + "(:firstName, :lastName, :email, :password, :phone, :zipCode, :role, :latitude, :longitude)")
  @GetGeneratedKeys
  long createUser(@BindBean User_2 user) throws Exception;
  
  @Override
  @SqlQuery("SELECT * FROM user WHERE email = :userEmail")
  User_2 findUserByEmail(@Bind("userEmail") String userEmail);

  long findNotificationIdByName(String name);

  @Override
  @SqlQuery("SELECT * FROM user WHERE role = 'RESIDENT'")
  List<User_2> getAllNormalUsers();
}
