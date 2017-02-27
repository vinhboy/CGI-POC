package com.cgi.poc.dw.factory;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.isBlank;

import com.cgi.poc.dw.dao.model.User;

public class AddressBuilderImpl implements AddressBuilder {
  @Override
  public String build(User user) {
    return build(user.getAddress1(), user.getAddress2(), user.getCity(), user.getState(), user.getZipCode());
  }

  @Override
  public String build(String address1, String address2, String city, String state, String zipCode) {
    if (isBlank(address1) && isBlank(address2) && isBlank(city) && isBlank(state)){
      return zipCode;
    }
    if (isBlank(address2)) {
      return String.format("%s, %s, %s %s", ofNullable(address1).orElse(""), ofNullable(city).orElse(""), ofNullable(state).orElse(""), ofNullable(zipCode).orElse(""));
    }
    return String.format("%s, %s, %s, %s %s", ofNullable(address1).orElse(""), ofNullable(address2).orElse(""), ofNullable(city).orElse(""), ofNullable(state).orElse(""), ofNullable(zipCode).orElse(""));
  }
}
