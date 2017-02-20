package com.cgi.poc.dw.rest.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.mail.MessagingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cgi.poc.dw.auth.model.Role;
import com.cgi.poc.dw.dao.model.NotificationType;
import com.cgi.poc.dw.dao.model.User;
import com.cgi.poc.dw.dao.model.UserNotificationType;
import com.cgi.poc.dw.helper.IntegrationTest;
import com.cgi.poc.dw.helper.IntegrationTestHelper;
import com.cgi.poc.dw.util.Error;
import com.cgi.poc.dw.util.ErrorInfo;
import com.cgi.poc.dw.util.GeneralErrors;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;

public class UpdateProfileResourceIntegrationTest extends IntegrationTest {

  private static final String url = "http://localhost:%d/updateProfile";

  private User tstUser;
  private GreenMail smtpServer;
  
  @BeforeClass
  public static void createUser() throws SQLException {
    IntegrationTestHelper.signupResidentUser();
  }
  
  @Before
  public void initUser() {
  	tstUser = new User();
  	tstUser.setEmail("resident@cgi.com");
  	tstUser.setPassword("!QAZ1qaz");
  	tstUser.setFirstName("john");
  	tstUser.setLastName("doe");
  	tstUser.setRole(Role.RESIDENT.name());
  	tstUser.setPhone("1234567890");
  	tstUser.setZipCode("95814");
  	tstUser.setCity("Sacramento");
  	tstUser.setState("California");
  	tstUser.setRequiredStreet("required street");
  	tstUser.setOptionalStreet("optional street");
  	tstUser.setAllowPhoneLocalization(false);
  	tstUser.setLatitude(38.5824933);
  	tstUser.setLongitude(-121.4941738);
    UserNotificationType selNot = new UserNotificationType(Long.valueOf(NotificationType.SMS.ordinal()));
    Set<UserNotificationType> notificationType = new HashSet<>();
    notificationType.add(selNot);
    tstUser.setNotificationType(notificationType);

    smtpServer = new GreenMail(new ServerSetup(3025, "127.0.0.1",
        ServerSetup.PROTOCOL_SMTP));
    smtpServer.start();
  }
  
  @After
  public void exit() {
    if (smtpServer != null) {
      smtpServer.stop();
    }
  }
  
  @AfterClass
  public static void cleanup() {
    IntegrationTestHelper.cleanDbState();
  }

  @Test
  public void noArgument() throws JSONException {
    Client client = new JerseyClientBuilder().build();
    String authToken = IntegrationTestHelper.getAuthToken("resident@cgi.com", "!QAZ1qaz", RULE);
    Response response = client.
        target(String.format(url, RULE.getLocalPort())).
        request().
        header("Authorization", "Bearer " + authToken).
        post(Entity.entity(null, MediaType.APPLICATION_JSON_TYPE));
    Assert.assertEquals(422, response.getStatus());
    JSONObject responseJo = new JSONObject(response.readEntity(String.class));
    Assert.assertTrue(!StringUtils.isBlank(responseJo.optString("errors")));
    Assert.assertEquals("[\"The request body may not be null\"]", responseJo.optString("errors"));
  }

  @Test
  public void invalidPasswordTooShort() {
    Client client = new JerseyClientBuilder().build();
    tstUser.setPassword("a");

    String authToken = IntegrationTestHelper.getAuthToken("resident@cgi.com", "!QAZ1qaz", RULE);
    Response response = client.
        target(String.format(url, RULE.getLocalPort())).
        request().
        header("Authorization", "Bearer " + authToken).
        post(Entity.entity(tstUser, MediaType.APPLICATION_JSON_TYPE));

    assertNotNull(response);

    Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    ErrorInfo errorInfo = response.readEntity(ErrorInfo.class);
    // this should fail 2 validations.. size and pwd validity
    // this test is specific to validity.. 
    boolean bValidErr = false;
    for (Error error : errorInfo.getErrors()) {
      assertThat(error.getCode()).isEqualTo(GeneralErrors.INVALID_INPUT.getCode());
      // The data provided in the API call is invalid. Message: <XXXXX>
      // where XXX is the message associated to the validation
      String partString = "password  must be greater that 2 character, contain no whitespace, and have at least one number and one letter.";
      String expectedErrorString = GeneralErrors.INVALID_INPUT.getMessage()
          .replace("REPLACE", partString);
      if (error.getMessage().equals(expectedErrorString)) {
        bValidErr = true;
      }
    }
    assertThat(bValidErr).isEqualTo(true);

  }

