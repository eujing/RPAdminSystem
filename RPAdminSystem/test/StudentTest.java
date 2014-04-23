/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author eujing
 */
public class StudentTest {
    String userid = "h0910001";
    String name = "Bob Doe";
    
    Student s;
    
    public StudentTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        s = new Student (userid, name);
    }
    
    @After
    public void tearDown() {
        s = null;
    }

    @Test
    public void arrayTests () {
        Object[] array = new Object[] {(Object) userid, (Object) name};
        
        Assert.assertArrayEquals (array, s.toArray());
        
        assertEquals (s, Student.fromArray(array));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void badArrayTest () {
        Object[] array = new Object [] {(Object) userid};
        
        assertEquals(s, Record.fromArray(array));
    }
    
    @Test
    public void studentAttributes () {
        assertEquals (userid, s.getUserid());
        assertEquals (name, s.getName());
        
        s.setUserid("h0910002");
        assertEquals("h0910002", s.getUserid());
        s.setname("John King");
        assertEquals("John King", s.getName());
    }
}
