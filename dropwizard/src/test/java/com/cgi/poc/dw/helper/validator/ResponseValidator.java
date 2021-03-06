package com.cgi.poc.dw.helper.validator;

import javax.ws.rs.core.Response;
import org.junit.Assert;

public class ResponseValidator {

  public static void validate(Response response, int status, String msg) {
    Assert.assertEquals(status, response.getStatus());
    Assert.assertEquals(msg, response.readEntity(String.class));
  }
}
