package com.cgi.poc.dw.api.service;

import com.cgi.poc.dw.api.service.data.GeoCoordinates;
import com.cgi.poc.dw.dao.model.User;

public interface MapsApiService {

  GeoCoordinates getGeoCoordinatesByZipCode(String zipCode);
  GeoCoordinates getGeoCoordinatesByAddress(String address);
}
