package com.cgi.poc.dw.service;

import com.cgi.poc.dw.MapApiConfiguration;
import com.cgi.poc.dw.auth.service.PasswordHash;
import com.cgi.poc.dw.dao.UserDao;
import com.cgi.poc.dw.dao.model.User;
import com.cgi.poc.dw.util.*;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Validator;
import javax.validation.groups.Default;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

public class UserServiceImpl extends BaseServiceImpl implements UserService {

	private final static Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

	private final UserDao userDao;

	private final PasswordHash passwordHash;

	private Client client;

	private MapApiConfiguration mapApiConfiguration;

	private final EmailService emailService;

	private final TextMessageService textMessageService;

	// The name of the query param for the
	private static final String ADDRESS = "address";

	@Inject
	public UserServiceImpl(MapApiConfiguration mapApiConfiguration, UserDao userDao, PasswordHash passwordHash,
			Validator validator, Client client, EmailService emailService, TextMessageService textMessageService) {
		super(validator);
		this.userDao = userDao;
		this.passwordHash = passwordHash;
		this.client = client;
		this.mapApiConfiguration = mapApiConfiguration;
		this.emailService = emailService;
		this.textMessageService = textMessageService;
	}

	public Response registerUser(User user) {
		// Defaulting the user to RESIDENT
		user.setRole("RESIDENT");

		validate(user, "rest", RestValidationGroup.class, Default.class);
		// check if the email already exists.
		User findUserByEmail = userDao.findUserByEmail(user.getEmail());
		if (findUserByEmail != null) {
			ErrorInfo errRet = new ErrorInfo();
			String errorString = "A profile already exists for that email address. Please register using a different email.";
			errRet.addError(GeneralErrors.DUPLICATE_ENTRY.getCode(), errorString);
			return Response.noContent().status(Response.Status.BAD_REQUEST).entity(errRet).build();
		}

		return processForSave(user, false, false);
	}

	private void saveUser(User user, boolean registered) {
		validate(user, "save", Default.class, PersistValidationGroup.class);


		userDao.save(user);
		if (!registered) {
			// Future TODO enhancement: make the subject and email body configurable
			emailService.send(null, Arrays.asList(user.getEmail()), "Registration confirmation",
					"Hello there, thank you for registering.");
			textMessageService.send(user.getPhone(), "MyCAlerts: Thank you for registering.");
		}
	}

	private void createPasswordHash(User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
	}

	// invoke Google Maps API to retrieve latitude and longitude by zipCode
	private void setUserGeoCoordinates(User user) throws JsonParseException, JsonMappingException, IOException {
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
	}

	private ErrorInfo getInternalErrorInfo(Exception exception, GeneralErrors generalErrors) {
		ErrorInfo errRet = new ErrorInfo();
		String message = generalErrors.getMessage();
		String exMsg = "";
		if (exception.getMessage() != null) {
			exMsg = exception.getMessage();
		}
		String errorString = message.replace("REPLACE1", exception.getClass().getCanonicalName()).replace("REPLACE2",
				exMsg);
		errRet.addError(generalErrors.getCode(), errorString);
		return errRet;
	}

	public Response updateUser(User user, User modifiedUser) {
		//If user password is empty keep same password.
		boolean keepPassword = false;
		if(StringUtils.isBlank(modifiedUser.getPassword())){
			modifiedUser.setPassword(user.getPassword());
			keepPassword = true;
		}
		else {
			validate(modifiedUser, "update", LoginValidationGroup.class);
		}
		modifiedUser.setId(user.getId());
		modifiedUser.setRole(user.getRole());
		return processForSave(modifiedUser, true, keepPassword);
	}

	private Response processForSave(User user, boolean registered, boolean keepPassword){
		Response response = null;
		try {
			if (!keepPassword) {
				String hash = passwordHash.createHash(user.getPassword());
				user.setPassword(hash);
			}
			setUserGeoCoordinates(user);
			saveUser(user, registered);
			response = Response.ok().entity(user).build();
		} catch (Exception exception) {
			LOG.error("Unable to save a user.", exception);
			ErrorInfo errRet = getInternalErrorInfo(exception, GeneralErrors.UNKNOWN_EXCEPTION);
			response = Response.noContent().status(Status.INTERNAL_SERVER_ERROR).entity(errRet).build();
		}
		return response;
	}

}
