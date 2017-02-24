package com.cgi.poc.dw.rest.resource;

import com.cgi.poc.dw.dao.model.User;
import com.cgi.poc.dw.service.UserService;
import com.cgi.poc.dw.service.UserServiceImpl;
import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;

import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.*;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api(value = "/user", basePath = "/")
public class UserResource {

	private final static Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

	@Inject
	private UserService userService;

	@GET
	@UnitOfWork
	@ApiOperation(value = "Retrieve user profile registration", notes = "Allows a user to retrieve registration information.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "System Error") })
	@ApiImplicitParams({
					@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")
	})
	@Timed(name = "User.get")
	public Response retrieve(@Auth User user) {
		return Response.ok().entity(user).build();
	}

	@POST
	@UnitOfWork
	@ApiOperation(value = "User profile registration", notes = "Allows a user to register.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "System Error") })
	@Timed(name = "User.save")
	public Response signup(@NotNull User user) {
		Response response = userService.registerUser(user);
		if (!response.getStatusInfo().getFamily().equals(Response.Status.Family.SUCCESSFUL)) {
			throw new WebApplicationException(response);
		}
		return response;
	}

	@PUT
	@UnitOfWork
	@ApiOperation(value = "User profile update", notes = "Allows a user to update.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "System Error") })
	@ApiImplicitParams({
					@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")
	})
	@Timed(name = "User.save")
	public Response updateProfile(@Auth User user, @NotNull User modifiedUser) {
		Response response = userService.updateUser(user, modifiedUser);
		if (!response.getStatusInfo().getFamily().equals(Response.Status.Family.SUCCESSFUL)) {
			throw new WebApplicationException(response);
		}
		return response;
	}
}
