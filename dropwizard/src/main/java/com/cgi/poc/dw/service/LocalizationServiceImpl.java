package com.cgi.poc.dw.service;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cgi.poc.dw.dao.UserDao;
import com.cgi.poc.dw.dao.model.User;
import com.cgi.poc.dw.util.ErrorInfo;
import com.cgi.poc.dw.util.GeneralErrors;
import com.google.inject.Inject;

import io.dropwizard.hibernate.UnitOfWork;

public class LocalizationServiceImpl implements LocalizationService{
	
	  private final static Logger LOG = LoggerFactory.getLogger(LocalizationServiceImpl.class);

	  private final UserDao userDao;
	  
	  @Inject
	  public LocalizationServiceImpl(UserDao userDao) {
	    this.userDao = userDao;
	  }
	  
	  /**
	   * Set the geo localization coordinates for a user.
	   * 
	   * @param email the user email
	   * @param latitude the geo localization latitude
	   * @param longitude the geo localization longitude
	   * @return response the status response 
	   */
	  public Response setLocalization(User user){
		  
		  if(userDao.findUserByEmail(user.getEmail()) != null){
		  
			  if(user.getGeoLocLongitude() != null && user.getGeoLocLatitude() != null){
				  
				  userDao.save(user);
			  }else{
				  LOG.error("User not find in data base or empty");
			      ErrorInfo errRet = new ErrorInfo();
			      String errorString = GeneralErrors.INVALID_INPUT.getMessage().replace("REPLACE", "geolocalization");
			      errRet.addError(GeneralErrors.INVALID_INPUT.getCode(), errorString);
			      return Response.noContent().status(Response.Status.BAD_REQUEST).entity(errRet).build();
			  }
		  }else{
			  LOG.error("User not find in data base or empty");
		      ErrorInfo errRet = new ErrorInfo();
		      String errorString = GeneralErrors.INVALID_INPUT.getMessage().replace("REPLACE", "email");
		      errRet.addError(GeneralErrors.INVALID_INPUT.getCode(), errorString);
		      return Response.noContent().status(Response.Status.BAD_REQUEST).entity(errRet).build();
		  }
		  
		  return Response.ok().build();
	  }
}
