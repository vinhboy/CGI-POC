package com.cgi.poc.dw.helper;

import com.cgi.poc.dw.CgiPocConfiguration;
import com.cgi.poc.dw.dao.HibernateUtil;
import com.cgi.poc.dw.dao.UserDao;
import com.cgi.poc.dw.dao.model.EventNotification;
import com.cgi.poc.dw.dao.model.User;
import com.cgi.poc.dw.rest.model.LoginUserDto;
import com.cgi.poc.dw.rest.resource.UserResourceIntegrationTest;
import io.dropwizard.testing.junit.DropwizardAppRule;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.internal.SessionImpl;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;

public class IntegrationTestHelper {
  public static void deleteAllEventNotfications()
      throws SQLException {
    Connection sqlConnection = null;
    
    
    try {
      SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
      sqlConnection = ((SessionImpl) sessionFactory.openSession()).connection();
      Statement st = sqlConnection.createStatement();
      st.executeUpdate("delete from event_notification_zipcode");
      st.executeUpdate("delete from event_notification");
      sqlConnection.commit();
    } catch (HibernateException | SQLException ex) {
      sqlConnection.rollback();
      ex.printStackTrace();
    }
  }   
   public static void addEventNotfication(EventNotification event)
      throws SQLException {
    Connection sqlConnection = null;
    
    
    try {
      SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
      sqlConnection = ((SessionImpl) sessionFactory.openSession()).connection();
      Statement st = sqlConnection.createStatement();
      st.executeUpdate("INSERT INTO event_notification (id, description," +
           "user_id,type,geometry,url1,url2,citizensAffected) VALUES (" 
            + event.getId() + " ," 
            + " '" + event.getDescription() + "',"
            + event.getUserId().getId() + " ," 
            + " '" + event.getType() + "',"
            + " '" + event.getGeometry() + "',"
            + " '" + event.getUrl1() + "',"
            + " '" + event.getUrl2() + "',"
            + event.getCitizensAffected()+ " )"); 


      sqlConnection.commit();
    } catch (HibernateException | SQLException ex) {
      sqlConnection.rollback();
      ex.printStackTrace();
    }
  }
  public static void signupAdminUser()
      throws SQLException {
    Connection sqlConnection = null;
    try {
      SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
      sqlConnection = ((SessionImpl) sessionFactory.openSession()).connection();
      Statement st = sqlConnection.createStatement();
      st.executeUpdate(
          "INSERT INTO user (id, first_name, last_name, email, password, phone, address1, address2, city, state, zip_code, role, latitude, longitude, email_notification, sms_notification, push_notification)\n"
              + "select 100,\n"
              + "'john',\n"
              + "'smith',\n"
              + "'admin100@cgi.com',\n"
              + "'518bd5283161f69a6278981ad00f4b09a2603085f145426ba8800c:8bd85a69ed2cb94f4b9694d67e3009909467769c56094fc0fce5af',\n"
              + "'1234567890',\n"
              + "'required street',\n"
              + "'optional street',\n"
              + "'Sacramento',\n"
              + "'CA',\n"
              + "'95814',\n"
              + "'ADMIN',\n"
              + "38.5824933,\n"
              + "-121.4941738,\n" +
                  "0,\n" +
                  "0,\n" +
                  "0\n"
              + "where not exists (select * from user where id = 100)");

      sqlConnection.commit();
    } catch (Exception ex) {
      sqlConnection.rollback();
      ex.printStackTrace();
    }
  }

  public static void signupResidentUser()
      throws SQLException {
    Connection sqlConnection = null;
    try {
      SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
      sqlConnection = ((SessionImpl) sessionFactory.openSession()).connection();
      Statement st = sqlConnection.createStatement();
      st.executeUpdate(
          "INSERT INTO user (id, first_name, last_name, email, password, phone, address1, address2, city, state, zip_code, role, latitude, longitude, email_notification, sms_notification, push_notification)\n"
              + "select 200,\n"
              + "'john',\n"
              + "'doe',\n"
              + "'resident@cgi.com',\n"
              + "'9e5f3dd72fbd5f309131364baf42b446f570629f4a809390be533f:1db93c4885d4bf980e92286d74da720dc298fdc1a29c89cf9c67ce',\n"
              + "'1234567890',\n"
              + "'required street',\n"
              + "'optional street',\n"
              + "'Sacramento',\n"
              + "'CA',\n"
              + "'95814',\n"
              + "'RESIDENT',\n"
              + "38.5824933,\n"
              + "-121.4941738,\n" +
                  "0,\n" +
                  "0,\n" +
                  "0\n"
              + "where not exists (select * from user where id = 200)");

      sqlConnection.commit();
    } catch (Exception ex) {
      sqlConnection.rollback();
      ex.printStackTrace();
    }
  }

