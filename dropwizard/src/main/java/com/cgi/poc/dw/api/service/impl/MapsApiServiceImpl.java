package com.cgi.poc.dw.api.service.impl;

import com.cgi.poc.dw.MapApiConfiguration;
import com.cgi.poc.dw.api.service.MapsApiService;
import com.cgi.poc.dw.api.service.data.GeoCoordinates;
import com.cgi.poc.dw.dao.model.User;
import com.cgi.poc.dw.service.AddressBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapsApiServiceImpl implements MapsApiService {

  private final static Logger LOG = LoggerFactory.getLogger(MapsApiServiceImpl.class);

  private MapApiConfiguration mapApiConfiguration;

  private Client client;
  private AddressBuilder addressBuilder;

  private static final String ADDRESS = "address";

  @Inject
  public MapsApiServiceImpl(MapApiConfiguration mapApiConfiguration, Client client, AddressBuilder addressBuilder) {
    this.mapApiConfiguration = mapApiConfiguration;
    this.client = client;
    this.addressBuilder = addressBuilder;
  }

  @Override
  public GeoCoordinates getGeoCoordinatesByZipCode(String zipCode) {
    return getGeoCoordinates(zipCode);
  }

  @Override
  public GeoCoordinates getGeoCoordinatesByUser(User user) {
    return getGeoCoordinates(addressBuilder.build(user));
  }

  private GeoCoordinates getGeoCoordinates(String address) {
    GeoCoordinates geoCoordinates = new GeoCoordinates();
    try {
      String response = client
              .target(mapApiConfiguration.getApiURL())
              .queryParam(ADDRESS, address)
              .request(MediaType.APPLICATION_JSON)
              .get(String.class);

      final ObjectNode node = new ObjectMapper().readValue(response, ObjectNode.class);

      if (node.path("results").size() > 0 && "OK".equals(node.path("status").asText())) {
        geoCoordinates.setLatitude(
                node.get("results").get(0).get("geometry").get("location").get("lat").asDouble());
        geoCoordinates.setLongitude(
                node.get("results").get(0).get("geometry").get("location").get("lng").asDouble());
      } else {
        geoCoordinates.setLatitude(0.0);
        geoCoordinates.setLongitude(0.0);
      }
    } catch (Exception exception) {
      LOG.error("Unable to make maps api call.", exception);
      throw new InternalServerErrorException("Maps API is unavailable");
    }

    return geoCoordinates;
  }
}
