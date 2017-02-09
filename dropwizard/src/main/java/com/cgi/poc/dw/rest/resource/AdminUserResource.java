package com.cgi.poc.dw.rest.resource;

import com.cgi.poc.dw.dao.model.User;
import com.cgi.poc.dw.service.AdminUserService;
import com.google.inject.Inject;
import io.dropwizard.auth.Auth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RolesAllowed("ADMIN")
@Path("admin/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api(value = "admin/user", basePath = "/")
public class AdminUserResource {

  @Inject
  AdminUserService adminUserService;

  @GET
  @ApiOperation(value = "Admin ",
      notes = "Returns all users with NORMAL role.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")
  })
  public Response getUsers(@Auth User principal) {
    return adminUserService.getUsers(principal);
  }
}
