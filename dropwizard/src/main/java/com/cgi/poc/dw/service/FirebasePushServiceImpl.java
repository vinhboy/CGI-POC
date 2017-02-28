package com.cgi.poc.dw.service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FirebasePushServiceImpl implements FirebasePushService{

  private final static Logger LOG = LoggerFactory.getLogger(FirebasePushServiceImpl.class);

  private String FCM_NOTIFICATION_URL = "https://fcm.googleapis.com/fcm/send";
  private String FCM_NOTIFICATION_KEY = "key=AAAA8XU5NgQ:APA91bEfETw0rTo7J1exWlzARff99f_SlyI1SVZywP_LGsLBOMAuVGbclayJGIfVRRb624N87-CP0MY6TmHR2jgBLU5irF7T4N40cFanF6or6pxLEGYMEisWilC6GjOL2Wx-0BXt8LRv";

  public boolean send(String token, String title, String message){
    String payload = "{ \n"
        + "\"notification\":\n"
        + "{ \"title\": \""+title+"\", \"body\": \""+message+"\" }\n"
        + ",\n"
        + "\"to\" : \""+token+"\"\n"
        + "}";

    try {
      HttpURLConnection httpcon = (HttpURLConnection) ((new URL(FCM_NOTIFICATION_URL).openConnection()));
      httpcon.setDoOutput(true);
      httpcon.setRequestProperty("Content-Type", "application/json");
      httpcon.setRequestProperty("Authorization", FCM_NOTIFICATION_KEY);
      httpcon.setRequestMethod("POST");
      httpcon.connect();

      byte[] outputBytes = payload.getBytes("UTF-8");
      OutputStream os = httpcon.getOutputStream();
      os.write(outputBytes);
      os.close();

      LOG.info("Push notification sent {}", token);

      return true;
    }catch(Exception e){
      LOG.error("Unable to push notification {} .", payload, e);
      return false;
    }
  }
}
