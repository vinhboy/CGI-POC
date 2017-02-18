package com.cgi.poc.dw.jobs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.cgi.poc.dw.JobsConfiguration;
import com.cgi.poc.dw.helper.IntegrationTest;
import static com.cgi.poc.dw.helper.IntegrationTest.RULE;

import io.dropwizard.lifecycle.JettyManaged;
import io.dropwizard.lifecycle.Managed;
import java.util.List;
import org.eclipse.jetty.util.component.LifeCycle;

public class JobExecutionServiceTest extends IntegrationTest{
	

	  @Test
	  public void verifyParemeterloadUnitTest() {
		  JobsConfiguration jobsConfiguration = RULE.getConfiguration().getJobsConfiguration();
		  assertNotNull(jobsConfiguration);
		  assertEquals(jobsConfiguration.getThread(), 2);
		  assertTrue(jobsConfiguration.getJobs().size() > 0);
		  assertEquals(jobsConfiguration.getJobs().get(0).getEventURL(), "https://wildfire.cr.usgs.gov/arcgis/rest/services/geomac_dyn/MapServer/0/query?f=json&where=1%3D1&outFields=*&outSR=4326");
		  assertEquals(jobsConfiguration.getJobs().get(0).getDelay(), 10);
		  assertEquals(jobsConfiguration.getJobs().get(0).getPeriod(), 10);
		  assertEquals(jobsConfiguration.getJobs().get(0).getTimeUnit(), "SECONDS");
	  }
	  
	  @Test
	  public void startedServiceUnitTest() {
              
              List<LifeCycle> managedObjects = RULE.getEnvironment().lifecycle().getManagedObjects();
              boolean bFound = false;
              for(LifeCycle obj : managedObjects){
                  if ( obj instanceof JettyManaged)  {
                      JettyManaged jmObj = (JettyManaged)obj;
                      Managed managed = jmObj.getManaged();
                     if (  managed instanceof JobExecutionService)  {
                         bFound = true;                      
                     }                            
                  }                            
              }
              assertTrue(bFound);

	  }
	
}
