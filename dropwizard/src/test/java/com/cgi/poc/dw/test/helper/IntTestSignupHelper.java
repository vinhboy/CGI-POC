package com.cgi.poc.dw.test.helper;

import com.cgi.poc.dw.CgiPocConfiguration;
import com.cgi.poc.dw.rest.model.SignupUserDto;
import io.dropwizard.testing.junit.DropwizardAppRule;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.Assert;

public class IntTestSignupHelper {

  public static void signupUser(String email, String password,
      DropwizardAppRule<CgiPocConfiguration> RULE) {
    Client client = new JerseyClientBuilder().build();
    SignupUserDto signupUserDto = new SignupUserDto();
    signupUserDto.setEmail(email);
    signupUserDto.setPassword(password);
    Response response = client
        .target(String.format("http://localhost:%d/signup", RULE.getLocalPort())).request()
        .post(Entity.json(signupUserDto));
    Assert.assertEquals(200, response.getStatus());
  }
}
