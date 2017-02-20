package com.cgi.poc.dw.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.groups.Default;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cgi.poc.dw.MapApiConfiguration;
import com.cgi.poc.dw.auth.service.PasswordHash;
import com.cgi.poc.dw.dao.UserDao;
import com.cgi.poc.dw.dao.model.User;
import com.cgi.poc.dw.dao.model.UserNotificationType;
import com.cgi.poc.dw.util.ErrorInfo;
import com.cgi.poc.dw.util.GeneralErrors;
import com.cgi.poc.dw.util.PasswordValidationGroup;
import com.cgi.poc.dw.util.PersistValidationGroup;
import com.cgi.poc.dw.util.RestValidationGroup;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;

/**
 * @author felix.r.pelletier
 */
public class UpdateProfileServiceImpl extends BaseServiceImpl implements UpdateProfileService {

	private final static Logger LOG = LoggerFactory.getLogger(UpdateProfileServiceImpl.class);

	private final UserDao userDao;

	private final PasswordHash passwordHash;

	private Client client;

	private MapApiConfiguration mapApiConfiguration;

	// Not currently used, could be changed to confirm account changes
	@SuppressWarnings("unused")
	private final EmailService emailService;

	// The name of the query param for the
	private static final String ADDRESS = "address";

	@Inject
	public UpdateProfileServiceImpl(MapApiConfiguration mapApiConfiguration, UserDao userDao, PasswordHash passwordHash,
			Validator validator, Client client, EmailService emailService) {
		super(validator);
		this.userDao = userDao;
		this.passwordHash = passwordHash;
		this.client = client;
		this.mapApiConfiguration = mapApiConfiguration;
		this.emailService = emailService;
	}

	/**
	 * Updates and saves modified user info to the database
	 *
	 * @param modifiedUser
	 * @return response
	 */
	public Response updateUser(User modifiedUser) {

		// check if the email already exists.
		if(modifiedUser == null || modifiedUser.getPassword() != ""){
			validate(modifiedUser, "rest", PasswordValidationGroup.class);
		}
		User currentUser = userDao.findUserByEmail(modifiedUser.getEmail());
		if (currentUser != null) {
			try {
				currentUser = updateCurrentFields(modifiedUser, currentUser);
			} catch (Exception exception) {
				LOG.error("Unable to create a password hash.", exception);
				ErrorInfo errRet = getInternalErrorInfo(exception, GeneralErrors.UNKNOWN_EXCEPTION);
				return Response.noContent().status(Status.INTERNAL_SERVER_ERROR).entity(errRet).build();
			}
		} else {
			ErrorInfo errRet = new ErrorInfo();
			String errorString = GeneralErrors.INVALID_INPUT.getMessage().replace("REPLACE", "user not found");
			errRet.addError(GeneralErrors.INVALID_INPUT.getCode(), errorString);
			return Response.noContent().status(Response.Status.BAD_REQUEST).entity(errRet).build();
		}
		
		validate(currentUser, "rest", RestValidationGroup.class, Default.class);

		String hash = null;
		try {
			hash = passwordHash.createHash(currentUser.getPassword());
			currentUser.setPassword(hash);
		} catch (Exception exception) {
			LOG.error("Unable to create a password hash.", exception);
			ErrorInfo errRet = getInternalErrorInfo(exception, GeneralErrors.UNKNOWN_EXCEPTION);
			return Response.noContent().status(Status.INTERNAL_SERVER_ERROR).entity(errRet).build();
		}

		setUserGeoCoordinates(currentUser);

		try {
			validate(currentUser, "save", Default.class, PersistValidationGroup.class);
			for (UserNotificationType notificationType : currentUser.getNotificationType()) {
				notificationType.setUserId(currentUser);
			}

			userDao.save(currentUser);

		} catch (ConstraintViolationException exception) {
			throw exception;
		} catch (Exception exception) {
			LOG.error("Unable to update a user.", exception);
			ErrorInfo errRet = getInternalErrorInfo(exception, GeneralErrors.UNKNOWN_EXCEPTION);
			return Response.noContent().status(Status.INTERNAL_SERVER_ERROR).entity(errRet).build();
		}
		return Response.ok().entity(currentUser).build();
	}

