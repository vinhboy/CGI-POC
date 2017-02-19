/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgi.poc.dw.model;

import com.cgi.poc.dw.dao.model.EventEarthquakePK;
import com.cgi.poc.dw.dao.model.EventEarthquake;
import com.cgi.poc.dw.dao.model.EventNotification;
import com.cgi.poc.dw.dao.model.EventNotificationZipcode;
import com.cgi.poc.dw.dao.model.User;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.File;
import java.util.Set;
import javax.validation.ConstraintViolation;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.io.FileReader;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.Date;
import static org.assertj.core.api.Assertions.assertThat;
import org.hibernate.validator.internal.engine.path.PathImpl;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

/**
 *
 * @author dawna.floyd
 */
public class EventNotificationTest extends BaseTest {


    public EventNotificationTest() {
        super();

    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws Exception {
       super.setUp();


    }

    @After
    public void tearDown() {
               super.tearDown();

    }

    /**
     * Test of Nulls 
     */
    @Test
    public void testNullsInNotNulls() {

        EventNotification testEvent = new EventNotification();
        
        Set<ConstraintViolation<EventNotification>> validate = validator.validate(testEvent);
        assertThat(validate.isEmpty()).isEqualTo(false);
        assertThat(validate.size()).isEqualTo(4);
        for ( ConstraintViolation violation : validate ) {
                
              String tmp = ((PathImpl)violation.getPropertyPath())
                .getLeafNode().getName();
              if (tmp.equals("userId")){
               assertThat(tmp).isEqualTo("userId");
               assertThat(violation.getMessageTemplate()).isEqualTo("{javax.validation.constraints.NotNull.message}");
                  
              }else if (tmp.equals("eventNotificationZipcodes")){
               assertThat(tmp).isEqualTo("eventNotificationZipcodes");
               assertThat(violation.getMessageTemplate()).isEqualTo("{javax.validation.constraints.NotNull.message}");
                  
              }else if (tmp.equals("type")){
               assertThat(tmp).isEqualTo("type");
               assertThat(violation.getMessageTemplate()).isEqualTo("{javax.validation.constraints.NotNull.message}");
                  
              }else if (tmp.equals("description")){
               assertThat(tmp).isEqualTo("description");
               assertThat(violation.getMessageTemplate()).isEqualTo("{javax.validation.constraints.NotNull.message}");
                  
              }
              else {
                  fail("not an expected constraint violation");
              }

        }

        
    }
  
    @Test
    public void testFieldLengthValidations() throws Exception {
        EventNotification testEvent = new EventNotification();
        EventNotification event = new EventNotification();
        User tmpUser = new User();
        tmpUser.setId(Long.valueOf(100));
        event.setType("012345678900123456789001234567890");
        String str = "01234567890";
        //make a 100+ char field
        for (int i = 0; i <= 11 ;i++) {
             str = str.concat( "01234567890");
        }
        event.setUrl1(str);
        event.setUrl12(str);
        event.setUserId(tmpUser);
        //make a 2000+ char field
        // str is already 110 char... so...
        for (int i = 0; i <= 201 ;i++) {
             str = str.concat( "01234567890");
        }
        event.setDescription(str);
        event.setCitizensAffected(Integer.valueOf(1000));

        EventNotificationZipcode eventNotificationZipcode1 = new EventNotificationZipcode();
        eventNotificationZipcode1.setZipCode("AAA");
        eventNotificationZipcode1.setEventNotificationId(event);
        EventNotificationZipcode eventNotificationZipcode2 = new EventNotificationZipcode();
        eventNotificationZipcode1.setEventNotificationId(event);
        event.addPZipcode(eventNotificationZipcode1);
        event.addPZipcode(eventNotificationZipcode2);

        String geo = "\"geometry\": {\n"
                + "    \"x\": -10677457.159137897,\n"
                + "    \"y\": 4106537.9944933983\n"
                + "  }";
        event.setGeometry(geo);
         
        Set<ConstraintViolation<EventNotification>> validate = validator.validate(event);
        
        assertThat(validate.size()).isEqualTo(6);
        
        
    }
}
