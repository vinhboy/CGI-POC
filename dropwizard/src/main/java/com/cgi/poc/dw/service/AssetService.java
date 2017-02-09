package com.cgi.poc.dw.service;

import com.cgi.poc.dw.dao.model.Asset;
import javax.ws.rs.core.Response;

public interface AssetService {

  Response getAssets(long id);

  Response getAsset(long id, long userId);

  Response addAsset(Asset asset);

  Response deleteAsset(long id, long userId);
}
