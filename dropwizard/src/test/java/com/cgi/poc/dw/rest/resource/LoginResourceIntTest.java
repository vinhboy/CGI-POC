package com.cgi.poc.dw.rest.resource;

import com.cgi.poc.dw.auth.model.Role;
import com.cgi.poc.dw.rest.model.LoginUserDto;
import com.cgi.poc.dw.rest.model.SignupUserDto;
import com.cgi.poc.dw.rest.model.error.ErrorMessage;
import com.cgi.poc.dw.test.IntegrationTest;
import com.cgi.poc.dw.test.validator.ResponseValidator;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class LoginResourceIntTest extends IntegrationTest {

  private static final String url = "http://localhost:%d/login";

  @Test
  public void noArgument() {
    Client client = new JerseyClientBuilder().build();
    Response response = client.target(String.format(url, RULE.getLocalPort())).request().post(null);
    ResponseValidator.validate(response, 400, ErrorMessage.LOGIN_FAIL_NO_CREDENTIALS.toString());
  }

  @Test
  public void noEmail() {
    Client client = new JerseyClientBuilder().build();
    LoginUserDto loginUserDto = new LoginUserDto();
    loginUserDto.setPassword("test123");
    Response response = client.target(String.format(url, RULE.getLocalPort())).request()
        .post(Entity.json(loginUserDto));
    ResponseValidator.validate(response, 400, ErrorMessage.LOGIN_FAIL_NO_EMAIL.toString());
  }

  @Test
  public void noPassword() {
    Client client = new JerseyClientBuilder().build();
    LoginUserDto loginUserDto = new LoginUserDto();
    loginUserDto.setEmail("test@gmail.com");
    Response response = client.target(String.format(url, RULE.getLocalPort())).request()
        .post(Entity.json(loginUserDto));
    ResponseValidator.validate(response, 400, ErrorMessage.LOGIN_FAIL_NO_PASSWORD.toString());
  }

  @Test
  public void userNotFound() {
    Client client = new JerseyClientBuilder().build();
    LoginUserDto loginUserDto = new LoginUserDto();
    loginUserDto.setEmail("user.not.found@gmail.com");
    loginUserDto.setPassword("test123");
    Response response = client.target(String.format(url, RULE.getLocalPort())).request()
        .post(Entity.json(loginUserDto));
    ResponseValidator.validate(response, 401,
        ErrorMessage.LOGIN_FAIL_USER_NOT_FOUND_OR_WRONG_PASSWORD.toString());
  }

  @Test
  public void signupUserLoginUserSuccess() throws JSONException {
    Client client = new JerseyClientBuilder().build();
    // signup user
    SignupUserDto signupUserDto = new SignupUserDto();
    signupUserDto.setEmail("success@gmail.com");
    signupUserDto.setPassword("test123");
    client.target(String.format("http://localhost:%d/signup", RULE.getLocalPort())).request()
        .post(Entity.json(signupUserDto));
    // login user
    LoginUserDto loginUserDto = new LoginUserDto();
    loginUserDto.setEmail("success@gmail.com");
    loginUserDto.setPassword("test123");
    Response response = client.target(String.format(url, RULE.getLocalPort())).request()
        .post(Entity.json(loginUserDto));
    Assert.assertEquals(200, response.getStatus());
    JSONObject responseJo = new JSONObject(response.readEntity(String.class));
    Assert.assertTrue(!StringUtils.isBlank(responseJo.optString("authToken")));
    Assert.assertEquals(Role.NORMAL.toString(), responseJo.optString("role"));
  }

  @Test
  public void loginAdminSuccess() throws JSONException {
    Client client = new JerseyClientBuilder().build();
    LoginUserDto loginUserDto = new LoginUserDto();
    loginUserDto.setEmail("admin@test.io");
    loginUserDto.setPassword("adminpw");
    Response response = client.target(String.format(url, RULE.getLocalPort())).request()
        .post(Entity.json(loginUserDto));
    Assert.assertEquals(200, response.getStatus());
    JSONObject responseJo = new JSONObject(response.readEntity(String.class));
    Assert.assertTrue(!StringUtils.isBlank(responseJo.optString("authToken")));
    Assert.assertEquals(Role.ADMIN.toString(), responseJo.optString("role"));
  }

  @Test
  public void loginFailureWrongPassword() {
    Client client = new JerseyClientBuilder().build();
    // signup user
    SignupUserDto signupUserDto = new SignupUserDto();
    signupUserDto.setEmail("wrong.pw@gmail.com");
    signupUserDto.setPassword("test123");
    client.target(String.format("http://localhost:%d/signup", RULE.getLocalPort())).request()
        .post(Entity.json(signupUserDto));
    // login user
    LoginUserDto loginUserDto = new LoginUserDto();
    loginUserDto.setEmail("wrong.pw@gmail.com");
    loginUserDto.setPassword("wrong");
    Response response = client.target(String.format(url, RULE.getLocalPort())).request()
        .post(Entity.json(loginUserDto));
    ResponseValidator.validate(response, 401,
        ErrorMessage.LOGIN_FAIL_USER_NOT_FOUND_OR_WRONG_PASSWORD.toString());
  }
}
