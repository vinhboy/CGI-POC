package com.cgi.poc.dw.helper;

import com.cgi.poc.dw.CgiPocConfiguration;
import com.cgi.poc.dw.rest.model.LoginUserDto;
import io.dropwizard.testing.junit.DropwizardAppRule;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;

public class IntegrationTestLoginHelper {

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
}
