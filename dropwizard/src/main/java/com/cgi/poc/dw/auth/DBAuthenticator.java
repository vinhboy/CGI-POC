package com.cgi.poc.dw.auth;

import com.cgi.poc.dw.auth.service.JwtReaderService;
import com.cgi.poc.dw.dao.UserDao;
import com.cgi.poc.dw.dao.model.User;
import com.google.inject.Inject;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.context.internal.ManagedSessionContext;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for authenticating users using backing database.
 */
public class DBAuthenticator implements Authenticator<JwtContext, User> {

  private final static Logger LOG = LoggerFactory.getLogger(DBAuthenticator.class);

  
  UserDao userDao;

  /**
   * Hibernate session factory; Necessary for the authenticate method to work,
   * which doesn't work as described in the documentation.
   */
  private final SessionFactory sessionFactory;

  JwtReaderService jwtReaderService;
  
  @Inject
  public DBAuthenticator(UserDao userDao, SessionFactory sessionFactory, JwtReaderService jwtReaderService) {
    this.userDao = userDao;
    this.sessionFactory = sessionFactory;
    this.jwtReaderService = jwtReaderService;
  }


  @Override
  public Optional<User> authenticate(JwtContext context) throws AuthenticationException {
    Session session = sessionFactory.openSession();

    String email = "";
    try {
      ManagedSessionContext.bind(session);

      email = jwtReaderService.getEmailFromJwt(context.getJwt());
      if (StringUtils.isBlank(email)) {
        LOG.error("Email is blank.");
        return Optional.empty();
      } else {
        User user = userDao.findUserByEmail(email);
        return Optional.ofNullable(user);
      }
    } catch (Exception e) {
      throw new AuthenticationException(e);
    }
    finally {
      ManagedSessionContext.unbind(sessionFactory);
      session.close();
    }
    
  }
}
