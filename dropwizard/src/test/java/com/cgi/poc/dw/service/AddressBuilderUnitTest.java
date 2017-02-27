package com.cgi.poc.dw.service;

import static org.junit.Assert.assertEquals;

import com.cgi.poc.dw.factory.AddressBuilder;
import com.cgi.poc.dw.factory.AddressBuilderImpl;
import org.junit.Test;

public class AddressBuilderUnitTest {
  private AddressBuilder builder = new AddressBuilderImpl();

  @Test
  public void buildsZipCodeOnlyIfAllOtherAddressElementsAreBlank() {
    assertEquals("95628", builder.build("", "", "", "", "95628"));
  }

  @Test
  public void buildsZipCodeOnlyIfAllOtherAddressElementsAreNull() {
    assertEquals("95628", builder.build(null, null, null, null, "95628"));
  }

  @Test
  public void buildsAddressWithoutAddress2UsingUnitedStatesFormat() {
    assertEquals("3188 Zinfandel Dr, Rancho Cordova, CA 95670", builder.build("3188 Zinfandel Dr", null, "Rancho Cordova", "CA", "95670"));
  }

  @Test
  public void buildsAddressIncludingAddress2() {
    assertEquals("3188 Zinfandel Dr, Suite 799, Rancho Cordova, CA 95670", builder.build("3188 Zinfandel Dr", "Suite 799", "Rancho Cordova", "CA", "95670"));
  }

  @Test
  public void nullsAreTreatedAsEmptyStrings() {
    assertEquals("3188 Zinfandel Dr, Suite 799, ,  95670", builder.build("3188 Zinfandel Dr", "Suite 799", null, null, "95670"));
  }
}
