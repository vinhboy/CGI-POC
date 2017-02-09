package com.cgi.poc.dw.auth.service;

import com.cgi.poc.dw.dao.model.User;
import org.jose4j.lang.JoseException;

public interface JwtBuilderService {

  String createJwt(User user) throws JoseException;
}
