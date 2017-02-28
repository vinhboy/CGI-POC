package com.cgi.poc.dw.auth.service;

import com.cgi.poc.dw.CgiPocConfiguration;
import com.cgi.poc.dw.auth.data.Keys;
import java.security.NoSuchAlgorithmException;

public interface KeyBuilderService {

  Keys createKeys(CgiPocConfiguration configuration) throws NoSuchAlgorithmException;
}
