package com.cgi.poc.dw.rest.resource;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.sql.SQLException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cgi.poc.dw.helper.IntegrationTest;
import com.cgi.poc.dw.helper.IntegrationTestHelper;
import com.cgi.poc.dw.util.Error;
import com.cgi.poc.dw.util.ErrorInfo;
import com.cgi.poc.dw.util.GeneralErrors;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class UserResourceForLocalizationIntegrationTest extends IntegrationTest {

	private static final String url = "http://localhost:%d/localizer";

	@BeforeClass
	public static void createAdminUser() throws SQLException {
		IntegrationTestHelper.signupResidentUser();
	}

	@AfterClass
	public static void cleanup() {
		IntegrationTestHelper.cleanDbState();
	}

	@Test
	public void noArgument() throws JsonParseException, JsonMappingException, IOException {
		
		String authToken = IntegrationTestHelper.getAuthToken("resident@cgi.com", "residentpw", RULE);

		Client client = new JerseyClientBuilder().build();
		Response response = client.
				target(String.format(url, RULE.getLocalPort())).
				request().
				header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken).
				post(Entity.json(null));
		Assert.assertEquals(422, response.getStatus());

		String output = response.readEntity(String.class);

		final ObjectNode node = new ObjectMapper().readValue(output, ObjectNode.class);

		Assert.assertTrue(!StringUtils.isBlank(node.path("errors").asText()));
		Assert.assertEquals("[\"The request body may not be null\"]", node.path("errors").asText());
	}

	@Test
	public void noGeoLocValue(){

		String authToken = IntegrationTestHelper.getAuthToken("resident@cgi.com", "residentpw", RULE);
		
		Client client = new JerseyClientBuilder().build();
		Response response = client.
				target(String.format(url, RULE.getLocalPort())).
				request().
				header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken).
				post(Entity.json(null));
		Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
		ErrorInfo errorInfo = response.readEntity(ErrorInfo.class);
		for (Error error : errorInfo.getErrors()) {
			assertThat(error.getCode()).isEqualTo(GeneralErrors.INVALID_INPUT.getCode());
			// The data provided in the API call is invalid. Message: <XXXXX>
			// where XXX is the message associated to the validation
			String partString = "geolocalization may not be null";
			String expectedErrorString = GeneralErrors.INVALID_INPUT.getMessage().replace("REPLACE", partString);
			assertThat(error.getMessage()).isEqualTo(expectedErrorString);
		}

	}

	@Test
	public void setLocalization_Success(){

		String authToken = IntegrationTestHelper.getAuthToken("resident@cgi.com", "residentpw", RULE);

		Client client = new JerseyClientBuilder().build();
		Response response = client.
				target(String.format(url, RULE.getLocalPort())).
				request().
				header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken).
				post(Entity.json(null));
		Assert.assertEquals(200, response.getStatus());

	}

}