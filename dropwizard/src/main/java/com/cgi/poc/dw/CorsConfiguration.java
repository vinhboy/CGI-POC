package com.cgi.poc.dw;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class CorsConfiguration {

  /**
   * A list of comma-separated domains that the service will allow cross-origin access from.  If the
   * Origin matches one of the domains in the list the application will generate a matching
   * Access-Control-Allow-Origin header in the response.
   *
   * If the Origin header does not match, the application will not send the A-C-A-O header back to
   * the client.
   *
   * Uses Jetty's CrossOriginFilter, so partial regex is supported: i.e. https?://*.cgi.[a-z]{3}
   * -> supports http/https with any subdomain and any 3 character top-level domain for the host
   * "cgi" c.f. https://www.eclipse.org/jetty/documentation/9.3.x/cross-origin-filter.html
   *
   * IMPORTANT NOTE: if there is no * character in the expression, the filter processes it as a raw
   * string, NOT as a regex
   */
  private String allowedDomains;
  private List<String> allowedHeaders;

  public CorsConfiguration() {
  }

  public CorsConfiguration(String allowedDomains) {
    this.allowedDomains = allowedDomains;
  }

  @JsonProperty
  public String getAllowedDomains() {
    return allowedDomains;
  }

  @JsonProperty
  public void setAllowedDomains(String allowedDomains) {
    this.allowedDomains = allowedDomains;
  }

  @JsonProperty
  public List<String> getAllowedHeaders() {
    return allowedHeaders;
  }

  @JsonProperty
  public void setAllowedHeaders(List<String> allowedHeaders) {
    this.allowedHeaders = allowedHeaders;
  }
}
