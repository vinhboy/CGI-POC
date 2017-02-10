package com.cgi.poc.dw.dao.mapper;

import com.cgi.poc.dw.dao.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class UserNotificationMapper implements ResultSetMapper<Long> {

  public Long map(int index, ResultSet r, StatementContext ctx) throws SQLException {
    
    Long notificationMethodId = r.getLong("id");
//    User user = new User();
//    user.setEmail(r.getString("email"));
//    user.setPassword(r.getString("password"));
//    user.setId(r.getLong("id"));
//    user.setRole(Role.valueOf(r.getString("role")));
    return notificationMethodId;
  }
}