  @Test
  public void invalidPasswordContainsWhiteSpace() {
    Client client = new JerseyClientBuilder().build();
    tstUser.setPassword("abcd abcd");

    String authToken = IntegrationTestHelper.getAuthToken("resident@cgi.com", "!QAZ1qaz", RULE);
    Response response = client.
        target(String.format(url, RULE.getLocalPort())).
        request().
        header("Authorization", "Bearer " + authToken).
        post(Entity.entity(tstUser, MediaType.APPLICATION_JSON_TYPE));
    assertNotNull(response);
    Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    ErrorInfo errorInfo = response.readEntity(ErrorInfo.class);
    for (Error error : errorInfo.getErrors()) {
      assertThat(error.getCode()).isEqualTo(GeneralErrors.INVALID_INPUT.getCode());
      // The data provided in the API call is invalid. Message: <XXXXX>
      // where XXX is the message associated to the validation
      String partString = "password  must be greater that 2 character, contain no whitespace, and have at least one number and one letter.";
      String expectedErrorString = GeneralErrors.INVALID_INPUT.getMessage()
          .replace("REPLACE", partString);
      assertThat(error.getMessage()).isEqualTo(expectedErrorString);
    }

  }

  @Test
  public void invalidPasswordNoAlphabeticalCharacters() {
    Client client = new JerseyClientBuilder().build();
    tstUser.setPassword("123");

    String authToken = IntegrationTestHelper.getAuthToken("resident@cgi.com", "!QAZ1qaz", RULE);
    Response response = client.
        target(String.format(url, RULE.getLocalPort())).
        request().
        header("Authorization", "Bearer " + authToken).
        post(Entity.entity(tstUser, MediaType.APPLICATION_JSON_TYPE));
    assertNotNull(response);
    Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    ErrorInfo errorInfo = response.readEntity(ErrorInfo.class);
    for (Error error : errorInfo.getErrors()) {
      assertThat(error.getCode()).isEqualTo(GeneralErrors.INVALID_INPUT.getCode());
      // The data provided in the API call is invalid. Message: <XXXXX>
      // where XXX is the message associated to the validation
      String partString = "password  must be greater that 2 character, contain no whitespace, and have at least one number and one letter.";
      String expectedErrorString = GeneralErrors.INVALID_INPUT.getMessage()
          .replace("REPLACE", partString);
      assertThat(error.getMessage()).isEqualTo(expectedErrorString);
    }
  }

  @Test
  public void updateSuccess() throws MessagingException {
    Client client = new JerseyClientBuilder().build();
    tstUser.setFirstName("Jane");
    String authToken = IntegrationTestHelper.getAuthToken("resident@cgi.com", "!QAZ1qaz", RULE);
    Response response = client.
        target(String.format(url, RULE.getLocalPort())).
        request().
        header("Authorization", "Bearer " + authToken).
        post(Entity.entity(tstUser, MediaType.APPLICATION_JSON_TYPE));
    Assert.assertEquals(200, response.getStatus());
  }

  @Test
  public void invalidPhoneNumber() throws JSONException, NoSuchAlgorithmException, InvalidKeySpecException {
  	Client client = new JerseyClientBuilder().build();
    tstUser.setPhone("44343");
    String authToken = IntegrationTestHelper.getAuthToken("resident@cgi.com", "!QAZ1qaz", RULE);
    Response response = client.
        target(String.format(url, RULE.getLocalPort())).
        request().
        header("Authorization", "Bearer " + authToken).
        post(Entity.entity(tstUser, MediaType.APPLICATION_JSON_TYPE));
    
    Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    ErrorInfo errorInfo = response.readEntity(ErrorInfo.class);
    for (Error error : errorInfo.getErrors()) {
      assertThat(error.getCode()).isEqualTo(GeneralErrors.INVALID_INPUT.getCode());
      // The data provided in the API call is invalid. Message: <XXXXX>
      // where XXX is the message associated to the validation
      String partString = "phone  size must be between 10 and 10";
      String expectedErrorString = GeneralErrors.INVALID_INPUT.getMessage()
          .replace("REPLACE", partString);
      assertThat(error.getMessage()).isEqualTo(expectedErrorString);
    }
  }


  @Test
  public void invalidZipCode() throws JSONException {
    Client client = new JerseyClientBuilder().build();
    tstUser.setZipCode("983");

    String authToken = IntegrationTestHelper.getAuthToken("resident@cgi.com", "!QAZ1qaz", RULE);
    Response response = client.
        target(String.format(url, RULE.getLocalPort())).
        request().
        header("Authorization", "Bearer " + authToken).
        post(Entity.entity(tstUser, MediaType.APPLICATION_JSON_TYPE));
    assertNotNull(response);
    Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    ErrorInfo errorInfo = response.readEntity(ErrorInfo.class);
    for (Error error : errorInfo.getErrors()) {
      assertThat(error.getCode()).isEqualTo(GeneralErrors.INVALID_INPUT.getCode());
      // The data provided in the API call is invalid. Message: <XXXXX>
      // where XXX is the message associated to the validation
      String partString = "zipCode  is invalid.";
      String expectedErrorString = GeneralErrors.INVALID_INPUT.getMessage()
          .replace("REPLACE", partString);
      assertThat(error.getMessage().trim().toLowerCase()).isEqualTo(expectedErrorString.trim().toLowerCase());
    }
  }
}
