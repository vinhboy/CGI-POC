package com.cgi.poc.dw.dao.mapper;

import com.cgi.poc.dw.dao.model.Asset;
import com.cgi.poc.dw.dao.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class AssetMapper implements ResultSetMapper<Optional<Asset>> {

  public Optional<Asset> map(int index, ResultSet r, StatementContext ctx) throws SQLException {
    Asset asset = new Asset();
    asset.setId(r.getLong("id"));
    asset.setUrl(r.getString("url"));
    asset.setDescription(r.getString("description"));

    User user = new User();
    user.setId(r.getLong("user_id"));

    asset.setUser(user);
    return Optional.ofNullable(asset);
  }
}
