package com.cgi.poc.dw.service.impl;

import com.cgi.poc.dw.dao.AssetDao;
import com.cgi.poc.dw.dao.model.Asset;
import com.cgi.poc.dw.rest.model.error.ErrorMessage;
import com.cgi.poc.dw.rest.model.error.ErrorMessageWebException;
import com.cgi.poc.dw.service.AssetService;
import com.google.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AssetServiceImpl implements AssetService {

  private final static Logger LOG = LoggerFactory.getLogger(AssetServiceImpl.class);

  private AssetDao assetDao;

  @Inject
  public AssetServiceImpl(AssetDao assetDao) {
    this.assetDao = assetDao;
  }

  @Override
  public Response getAssets(long id) {
    return Response.ok().entity(assetDao.findByUserId(id)).build();
  }

  @Override
  public Response getAsset(long id, long userId) {
    return Response.ok().entity(assetDao.findByIdAndUserId(id, userId)).build();
  }

  @Override
  public Response addAsset(Asset asset) {
    try {
      assetDao.createAsset(asset);
    } catch (Exception exception) {
      LOG.error("Unable to create asset.", exception);
      throw new ErrorMessageWebException(ErrorMessage.ASSET_FAIL_ASSET_ALREADY_EXISTS);
    }
    return Response.ok().build();
  }

  @Override
  public Response deleteAsset(long id, long userId) {

    findAssetOrTrowException(id, userId);
    try {
      assetDao.deleteAsset(id);
    } catch (Exception exception) {
      LOG.error("Unable to delete asset.", exception);
      throw new ErrorMessageWebException(ErrorMessage.ASSET_FAIL_ASSET_ALREADY_EXISTS);
    }
    return Response.status(Status.NO_CONTENT).build();
  }

  /**
   * Method looks for a asset by id and User id and returns the asset or
   * throws NotFoundException otherwise.
   *
   * @param id the id of a asset.
   * @param userId the id of the owner.
   * @return Asset
   */
  private Asset findAssetOrTrowException(long id, long userId) {
    Asset asset = assetDao.findByIdAndUserId(
        id, userId
    ).orElseThrow(()
        -> new NotFoundException("The requested asset was not found."));
    return asset;
  }

}
