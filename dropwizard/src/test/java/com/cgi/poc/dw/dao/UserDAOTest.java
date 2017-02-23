package com.cgi.poc.dw.dao;

import com.cgi.poc.dw.api.service.data.GeoCoordinates;
import com.cgi.poc.dw.auth.model.Role;
import com.cgi.poc.dw.dao.model.NotificationType;
import com.cgi.poc.dw.dao.model.User;
import com.cgi.poc.dw.dao.model.UserNotificationType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class UserDAOTest extends DaoUnitTestBase{

  UserDao userDao;

  public UserDAOTest() {
    super();
  }

  @Before
  public void setUp() throws Exception {
    super.setUp();
    userDao = new UserDao(getSessionFactory());
  }

  @After
  public void tearDown() {
    super.tearDown();
  }
  private List<GeoCoordinates> geoCoordinates;

  @Test
  public void userIsWithinRadius(){
    GeoCoordinates geo = new GeoCoordinates();
    geo.setLatitude(10.00);
    geo.setLongitude(20.00);

    Double radius = 50.00;

    geoCoordinates = Arrays.asList(geo);

    User user = createUser();

    userDao.save(user);

    List<User> users = userDao.getGeoWithinRadius(geoCoordinates, radius);

    assertEquals(1, users.size());
    assertEquals("test@test.com", users.get(0).getEmail());
  }

  @Test
  public void userIsOutsideRadius(){
    GeoCoordinates geo = new GeoCoordinates();
    geo.setLatitude(15.00);
    geo.setLongitude(20.00);

    Double radius = 50.00;

    geoCoordinates = Arrays.asList(geo);

    List<User> users = userDao.getGeoWithinRadius(geoCoordinates, radius);

    assertEquals(0, users.size());
  }

  @Test
  public void newNotificationSelectionsArePersisted() {
    User user = createUser();
    userDao.save(user);

    User retrievedUser = userDao.findUserByEmail(user.getEmail());
    List<Long> retrievedNotificationTypes = mapToOrdinals(retrievedUser.getNotificationType());
    assertEquals(Long.valueOf(NotificationType.SMS.ordinal()), retrievedNotificationTypes.get(0));

    retrievedUser.addNotificationType(NotificationType.PUSH);
    userDao.save(retrievedUser);

    retrievedUser = userDao.findUserByEmail(user.getEmail());
    retrievedNotificationTypes = mapToOrdinals(retrievedUser.getNotificationType());
    assertEquals(2, retrievedNotificationTypes.size());
    assertEquals(Long.valueOf(NotificationType.SMS.ordinal()), retrievedNotificationTypes.get(0));
    assertEquals(Long.valueOf(NotificationType.PUSH.ordinal()), retrievedNotificationTypes.get(1));

    retrievedUser.addNotificationType(NotificationType.EMAIL);
    userDao.save(retrievedUser);

    retrievedUser = userDao.findUserByEmail(user.getEmail());
    retrievedNotificationTypes = mapToOrdinals(retrievedUser.getNotificationType());
    assertEquals(3, retrievedNotificationTypes.size());
    assertEquals(Long.valueOf(NotificationType.EMAIL.ordinal()), retrievedNotificationTypes.get(0));
    assertEquals(Long.valueOf(NotificationType.SMS.ordinal()), retrievedNotificationTypes.get(1));
    assertEquals(Long.valueOf(NotificationType.PUSH.ordinal()), retrievedNotificationTypes.get(2));

    retrievedUser.getNotificationType().removeIf(unt -> unt.getNotificationId() == Long.valueOf(NotificationType.SMS.ordinal()));
    userDao.save(retrievedUser);

    retrievedUser = userDao.findUserByEmail(user.getEmail());
    retrievedNotificationTypes = mapToOrdinals(retrievedUser.getNotificationType());
    assertEquals(2, retrievedNotificationTypes.size());
    assertEquals(Long.valueOf(NotificationType.EMAIL.ordinal()), retrievedNotificationTypes.get(0));
    assertEquals(Long.valueOf(NotificationType.PUSH.ordinal()), retrievedNotificationTypes.get(1));

    retrievedUser.getNotificationType().clear();
    userDao.save(retrievedUser);

    retrievedUser = userDao.findUserByEmail(user.getEmail());
    retrievedNotificationTypes = mapToOrdinals(retrievedUser.getNotificationType());
    assertEquals(0, retrievedNotificationTypes.size());
  }

  private User createUser() {
    User user = new User();

    user.setEmail("test@test.com");
    user.setPassword("test123");
    user.setFirstName("john");
    user.setLastName("smith");
    user.setRole(Role.RESIDENT.name());
    user.setPhone("1234567890");
    user.setZipCode("98765");
    user.setCity("Sacramento");
    user.setState("CA");
    user.setAddress1("required street");
    user.setAddress2("optional street");
    user.setLatitude(10.5);
    user.setLongitude(20.0);

    user.addNotificationType(NotificationType.SMS);

    return user;
  }

  private List<Long> mapToOrdinals(Set<UserNotificationType> set) {
    return set.stream().map(unt -> unt.getNotificationId()).sorted().collect(Collectors.toList());
  }
}
