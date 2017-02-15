/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cgi.poc.dw.model;

import com.cgi.poc.dw.dao.model.EventTsunami;
import java.io.File;
import java.util.Set;
import javax.validation.ConstraintViolation;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.io.FileReader;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 *
 * @author dawna.floyd
 */
public class EventTsunamiValidationTest extends BaseTest {


    public EventTsunamiValidationTest() {
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
     * Test of min required fields on model
     */
    @Test
    public void testValidTsunamiEventMinValid() {
        // The only field we know is required is the ID
        EventTsunami event = new EventTsunami();
        event.setId(BigDecimal.valueOf(123));


        JSONObject geo = new JSONObject();
        JSONObject ele = new JSONObject();
        geo.put("geometry", ele);
        ele.put("x", -10677457.159137897);
        ele.put("y", 4106537.9944933983);
        
        event.setGeometry(geo.toJSONString());
        Set<ConstraintViolation<EventTsunami>> validate = validator.validate(event);
        assertThat(validate.isEmpty()).isEqualTo(true);
    }

    /**
     * Test of min required fields on model
     */
    @Test
    public void testExampleFromSource() throws IOException, ParseException, URISyntaxException {
         JSONParser parser = new JSONParser();
         File file = new File(ClassLoader.getSystemResource("exampleTsunamiEvent.json").toURI());
         
            Object obj = parser.parse(new FileReader(file));

           JSONObject jsonObject = (JSONObject) obj;
           JSONObject jsonEvent = (JSONObject)jsonObject.get("attributes");
           JSONObject geo = (JSONObject)jsonObject.get("geometry");
           ObjectMapper mapper = new ObjectMapper();
           EventTsunami event = mapper.readValue(jsonEvent.toString(), EventTsunami.class);
         assertEquals(event.getId(),new BigDecimal((long)jsonEvent.get("ID")));
         assertEquals(event.getYear().intValue(),Long.valueOf((long)jsonEvent.get("YEAR")).intValue());
         assertEquals(event.getMonth().intValue(),Long.valueOf((long)jsonEvent.get("MONTH")).intValue());
         assertEquals(event.getDay().intValue(),Long.valueOf((long)jsonEvent.get("DAY")).intValue());
         assertEquals(event.getHour().intValue(),Long.valueOf((long)jsonEvent.get("HOUR")).intValue());
         assertEquals(event.getMinute().intValue(),Long.valueOf((long)jsonEvent.get("MINUTE")).intValue());
         assertEquals(event.getSecond().intValue(),Double.valueOf((double)jsonEvent.get("SECOND")).intValue());
         assertEquals(event.getDateString(),jsonEvent.get("DATE_STRING"));
         assertEquals(event.getLatitude().setScale(20), BigDecimal.valueOf( (double)jsonEvent.get("LATITUDE")).setScale(20));
         assertEquals(event.getLongitude().setScale(20), BigDecimal.valueOf( (double)jsonEvent.get("LONGITUDE")).setScale(20));

         assertEquals(event.getLocationName(),jsonEvent.get("LOCATION_NAME"));
         assertEquals(event.getArea(),jsonEvent.get("AREA"));
         assertEquals(event.getCountry(),jsonEvent.get("COUNTRY"));
         assertEquals(event.getRegionCode().intValue(),Long.valueOf((long)jsonEvent.get("REGION_CODE")).intValue());
         assertEquals(event.getRegion(),jsonEvent.get("REGION"));
         assertEquals(event.getCauseCode().intValue(),Long.valueOf((long)jsonEvent.get("CAUSE_CODE")).intValue());
         assertEquals(event.getCause(),jsonEvent.get("CAUSE"));
         assertEquals(event.getEventValidityCode().intValue(),Long.valueOf((long)jsonEvent.get("EVENT_VALIDITY_CODE")).intValue());
         assertEquals(event.getEventValidity(),jsonEvent.get("EVENT_VALIDITY"));
         assertNull(event.getEqMagUnk());
         assertEquals(event.getEqMagMb().setScale(4), BigDecimal.valueOf( (double)jsonEvent.get("EQ_MAG_MB")).setScale(4));
         assertEquals(event.getEqMagMs().setScale(4), BigDecimal.valueOf( (double)jsonEvent.get("EQ_MAG_MS")).setScale(4));
         assertEquals(event.getEqMagMw().setScale(4), BigDecimal.valueOf( (double)jsonEvent.get("EQ_MAG_MW")).setScale(4));
         assertNull(event.getEqMagMl());
         assertNull(event.getEqMagMta());
         
         assertEquals(event.getEqMagnatude().setScale(4), BigDecimal.valueOf( (double)jsonEvent.get("EQ_MAGNITUDE")).setScale(4));
         assertEquals(event.getEqMagnatudeRank().setScale(4), BigDecimal.valueOf( (long)jsonEvent.get("EQ_MAGNITUDE_RANK")).setScale(4));
         assertEquals(event.getEqDepth().intValue(),Long.valueOf((long)jsonEvent.get("EQ_DEPTH")).intValue());
         assertEquals(event.getMaxEventRunup().setScale(4), BigDecimal.valueOf( (long)jsonEvent.get("MAX_EVENT_RUNUP")).setScale(4));
         assertNull(event.getTsMtAbe());
         assertEquals(event.getTsMtIi().setScale(4), BigDecimal.valueOf( (double)jsonEvent.get("TS_MT_II")).setScale(4));
         assertEquals(event.getTsIntensity().setScale(4), BigDecimal.valueOf( (double)jsonEvent.get("TS_INTENSITY")).setScale(4));
         assertNull(event.getDamageMillionsDollars());
         assertEquals(event.getDamageAmountOrder().intValue(),Long.valueOf((long)jsonEvent.get("DAMAGE_AMOUNT_ORDER")).intValue());
         assertEquals(event.getDamageDescription(),jsonEvent.get("DAMAGE_DESCRIPTION"));
         assertNull(event.getHousesDestroyed());
         assertNull(event.getHousesAmountOrder());
         assertNull(event.getHousesDescription());
         assertEquals(event.getDeaths().intValue(),Long.valueOf((long)jsonEvent.get("DEATHS")).intValue());
         assertEquals(event.getDeathsAmountOrder().intValue(),Long.valueOf((long)jsonEvent.get("DEATHS_AMOUNT_ORDER")).intValue());
         assertEquals(event.getDeathsDescription(),jsonEvent.get("DEATHS_DESCRIPTION"));
         assertEquals(event.getInjuries().intValue(),Long.valueOf((long)jsonEvent.get("INJURIES")).intValue());
         assertEquals(event.getInjuriesAmountOrder().intValue(),Long.valueOf((long)jsonEvent.get("INJURIES_AMOUNT_ORDER")).intValue());
         assertEquals(event.getInjuriesDescription(),jsonEvent.get("INJURIES_DESCRIPTION"));
         assertNull(event.getMissing());
         assertNull(event.getMissingAmountOrder());
         assertNull(event.getMissingDescription());
         assertEquals(event.getComments(),jsonEvent.get("COMMENTS"));
         assertEquals(event.getNumRunup().intValue(),Long.valueOf((long)jsonEvent.get("NUM_RUNUP")).intValue());
         assertNull(event.getDamageMillionsDollarsTotal());
         assertEquals(event.getDamageAmountOrderTotal().intValue(),Long.valueOf((long)jsonEvent.get("DAMAGE_AMOUNT_ORDER_TOTAL")).intValue());
         assertEquals(event.getDamageTotalDescription(),jsonEvent.get("DAMAGE_TOTAL_DESCRIPTION"));
         assertNull(event.getHousesDestroyedTotal());
         assertNull(event.getHousesAmountOrderTotal());
         assertNull(event.getHousesTotalDescription());
         assertEquals(event.getDeathsTotal().intValue(),Long.valueOf((long)jsonEvent.get("DEATHS_TOTAL")).intValue());
         assertEquals(event.getDeathsAmountOrderTotal().intValue(),Long.valueOf((long)jsonEvent.get("DEATHS_AMOUNT_ORDER_TOTAL")).intValue());
         assertEquals(event.getDeathsTotalDescription(),jsonEvent.get("DEATHS_TOTAL_DESCRIPTION"));
         assertEquals(event.getInjuriesTotal().intValue(),Long.valueOf((long)jsonEvent.get("INJURIES_TOTAL")).intValue());
         assertEquals(event.getInjuriesAmountOrderTotal().intValue(),Long.valueOf((long)jsonEvent.get("INJURIES_AMOUNT_ORDER_TOTAL")).intValue());
         assertEquals(event.getInjuriesTotalDescription(),jsonEvent.get("INJURIES_TOTAL_DESCRIPTION"));
         assertNull(event.getMissingTotal());
         assertNull(event.getMissingAmountOrderTotal());
         assertNull(event.getMissingTotalDescription());
         assertEquals(event.getObjectid().intValue(),Long.valueOf((long)jsonEvent.get("OBJECTID")).intValue());
         assertEquals(event.getShape(),jsonEvent.get("shape"));
         assertEquals(event.getHousesDamaged().intValue(),Long.valueOf((long)jsonEvent.get("HOUSES_DAMAGED")).intValue());
         assertEquals(event.getHousesDamagedAmountOrder().intValue(),Long.valueOf((long)jsonEvent.get("HOUSES_DAMAGED_AMOUNT_ORDER")).intValue());
         assertEquals(event.getHousesDamDescription(),jsonEvent.get("HOUSES_DAM_DESCRIPTION"));
         assertEquals(event.getHousesDamagedTotal().intValue(),Long.valueOf((long)jsonEvent.get("HOUSES_DAMAGED_TOTAL")).intValue());
         assertEquals(event.getHousesDamaAmountOrderTotal().intValue(),Long.valueOf((long)jsonEvent.get("HOUSES_DAM_AMOUNT_ORDER_TOTAL")).intValue());
         assertEquals(event.getHousesDamTotalDescription(),jsonEvent.get("HOUSES_DAM_TOTAL_DESCRIPTION"));
         assertEquals(event.getNumDeposits().intValue(),Long.valueOf((long)jsonEvent.get("NUM_DEPOSITS")).intValue());
         assertEquals(event.getUrl(),jsonEvent.get("URL")); 
    }
    @Test
    public void testFieldLengthValidations() throws IOException, ParseException {
        EventTsunami testEvent = new EventTsunami();
        testEvent.setId(new BigDecimal(1));
        testEvent.setDateString("01234567890123456789");
        testEvent.setLocationName("01234567890123456789");
        testEvent.setArea("0123");
        testEvent.setCountry("012345678901234567890123456789012345678901234567890123456789");
         testEvent.setRegion("012345678901234567890123456789012345678901234567890123456789");
         testEvent.setCause("012345678901234567890123456789012345678901234567890123456789");
         testEvent.setEventValidity("01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789");
        testEvent.setDamageDescription("01234567890123456789012345678901234567890123456789");
        testEvent.setHousesDescription("01234567890123456789012345678901234567890123456789");
        testEvent.setDeathsDescription("01234567890123456789012345678901234567890123456789");
        testEvent.setInjuriesDescription("01234567890123456789012345678901234567890123456789");
        testEvent.setMissingDescription("01234567890123456789012345678901234567890123456789");
        String str = "01234567890";
        for (int i = 0; i <= 401 ;i++) {
             str = str.concat( "01234567890");
        }
        testEvent.setComments(str);
        testEvent.setDamageTotalDescription("012345678901234567890123456789012345678901234567890123456789");
        testEvent.setHousesTotalDescription("012345678901234567890123456789012345678901234567890123456789");
        testEvent.setDeathsTotalDescription("012345678901234567890123456789012345678901234567890123456789");
        testEvent.setMissingTotalDescription("012345678901234567890123456789012345678901234567890123456789");
        testEvent.setInjuriesTotalDescription("012345678901234567890123456789012345678901234567890123456789");
        testEvent.setHousesDamDescription("012345678901234567890123456789012345678901234567890123456789");
        testEvent.setHousesDamTotalDescription("012345678901234567890123456789012345678901234567890123456789");
        
         
        JSONObject geo = new JSONObject();
        JSONObject ele = new JSONObject();
        geo.put("geometry", ele);
        ele.put("x", -10677457.159137897);
        ele.put("y", 4106537.9944933983);
 
        testEvent.setGeometry(geo.toJSONString());
        Set<ConstraintViolation<EventTsunami>> validate = validator.validate(testEvent);
        assertThat(validate.size()).isEqualTo(20);
        
        
    }
}
