package com.cgi.poc.dw.rest.resource;

import com.cgi.poc.dw.rest.model.SignupUserDto;
import com.cgi.poc.dw.service.SignupService;
import com.google.inject.Inject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/signup")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api(value = "/signup", basePath = "/")
public class SignupResource {

  @Inject
  SignupService signupService;

  @POST
  @ApiOperation(value = "User Sign-up",
      notes = "Allows a user to sign-up")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success"),
      @ApiResponse(code = 500, message = "System Error")
  })
  public Response signup(SignupUserDto signupUserDto) {
    return signupService.signup(signupUserDto);
  }
}