	/**
	 * Checks and updates fields that have been changed
	 * 
	 * @param modifiedUser
	 * @param currentUser
	 * @return updatedUser
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 */
	private User updateCurrentFields(User modifiedUser, User currentUser) throws NoSuchAlgorithmException, InvalidKeySpecException {
		User updatedUser = currentUser;
		if (!modifiedUser.getFirstName().equals(currentUser.getFirstName())) {
			updatedUser.setFirstName(modifiedUser.getFirstName());
		}
		if (!modifiedUser.getLastName().equals(currentUser.getLastName())) {
			updatedUser.setLastName(modifiedUser.getLastName());
		}
		if (modifiedUser.getPassword() != "") {
			if(!passwordHash.createHash(modifiedUser.getPassword()).equals(currentUser.getPassword())){
				updatedUser.setPassword(modifiedUser.getPassword());
			}
		}
		if (modifiedUser.getPhone() != "" && !modifiedUser.getPhone().equals(currentUser.getPhone())) {
			updatedUser.setPhone(modifiedUser.getPhone());
		}
		if (modifiedUser.getRequiredStreet() != ""
				&& !modifiedUser.getRequiredStreet().equals(currentUser.getRequiredStreet())) {
			updatedUser.setRequiredStreet(modifiedUser.getRequiredStreet());
		}
		if (modifiedUser.getOptionalStreet() != ""
				&& !modifiedUser.getOptionalStreet().equals(currentUser.getOptionalStreet())) {
			updatedUser.setOptionalStreet(modifiedUser.getOptionalStreet());
		}
		if (modifiedUser.getCity() != "" && !modifiedUser.getCity().equals(currentUser.getCity())) {
			updatedUser.setCity(modifiedUser.getCity());
		}
		if (modifiedUser.getState() != "" && !modifiedUser.getState().equals(currentUser.getState())) {
			updatedUser.setState(modifiedUser.getState());
		}
		if (modifiedUser.getZipCode() != "" && !modifiedUser.getZipCode().equals(currentUser.getZipCode())) {
			updatedUser.setZipCode(modifiedUser.getZipCode());
		}
		if (modifiedUser.getState() != "" && !modifiedUser.equals(currentUser.getState())) {
			updatedUser.setState(modifiedUser.getState());
		}
		if (modifiedUser.isAllowPhoneLocalization() != currentUser.isAllowPhoneLocalization()) {
			updatedUser.setAllowPhoneLocalization(modifiedUser.isAllowPhoneLocalization());
		}
		
		return updatedUser;
	}

	// invoke Google Maps API to retrieve latitude and longitude by zipCode
	private void setUserGeoCoordinates(User user) {
		try {
			String response = client.target(mapApiConfiguration.getApiURL()).queryParam(ADDRESS, user.getZipCode())
					.request(MediaType.APPLICATION_JSON).get(String.class);

			final ObjectNode node = new ObjectMapper().readValue(response, ObjectNode.class);

			if (node.path("results").size() > 0 && "OK".equals(node.path("status").asText())) {
				user.setLatitude(node.get("results").get(0).get("geometry").get("location").get("lat").asDouble());
				user.setLongitude(node.get("results").get(0).get("geometry").get("location").get("lng").asDouble());
			} else {
				user.setLatitude(0.0);
				user.setLongitude(0.0);
			}
		} catch (Exception exception) {
			LOG.error("Unable to make maps api call.", exception);
			ErrorInfo errRet = getInternalErrorInfo(exception, GeneralErrors.UNKNOWN_EXCEPTION);
			throw new WebApplicationException(
					Response.noContent().status(Status.INTERNAL_SERVER_ERROR).entity(errRet).build());
		}
	}

	private ErrorInfo getInternalErrorInfo(Exception exception, GeneralErrors generalErrors) {
		ErrorInfo errRet = new ErrorInfo();
		String message = generalErrors.getMessage();
		String errorString = "";
		if(exception.getMessage() != null){
		errorString = message.replace("REPLACE1", exception.getClass().getCanonicalName()).replace("REPLACE2",
				exception.getMessage());
		}else{
			errorString = message.replace("REPLACE1", exception.getClass().getCanonicalName()).replace("REPLACE2",
					"");
		}
		errRet.addError(generalErrors.getCode(), errorString);
		return errRet;
	}
}
