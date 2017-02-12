package com.cgi.poc.dw.dao;

import java.util.List;

public interface UserNotificationDao2 {

  long findNotificationMethodIdByName(String name);

  void createUserNotification(long id, List<Long> notificationTypes);

}
