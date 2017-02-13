/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgi.poc.dw.dao;

import com.cgi.poc.dw.dao.model.EventEarthquake;
import com.cgi.poc.dw.dao.model.EventEarthquakePK;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.hibernate.validator.internal.engine.path.PathImpl;
import static org.assertj.core.api.Assertions.assertThat;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
 
/**
 *
 * @author dawna.floyd
 */
 
public class EventEarthquakeDAOTest extends DaoUnitTestBase  {
    
 
    EventEarthquakeDAO eventDAO;
     
     public EventEarthquakeDAOTest() {
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
        eventDAO = new EventEarthquakeDAO(getSessionFactory(),validator); 
        
    }
    
    @After
    public void tearDown() {
              super.tearDown();

     }
 
    /**
     * Test of update method, of class FireEventDAO.
     */
    @Test
    public void testCRUD() throws IOException, ParseException {
        System.out.println("update");
        EventEarthquakePK eventEarthquakePK = new EventEarthquakePK();
        EventEarthquake event = new EventEarthquake();
        eventEarthquakePK.setEqid("1234");
        eventEarthquakePK.setDatetime(new Date());
        event.setEventEarthquakePK(eventEarthquakePK);
        event.setMagnitude(BigDecimal.valueOf(4.0));
        JSONObject geo = new JSONObject();
        JSONObject ele = new JSONObject();
        geo.put("geometry", ele);
        ele.put("x", -10677457.159137897);
        ele.put("y", 4106537.9944933983);

        event.setGeometry(geo.toJSONString());
        EventEarthquake result = eventDAO.save(event);
        Set<ConstraintViolation<EventEarthquake>> errors = validator.validate(result);
        assertThat(result.getEventEarthquakePK().getEqid()).isEqualTo(event.getEventEarthquakePK().getEqid());
        assertEquals(result.getLastModified(), result.getLastModified());
        assertEquals(result.getNotificationId(), result.getNotificationId());
        assertEquals(result.getMagnitude().setScale(2), result.getMagnitude().setScale(2));
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(result.getGeometry());
        JSONObject resAsJson = ( JSONObject) obj;
          assertThat((( JSONObject) resAsJson.get("geometry")).get("x")).isEqualTo((( JSONObject) resAsJson.get("geometry")).get("x"));
        
        
        
        assertEquals(result.getGeometry(), result.getGeometry());
        
        
         flush(); // have to do this.. so that the sql is actually executed.

        List<EventEarthquake> resultList = eventDAO.findById(result.getEventEarthquakePK().getEqid());  
        EventEarthquake result2  = resultList.get(0);
        assertThat(result2.getEventEarthquakePK().getEqid()).isEqualTo(event.getEventEarthquakePK().getEqid());
        assertEquals(result2.getLastModified(), result.getLastModified());
        assertEquals(result2.getNotificationId(), result.getNotificationId());
        assertEquals(result2.getMagnitude().setScale(2), result.getMagnitude().setScale(2));
        
         resAsJson = ( JSONObject) (parser.parse(result2.getGeometry()));

          assertThat((( JSONObject) resAsJson.get("geometry")).get("x")).isEqualTo((( JSONObject) resAsJson.get("geometry")).get("x"));

  
        EventEarthquake selectForUpdate = eventDAO.selectForUpdate(result);
        assertThat(selectForUpdate.getEventEarthquakePK().getEqid()).isEqualTo(event.getEventEarthquakePK().getEqid());
        assertEquals(selectForUpdate.getLastModified(), result.getLastModified());
        assertEquals(selectForUpdate.getNotificationId(), result.getNotificationId());
        assertEquals(selectForUpdate.getMagnitude(), result.getMagnitude());
         resAsJson = ( JSONObject) (parser.parse(selectForUpdate.getGeometry()));
          assertThat((( JSONObject) resAsJson.get("geometry")).get("x")).isEqualTo((( JSONObject) resAsJson.get("geometry")).get("x"));
        
 
    }
    @Test
    public void findNotFound() {
        System.out.println("update");

        List<EventEarthquake> resultList   = eventDAO.findById("noWay");   
         flush(); // have to do this.. so that the sql is actually executed.
         assertThat(resultList.size()).isEqualTo(0);
    }
    @Test
    public void invalidInsert() {
        System.out.println("update");
        EventEarthquakePK eventEarthquakePK = new EventEarthquakePK();
        EventEarthquake event = new EventEarthquake();
        eventEarthquakePK.setEqid("");
        eventEarthquakePK.setDatetime(new Date());
        event.setEventEarthquakePK(eventEarthquakePK);
        event.setMagnitude(BigDecimal.valueOf(4.0));

        EventEarthquake result = eventDAO.save(event);
        boolean bExceptionCaught = false;
        try {
             flush();            
        } catch (ConstraintViolationException hibernateException) {
            Set<ConstraintViolation<?>> constraintViolations = hibernateException.getConstraintViolations();
            for ( ConstraintViolation violation : constraintViolations ) {
                
              String tmp = ((PathImpl)violation.getPropertyPath())
                .getLeafNode().getName();
              if (tmp.equals("eqid")){
               assertThat(tmp).isEqualTo("eqid");
               assertThat(violation.getMessageTemplate()).isEqualTo("{javax.validation.constraints.Size.message}");
                  
              }else if (tmp.equals("geometry")){
               assertThat(tmp).isEqualTo("geometry");
               assertThat(violation.getMessageTemplate()).isEqualTo("{javax.validation.constraints.NotNull.message}");
                  
              }
              else {
                  fail("not an expected constraint violation");
              }

            }
            bExceptionCaught = true;

        } 
        
        
        assertThat(bExceptionCaught).isEqualTo(true);

 
    }
}
