package com.cgi.poc.dw.auth.service;

import org.jose4j.jwt.consumer.InvalidJwtException;

public interface JwtReaderService {

  String getEmailFromJwt(String token) throws InvalidJwtException;
}
