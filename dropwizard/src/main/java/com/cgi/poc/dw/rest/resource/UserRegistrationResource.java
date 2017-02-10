package com.cgi.poc.dw.rest.resource;

import com.cgi.poc.dw.rest.model.UserRegistrationDto;
import com.cgi.poc.dw.service.UserRegistrationService;
import com.google.inject.Inject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/register")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api(value = "/register", basePath = "/")
public class UserRegistrationResource {

  @Inject
  UserRegistrationService userRegistrationService;

  @POST
  @ApiOperation(value = "User profile registration",
      notes = "Allows a user to register.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success"),
      @ApiResponse(code = 500, message = "System Error")
  })
  public Response signup(@Valid @NotNull UserRegistrationDto userRegistrationDto) {
    return userRegistrationService.registerUser(userRegistrationDto);
  }
}
