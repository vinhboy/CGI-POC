package com.cgi.poc.dw.rest.resource;

import com.cgi.poc.dw.dao.model.EventNotification;
import com.cgi.poc.dw.dao.model.User;
import com.cgi.poc.dw.helper.IntegrationTest;
import com.cgi.poc.dw.helper.IntegrationTestHelper;
import com.cgi.poc.dw.rest.dto.EventNotificationDto;
import com.google.common.collect.Sets;
import com.icegreen.greenmail.store.FolderException;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.*;

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class EventNotificationResourceIntegrationTest extends IntegrationTest {

  private static final String url = "http://localhost:%d/notification";

  private EventNotificationDto eventNotificationDto;

  private static GreenMail smtpServer;

  @BeforeClass
  public static void createAdminUser() throws SQLException {
    IntegrationTestHelper.cleanDbState();

    IntegrationTestHelper.signupAdminUser();
    IntegrationTestHelper.signupResidentUser("92106", "res101@cgi.com");
    IntegrationTestHelper.removeAllNotificationsForUser("res101@cgi.com");
    IntegrationTestHelper.addEmailNotificationForUser("res101@cgi.com");
    smtpServer = new GreenMail(new ServerSetup(3025, "127.0.0.1",
        ServerSetup.PROTOCOL_SMTP));
    smtpServer.start();
  }

  @Before
  public void createEventNotification() throws IOException {
    eventNotificationDto = new EventNotificationDto();
    eventNotificationDto.setType("ADMIN_E");
    eventNotificationDto.setDescription("flood action");
    eventNotificationDto.setZipCodes(Sets.newHashSet("92105", "92106"));
  }

  @AfterClass
  public static void cleanup() {
    IntegrationTestHelper.cleanDbState();
    if (smtpServer != null) {
      smtpServer.stop();
    }
  }
  
  @Test
  public void publishAdHocEmergencyNotification()
      throws JSONException, MessagingException, FolderException {

    smtpServer.purgeEmailFromAllMailboxes();
    String authToken = IntegrationTestHelper.getAuthToken("admin100@cgi.com", "adminpw", RULE);

    eventNotificationDto.setType("ADMIN_E");
    String description = "Tornado warning in your area.Please evacuate.";
    eventNotificationDto.setDescription(description);

    Client client = new JerseyClientBuilder().build();
    Response response = client.
        target(String.format(url, RULE.getLocalPort())).
        request().
        header("Authorization", "Bearer " + authToken).
        post(Entity.json(eventNotificationDto));

    Assert.assertEquals(200, response.getStatus());

    //verify email registration
    smtpServer.waitForIncomingEmail(1);

    MimeMessage[] receivedMails = smtpServer.getReceivedMessages();
    assertEquals( "Should have received 1 emails.", 1, receivedMails.length);

    for(MimeMessage mail : receivedMails) {
      assertTrue(GreenMailUtil.getHeaders(mail).contains("Emergency alert (ad hoc) from MyCAlerts"));
      assertTrue(GreenMailUtil.getBody(mail).contains(description));
    }
    // BCC addresses are not contained in the message since other receivers are not allowed to know 
    // the list of BCC recipients
    assertNull(receivedMails[0].getAllRecipients());
  }

  @Test
  public void publishAdHocNonEmergencyNotification()
      throws JSONException, MessagingException, FolderException {

    smtpServer.purgeEmailFromAllMailboxes();
    String authToken = IntegrationTestHelper.getAuthToken("admin100@cgi.com", "adminpw", RULE);

    eventNotificationDto.setType("ADMIN_I");
    String description = "Flood warning in your area.";
    eventNotificationDto.setDescription(description);

    Client client = new JerseyClientBuilder().build();
    Response response = client.
        target(String.format(url, RULE.getLocalPort())).
        request().
        header("Authorization", "Bearer " + authToken).
        post(Entity.json(eventNotificationDto));

    Assert.assertEquals(200, response.getStatus());

    //verify email registration
    smtpServer.waitForIncomingEmail(1);

    MimeMessage[] receivedMails = smtpServer.getReceivedMessages();
    assertEquals( "Should have received 1 emails.", 1, receivedMails.length);

    for(MimeMessage mail : receivedMails) {
      assertTrue(GreenMailUtil.getHeaders(mail).contains("Non-emergency alert (ad hoc) from MyCAlerts"));
      assertTrue(GreenMailUtil.getBody(mail).contains(description));
    }
    // BCC addresses are not contained in the message since other receivers are not allowed to know 
    // the list of BCC recipients
    assertNull(receivedMails[0].getAllRecipients());
  }

  @Test
  public void noArgument() throws JSONException {
    String authToken = IntegrationTestHelper.getAuthToken("admin100@cgi.com", "adminpw", RULE);

    Client client = new JerseyClientBuilder().build();
    Response response = client.
        target(String.format(url, RULE.getLocalPort())).
        request().
        header("Authorization", "Bearer " + authToken).
        post(null);

    Assert.assertEquals(422, response.getStatus());
    JSONObject responseJo = new JSONObject(response.readEntity(String.class));
    Assert.assertTrue(!StringUtils.isBlank(responseJo.optString("errors")));
    Assert.assertEquals("[\"The request body may not be null\"]", responseJo.optString("errors"));
  }

  @Test
  public void nullDescription() throws JSONException {
    String authToken = IntegrationTestHelper.getAuthToken("admin100@cgi.com", "adminpw", RULE);
    eventNotificationDto.setDescription(null);
    Client client = new JerseyClientBuilder().build();
    Response response = client.
        target(String.format(url, RULE.getLocalPort())).
        request().
        header("Authorization", "Bearer " + authToken).
        post(Entity.json(eventNotificationDto));

    Assert.assertEquals(422, response.getStatus());
    JSONObject responseJo = new JSONObject(response.readEntity(String.class));
    Assert.assertTrue(!StringUtils.isBlank(responseJo.optString("errors")));
    Assert.assertEquals("[\"description may not be null\"]", responseJo.optString("errors"));
  }

  @Test
  public void invalidDescription() throws JSONException {
    String authToken = IntegrationTestHelper.getAuthToken("admin100@cgi.com", "adminpw", RULE);
    eventNotificationDto.setDescription("abc");
    Client client = new JerseyClientBuilder().build();
    Response response = client.
        target(String.format(url, RULE.getLocalPort())).
        request().
        header("Authorization", "Bearer " + authToken).
        post(Entity.json(eventNotificationDto));

    Assert.assertEquals(422, response.getStatus());
    JSONObject responseJo = new JSONObject(response.readEntity(String.class));
    Assert.assertTrue(!StringUtils.isBlank(responseJo.optString("errors")));
    Assert.assertEquals("[\"description size must be between 5 and 2048\"]",
        responseJo.optString("errors"));
  }

  @Test
  public void invalidZipcode() throws JSONException {
    String authToken = IntegrationTestHelper.getAuthToken("admin100@cgi.com", "adminpw", RULE);
    eventNotificationDto.getZipCodes().add("987");
    Client client = new JerseyClientBuilder().build();
    Response response = client.
        target(String.format(url, RULE.getLocalPort())).
        request().
        header("Authorization", "Bearer " + authToken).
        post(Entity.json(eventNotificationDto));

    Assert.assertEquals(422, response.getStatus());
    JSONObject responseJo = new JSONObject(response.readEntity(String.class));
    Assert.assertTrue(!StringUtils.isBlank(responseJo.optString("errors")));
    Assert.assertEquals("[\"zipCodes invalid zipcode found.\"]",
        responseJo.optString("errors"));
  }

  @Test
  public void retrieveAllNoneExists() throws Exception {
    String authToken = IntegrationTestHelper.getAuthToken("admin100@cgi.com", "adminpw", RULE);
    IntegrationTestHelper.deleteAllEventNotfications();
    Client client = new JerseyClientBuilder().build();
    Response response = client.
        target(String.format(url+"/admin", RULE.getLocalPort())).
        request().
        header("Authorization", "Bearer " + authToken).
        get();

        Assert.assertEquals(200, response.getStatus());
        List<EventNotification> list = response.readEntity(new GenericType<List<EventNotification>>(){});
        String count = response.getHeaderString("x-total-count");
        int rows = Integer.decode(count);
        assertThat(rows).isEqualTo(0);
        assertThat(list.size()).isEqualTo(0);
  }
 
  @Test
  public void retrieveAll() throws Exception {
        EventNotification event = new EventNotification();
        event.setId(Long.valueOf(1234));
        User tmpUser = new User();
        tmpUser.setId(Long.valueOf(100));
        event.setType("FIRE");
        event.setUrl1("www.msn.com");
        event.setUrl2("www.cnn.com");
        event.setUserId(tmpUser);
        event.setDescription("CRUD TEST EVENT");
      event.setCitizensAffected(Integer.valueOf(1000));

      IntegrationTestHelper.addEventNotfication(event);
      String authToken = IntegrationTestHelper.getAuthToken("admin100@cgi.com", "adminpw", RULE);
      Client client = new JerseyClientBuilder().build();
      Response response = client.
              target(String.format(url+"/admin", RULE.getLocalPort())).
              request().
              header("Authorization", "Bearer " + authToken).
        get();

        Assert.assertEquals(200, response.getStatus());
        List<EventNotification> list = response.readEntity(new GenericType<List<EventNotification>>(){});
        String count = response.getHeaderString("x-total-count");
        int rows = Integer.decode(count);
        assertThat(rows).isEqualTo(1);
        assertThat(list.size()).isEqualTo(1);
        IntegrationTestHelper.deleteAllEventNotfications();
  }
}
