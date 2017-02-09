package com.cgi.poc.dw.rest.resource;

import com.cgi.poc.dw.dao.model.Asset;
import com.cgi.poc.dw.dao.model.User;
import com.cgi.poc.dw.rest.model.AssetDto;
import com.cgi.poc.dw.service.AssetService;
import com.google.inject.Inject;
import io.dropwizard.auth.Auth;
import io.dropwizard.jersey.params.LongParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/assets")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "/assets", basePath = "/")
public class AssetsResource {


  /*
   * Service to manipulate asset DAOs.
   */
  private AssetService assetService;

  /**
   * Constructor to initialize Service.
   *
   * @param assetService service to manipulate asset DAOs.
   */
  @Inject
  public AssetsResource(final AssetService assetService) {
    this.assetService = assetService;
  }

  @RolesAllowed("NORMAL")
  @GET
  @ApiOperation(value = "Get All Assets",
      notes = "Returns asset meta data related to the specified user.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")
  })
  public Response getAssets(@Auth User user) {
    return assetService.getAssets(user.getId());
  }

  @PermitAll
  @GET
  @Path("/{id}")
  @ApiOperation(value = "Get Asset",
      notes = "Returns asset meta data related to the specified user and asset id.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")
  })
  public Response getAsset(@PathParam("id") LongParam id, @Auth User user) {
    return assetService.getAsset(id.get(), user.getId());
  }

  @POST
  @ApiOperation(value = "Add Asset",
      notes = "Adds asset meta data related to the specified user.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")
  })
  public Response addAsset(@Valid @NotNull AssetDto assetDto, @Auth User user) {
    Asset asset = new Asset(assetDto.getUrl(), assetDto.getDescription(), user);
    return assetService.addAsset(asset);
  }


  @DELETE
  @Path("/{id}")
  @ApiOperation(value = "Remove Asset",
      notes = "Removed asset data or throws an exception if the asset with the id provided was not found")
  @ApiResponses(value = {
      @ApiResponse(code = 204, message = "Success"),
      @ApiResponse(code = 404, message = "Not Found"),
      @ApiResponse(code = 409, message = "Conflict")
  })
  @ApiImplicitParams({
      @ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")
  })
  public Response deleteAsset(@PathParam("id") LongParam id, @Auth User user) {
    return assetService.deleteAsset(id.get(), user.getId());
  }
}
