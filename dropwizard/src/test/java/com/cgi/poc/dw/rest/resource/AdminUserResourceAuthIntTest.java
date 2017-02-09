package com.cgi.poc.dw.rest.resource;

import com.cgi.poc.dw.test.IntegrationTest;
import com.cgi.poc.dw.test.helper.IntTestLoginHelper;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.Assert;
import org.junit.Test;

public class AdminUserResourceAuthIntTest extends IntegrationTest {

  private static final String url = "http://localhost:%d/admin/user";

  @Test
  public void needAuthToken() {
    Client client = new JerseyClientBuilder().build();
    Response response = client.target(String.format(url, RULE.getLocalPort())).request().get();
    Assert.assertEquals(401, response.getStatus());
  }

  @Test
  public void needValidAuthToken() {
    Client client = new JerseyClientBuilder().build();
    Response response = client.target(String.format(url, RULE.getLocalPort())).request()
        .header("Authorization", "Bearer " + "abc").get();
    Assert.assertEquals(401, response.getStatus());
  }

  @Test
  public void getUsersSuccess() {
    String authToken = IntTestLoginHelper.getAuthToken("admin@test.io", "adminpw", RULE);
    Client client = new JerseyClientBuilder().build();
    Response response = client.target(String.format(url, RULE.getLocalPort())).request()
        .header("Authorization", "Bearer " + authToken).get();
    Assert.assertEquals(200, response.getStatus());
    Assert.assertEquals("[]", response.readEntity(String.class));
  }
}
