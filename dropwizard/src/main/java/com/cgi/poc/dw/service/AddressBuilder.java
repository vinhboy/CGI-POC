package com.cgi.poc.dw.service;

import com.cgi.poc.dw.dao.model.User;

public interface AddressBuilder {
  String build(User user);
  String build(String address1, String address2, String city, String state, String zipCode);
}
