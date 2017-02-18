package com.cgi.poc.dw.rest.resource;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionImpl;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cgi.poc.dw.auth.model.Role;
import com.cgi.poc.dw.dao.HibernateUtil;
import com.cgi.poc.dw.dao.model.NotificationType;
import com.cgi.poc.dw.dao.model.User;
import com.cgi.poc.dw.dao.model.UserNotification;
import com.cgi.poc.dw.helper.IntegrationTest;
import com.cgi.poc.dw.rest.model.LoginUserDto;
import com.cgi.poc.dw.util.ErrorInfo;
import com.cgi.poc.dw.util.GeneralErrors;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;

public class LocalizationResourceIntegrationTest extends IntegrationTest {

	private String authToken;

	private GreenMail smtpServer;

	private static final String PASSWORD = "test123";

	private static final String USERNAME = "success@gmail.com";

	private static final String url = "http://localhost:%d/localize";

	private static User tstUser = new User(1L, "john", "smith", USERNAME, PASSWORD, "1234567890", "98765",
			Role.RESIDENT.toString(), 0.0, 0.0);

	@Before
	public void createUser() throws JSONException {
		tstUser.setGeoLocLatitude(53.0);
		tstUser.setGeoLocLongitude(-121.0);
		UserNotification selNot = new UserNotification(Long.valueOf(NotificationType.EMAIL.ordinal()));
		Set<UserNotification> notificationType = new HashSet<>();
		notificationType.add(selNot);
		tstUser.setNotificationType(notificationType);

		smtpServer = new GreenMail(new ServerSetup(3025, "127.0.0.1", ServerSetup.PROTOCOL_SMTP));
		smtpServer.start();

		registerAndLogUser();
	}

	@After
	public void exit() {

		if (smtpServer != null) {
			smtpServer.stop();
		}
		try {
			SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
			Connection sqlConnection = ((SessionImpl) sessionFactory.openSession()).connection();
			Statement st = sqlConnection.createStatement();
			int res = st.executeUpdate("delete from user_notification");
			res = st.executeUpdate("delete from user");
			sqlConnection.commit();
		} catch (Exception ex) {
			Logger.getLogger(LocalizationResourceIntegrationTest.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void registerAndLogUser() throws JSONException {
		Client client = new JerseyClientBuilder().build();

		client.target(String.format("http://localhost:%d/register", RULE.getLocalPort())).request()
				.post(Entity.json(tstUser));
		// login user
		LoginUserDto loginUserDto = new LoginUserDto();
		loginUserDto.setEmail(USERNAME);
		loginUserDto.setPassword(PASSWORD);
		Response response = client.target(String.format("http://localhost:%d/login", RULE.getLocalPort())).request()
				.post(Entity.json(loginUserDto));
		JSONObject responseJo = new JSONObject(response.readEntity(String.class));
		authToken = responseJo.optString("authToken");
	}

	@Test
	public void noArgument() throws JSONException {

		Client client = new JerseyClientBuilder().build();
		Response response = client.target(String.format(url, RULE.getLocalPort())).request()
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken).post(Entity.json(null));
		Assert.assertEquals(422, response.getStatus());
		JSONObject responseJo = new JSONObject(response.readEntity(String.class));
		Assert.assertTrue(!StringUtils.isBlank(responseJo.optString("errors")));
		Assert.assertEquals("[\"The request body may not be null\"]", responseJo.optString("errors"));
	}

	@Test
	public void noGeoLocValue() throws JSONException {
		Client client = new JerseyClientBuilder().build();
		tstUser.setEmail(null);

		UserNotification selNot = new UserNotification(Long.valueOf(NotificationType.EMAIL.ordinal()));
		Set<UserNotification> notificationType = new HashSet<>();
		notificationType.add(selNot);
		tstUser.setNotificationType(notificationType);

		Response response = client.target(String.format(url, RULE.getLocalPort())).request()
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken).post(Entity.json(tstUser));
		Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
		ErrorInfo errorInfo = response.readEntity(ErrorInfo.class);
		for (com.cgi.poc.dw.util.Error error : errorInfo.getErrors()) {
			assertThat(error.getCode()).isEqualTo(GeneralErrors.INVALID_INPUT.getCode());
			// The data provided in the API call is invalid. Message: <XXXXX>
			// where XXX is the message associated to the validation
			String partString = "geolocalization may not be null";
			String expectedErrorString = GeneralErrors.INVALID_INPUT.getMessage().replace("REPLACE", partString);
			assertThat(error.getMessage()).isEqualTo(expectedErrorString);
		}

	}

	@Test
	public void setLocalizationSuccess() throws JSONException {

		Client client = new JerseyClientBuilder().build();
		Response response = client.target(String.format(url, RULE.getLocalPort())).request()
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken).post(Entity.json(tstUser));
		Assert.assertEquals(422, response.getStatus());
		JSONObject responseJo = new JSONObject(response.readEntity(String.class));
		Assert.assertTrue(!StringUtils.isBlank(responseJo.optString("errors")));
		Assert.assertEquals("[\"The request body may not be null\"]", responseJo.optString("errors"));
	}

}