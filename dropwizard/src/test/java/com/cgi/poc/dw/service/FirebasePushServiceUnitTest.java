package com.cgi.poc.dw.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FirebasePushServiceUnitTest {

  @InjectMocks
  private FirebasePushServiceImpl underTest;

  @Test
  public void sendingPushNotification(){
    Boolean rt = underTest.send("Test","Title", "Message");

    assertEquals("Trying send test push notification.",true,rt);
  }
}