  public static void signupResidentUser(String zipcode, String email)
      throws SQLException {
    Connection sqlConnection = null;
    try {
      SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
      sqlConnection = ((SessionImpl) sessionFactory.openSession()).connection();
      Statement st = sqlConnection.createStatement();
      st.executeUpdate(
          "INSERT INTO user (id, first_name, last_name, email, password, phone, address1, address2, city, state, zip_code, role, latitude, longitude, email_notification, sms_notification, push_notification)\n"
              + "VALUES ( 1111,\n"
              + "'john',\n"
              + "'doe',\n"
              + " '"+email+"',\n"
              + "'9e5f3dd72fbd5f309131364baf42b446f570629f4a809390be533f:1db93c4885d4bf980e92286d74da720dc298fdc1a29c89cf9c67ce',\n"
              + "'1234567890',\n"
              + "'required street',\n"
              + "'optional street',\n"
              + "'Sacramento',\n"
              + "'CA',\n"
              +  " '"+ zipcode+"',\n"
              + "'RESIDENT',\n"
              + "32.7920948,\n"
              + "-117.2323367,\n" +
                  "0,\n" +
                  "0,\n" +
                  "0\n"
              + ")");

      sqlConnection.commit();
    } catch (Exception ex) {
      sqlConnection.rollback();
      ex.printStackTrace();
    }
  }

  public static void addEmailNotificationForUser(String email)
          throws SQLException {
    Connection sqlConnection = null;
    try {
      SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
      sqlConnection = ((SessionImpl) sessionFactory.openSession()).connection();
      Statement st = sqlConnection.createStatement();
      st.executeUpdate(
              "update user set email_notification = 1 where email = '" + email + "'");
      sqlConnection.commit();
    } catch (Exception ex) {
      sqlConnection.rollback();
      ex.printStackTrace();
    }
  }

  public static String getAuthToken(String email, String password,
      DropwizardAppRule<CgiPocConfiguration> RULE) {
    Client client = new JerseyClientBuilder().build();
    LoginUserDto loginUserDto = new LoginUserDto();
    loginUserDto.setEmail(email);
    loginUserDto.setPassword(password);
    Response loginResponse = client
        .target(String.format("http://localhost:%d/login", RULE.getLocalPort())).request()
        .post(Entity.json(loginUserDto));
    JSONObject loginResponseJo = null;
    try {
      loginResponseJo = new JSONObject(loginResponse.readEntity(String.class));
    } catch (JSONException e) {
      e.printStackTrace();
    }
    String authToken = loginResponseJo.optString("authToken");
    Assert.assertTrue(!StringUtils.isBlank(authToken));
    return authToken;
  }

  public static void cleanDbState() {
    try {
      SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
      Connection sqlConnection = ((SessionImpl) sessionFactory.openSession()).connection();
      Statement st = sqlConnection.createStatement();
      st.executeUpdate("delete from event_notification_zipcode");
      st.executeUpdate("delete from event_notification");
      st.executeUpdate("delete from user where email != 'admin@cgi.com'");

      sqlConnection.commit();
    } catch (Exception ex) {
      Logger.getLogger(UserResourceIntegrationTest.class.getName())
          .log(Level.SEVERE, null, ex);
    }

  }

  public static void removeAllNotificationsForUser(String email) throws SQLException {
    Connection sqlConnection = null;
    try {
      SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
      sqlConnection = ((SessionImpl) sessionFactory.openSession()).connection();
      Statement st = sqlConnection.createStatement();
      st.executeUpdate(
              "update user set email_notification = 0, sms_notification = 0, push_notification = 0 where email = '" + email + "'");
      sqlConnection.commit();
    } catch (Exception ex) {
      sqlConnection.rollback();
      ex.printStackTrace();
    }
  }

  public static Response requestPost(String url, DropwizardAppRule<CgiPocConfiguration> rule, Object entity) {
    Client client = new JerseyClientBuilder().build();
    return client.target(String.format(url, rule.getLocalPort())).request().post(Entity.json(entity));
  }

  public static Response requestGet(String url, DropwizardAppRule<CgiPocConfiguration> rule) {
    Client client = new JerseyClientBuilder().build();
    return client.target(String.format(url, rule.getLocalPort())).request().get();
  }

  public static Response requestPut(String url, DropwizardAppRule<CgiPocConfiguration> rule, Object entity) {
    Client client = new JerseyClientBuilder().build();
    return client.target(String.format(url, rule.getLocalPort())).request().put(Entity.json(entity));
  }

  public static User getUserFromDb(String email) {
    SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
    Transaction transaction = null;
    try {
      transaction = sessionFactory.getCurrentSession().beginTransaction();
      UserDao dao = new UserDao(sessionFactory);
      return dao.findUserByEmail(email);
    }
    finally {
      transaction.rollback();
    }
  }
}
