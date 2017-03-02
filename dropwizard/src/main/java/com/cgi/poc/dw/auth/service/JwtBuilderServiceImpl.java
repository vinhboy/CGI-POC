package com.cgi.poc.dw.auth.service;

import com.cgi.poc.dw.auth.data.Keys;
import com.cgi.poc.dw.dao.model.User;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.lang.JoseException;

public class JwtBuilderServiceImpl implements JwtBuilderService {

  private final Keys keys;
  
  private Integer jwtExpiryInMinutes;

  @Inject
  public JwtBuilderServiceImpl(Keys keys, @Named("jwtExpiryInMinutes") Integer jwtExpiryInMinutes) {
    this.keys = keys;
    this.jwtExpiryInMinutes = jwtExpiryInMinutes;
  }

  public String createJwt(User user) throws JoseException {
    JwtClaims claims = new JwtClaims();
    claims.setIssuer("https://www.mycalerts.com");
    claims.setSubject(user.getEmail());
    claims.setExpirationTimeMinutesInTheFuture(jwtExpiryInMinutes);
    claims.setIssuedAtToNow();
    claims.setClaim("email", user.getEmail());
    claims.setClaim("role", user.getRole());
    JsonWebSignature jws = new JsonWebSignature();
    jws.setPayload(claims.toJson());
    jws.setKey(keys.getSignatureKey());
    jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);
    jws.setHeader("typ", "JWT");

    String jwt = jws.getCompactSerialization();
    return jwt;
  }
}
