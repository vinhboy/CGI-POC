package com.cgi.poc.dw.dao.impl;

import com.cgi.poc.dw.dao.AssetDao;
import com.cgi.poc.dw.dao.mapper.AssetMapper;
import com.cgi.poc.dw.dao.model.Asset;
import java.util.List;
import java.util.Optional;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

//Uncomment @LogSqlFactory to enable SQL debugging
//@LogSqlFactory
@RegisterMapper(AssetMapper.class)
public interface AssetDaoImpl extends AssetDao {

  @Override
  @SqlUpdate("INSERT INTO assets (url, description, user_id) values (:url, :description, :userId)")
  void createAsset(@BindBean Asset asset) throws Exception;

  @Override
  @SqlQuery("SELECT * FROM assets WHERE user_id = :userId")
  List<Asset> findByUserId(@Bind("userId") long id);

  @Override
  @SqlQuery("SELECT * FROM assets WHERE id = :id AND user_id = :userId")
  Optional<Asset> findByIdAndUserId(@Bind("id") long id, @Bind("userId") long userId);

  @Override
  @SqlUpdate("DELETE FROM assets WHERE id = :id")
  void deleteAsset(@Bind("id") long id);
}
