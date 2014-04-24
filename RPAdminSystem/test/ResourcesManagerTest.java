/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        rm = new ResourcesManager();
    }
    
    @After
    public void tearDown() {
        rm = null;
    }

    @Test
    public void useridTests() {
        assertEquals ("h0910001", rm.verifyUserid("h0910001"));
        assertEquals ("h0000000", rm.verifyUserid("h0000000"));
        assertEquals ("h5555555", rm.verifyUserid("h5555555"));
        assertEquals ("h9999999", rm.verifyUserid("h9999999"));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void badidTest1() {
        assertEquals("g0910001", rm.verifyUserid("g0910001"));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void badidTest2() {
        assertEquals("h09100010", rm.verifyUserid("h09100010"));
    }
    
    @Test
    public void yearTests() {
        assertEquals(2014, rm.verifyYear("2014"));
        assertEquals(0, rm.verifyYear("0"));
        assertEquals(5000, rm.verifyYear("5000"));
        assertEquals(9999, rm.verifyYear("9999"));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void badyearTest1() {
        assertEquals(-2014, rm.verifyUserid("-2014"));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void badyearTest2() {
        assertEquals(12345, rm.verifyUserid("12345"));
    }
}
