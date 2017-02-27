package com.cgi.poc.dw.api.service.impl;

import com.cgi.poc.dw.api.service.APICallerService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import java.io.IOException;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * class to call API to collect the resources event information
 *
 * @author vincent.baylly
 */
public abstract class APICallerServiceImpl implements APICallerService {

  private static final Logger LOG = LoggerFactory.getLogger(APICallerServiceImpl.class);

  private Client client;
  private String eventUrl;
  protected SessionFactory sessionFactory;

  @Inject
  public APICallerServiceImpl(String eventUrl, Client client,
      SessionFactory sessionFactory) {
    this.eventUrl = eventUrl;
    this.client = client;
    this.sessionFactory = sessionFactory;
  }

  /**
   * call the service API to collect event information.
   */
  @Override
  public void callServiceAPI() {
    try {
      String response = client
          .target(eventUrl)
          .request(MediaType.APPLICATION_JSON)
          .get(String.class);

      final ObjectNode eventJson = new ObjectMapper().readValue(response, ObjectNode.class);
      if (eventJson != null) {
        processEventJSON(eventJson);
      }
    } catch (Exception e) {
      LOG.error("Unable to parse the result for the url event : {}", eventUrl, e);
    }
  }

  /**
   * parsing the json to a java object.
   *
   * @param featureJson the json string get from the Rest API
   */
  abstract void processEventJSON(ObjectNode featureJson);
}
