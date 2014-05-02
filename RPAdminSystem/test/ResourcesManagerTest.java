/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import RPAdminSystem.ResourcesManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author eujing
 */
public class ResourcesManagerTest {
    
    ResourcesManager rm;
    
    public ResourcesManagerTest() {
    }

    @Before
    public void setUp() {
        rm = new ResourcesManager();
    }
    
    @After
    public void tearDown() {
        rm = null;
    }

    /**
     * Tests the userid validation function that is called when loading files that should contain userids.
     * Partitions:
     * Median Values
     * Extreme Values
     * Invalid Values (Wrong format)
     * Invalid Values (Wrong length)
     */
    @Test
    public void useridTests() {
        //Median Values
        assertEquals ("h0910001", rm.verifyUserid("h0910001"));
        assertEquals ("h5555555", rm.verifyUserid("h5555555"));
        
        //Extreme Values
        assertEquals ("h0000000", rm.verifyUserid("h0000000"));
        assertEquals ("h9999999", rm.verifyUserid("h9999999"));
    }
    //Invalid Values (Wrong format)
    @Test(expected = IllegalArgumentException.class)
    public void badidTest1() {
        assertEquals("g0910001", rm.verifyUserid("g0910001"));
    }
    //Invalid Values (Wrong length)
    @Test(expected = IllegalArgumentException.class)
    public void badidTest2() {
        assertEquals("h09100010", rm.verifyUserid("h09100010"));
    }
    
    /**
    * Tests the year validation function that is called when loading files that should contain years.
    * Partitions:
    * Median Values
    * Extreme Values
    * Invalid Values (Negative years)
    * Invalid Values (Too large years)
    */
    @Test
    public void yearTests() {
        //Median Values
        assertEquals(2014, rm.verifyYear("2014"));
        assertEquals(5000, rm.verifyYear("5000"));
        
        //Extreme values
        assertEquals(0, rm.verifyYear("0"));
        assertEquals(9999, rm.verifyYear("9999"));
    }
    //Invalid Values (Negative years)
    @Test(expected = IllegalArgumentException.class)
    public void badyearTest1() {
        assertEquals(-2014, rm.verifyUserid("-2014"));
    }
    //Invalid Values (Too large years)
    @Test(expected = IllegalArgumentException.class)
    public void badyearTest2() {
        assertEquals(12345, rm.verifyUserid("12345"));
    }
}
