package com.cgi.poc.dw.rest.resource;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cgi.poc.dw.dao.model.User;
import com.cgi.poc.dw.service.UpdateProfileService;
import com.cgi.poc.dw.service.UpdateProfileServiceImpl;
import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;

import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/updateProfile")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api(value = "/updateProfile", basePath = "/")
public class UpdateProfileResource {

  @SuppressWarnings("unused")
  private final static Logger LOG = LoggerFactory.getLogger(UpdateProfileServiceImpl.class);

  @Inject
  UpdateProfileService updateProfileService;

  @POST
  @UnitOfWork
  @ApiOperation(value = "User profile update",
      notes = "Allows a user to update.")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Success"),
      @ApiResponse(code = 500, message = "System Error")
  })
  @Timed(name = "User.save")
  public Response updateProfile(@Auth User user, @NotNull User userDto) {
      Response response = updateProfileService.updateUser(userDto);
      if (!response.getStatusInfo().getFamily().equals(Response.Status.Family.SUCCESSFUL)) {
                throw new WebApplicationException(response);
      }
    return response;
  }
} 




