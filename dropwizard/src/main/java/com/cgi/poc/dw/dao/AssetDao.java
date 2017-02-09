package com.cgi.poc.dw.dao;

import com.cgi.poc.dw.dao.model.Asset;
import java.util.List;
import java.util.Optional;
import org.skife.jdbi.v2.sqlobject.Bind;

public interface AssetDao {

  void createAsset(Asset asset) throws Exception;

  /**
   * Find assets for a particular user.
   *
   * @param id the id of the user.
   * @return List of all assets stored by the user identified by id.
   */
  List<Asset> findByUserId(long id);

  /**
   * Method looks for the asset characterized by id for a user
   * characterized by userId.
   *
   * @param id the id of a asset to look for.
   * @param userId the id of the user owner of the asset.
   * @return Optional containing the asset or an empty Optional if the asset was not found.
   */
  Optional<Asset> findByIdAndUserId(long id, long userId);

  /**
   * Method removes the asset from the database.
   *
   * @param id the id of the asset to be deleted.
   */
  void deleteAsset(@Bind("id") long id);

}
