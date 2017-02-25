package com.cgi.poc.dw.service;

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
      return String.format("%s, %s, %s %s", address1, city, state, zipCode);
    }
    return String.format("%s, %s, %s, %s %s", address1, address2, city, state, zipCode);
  }
}
