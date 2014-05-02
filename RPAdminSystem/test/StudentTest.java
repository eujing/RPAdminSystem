/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import RPAdminSystem.Student;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author eujing
 */
public class StudentTest {
    /**
     * There are no checks when creating a Student Record from scratch as
     * no Student Record should be created from scratch, only via the ResourceManager
     * where verification is carried out when loading from resources.
     */
    //Sample data
    String userid = "h0910001";
    String name = "Bob Doe";
    
    Student s;
    
    public StudentTest() {
    }
    
    @Before
    public void setUp() {
        s = new Student (userid, name);
    }
    
    @After
    public void tearDown() {
        s = null;
    }

    /**
     * Tests the consistency of Student Records when reduced to arrays and
     * when creating records from arrays
     * Partitions:
     * array to array comparison
     * record to record comparison
     * invalid values (wrong array length)
     */
    @Test
    public void arrayTests () {
        //Correct array
        Object[] array = new Object[] {(Object) userid, (Object) name};
        
        //Test consistency of array form
        Assert.assertArrayEquals (array, s.toArray());
        
        //Test consistency of record form
        assertEquals (s, Student.fromArray(array));
    }
    //Invalid Values (Wrong length array)
    @Test(expected = IllegalArgumentException.class)
    public void badArrayTest1 () {
        Object[] array = new Object [] {(Object) userid};
        
        assertEquals(s, Student.fromArray(array));
    }
    
    /**
     * Ensure the consistency of fields from the get and set methods of the class
     * There are no checks involved so partition testing is useless.
     */
    @Test
    public void studentAttributes () {
        //Check consistency of get methods
        assertEquals (userid, s.getUserid());
        assertEquals (name, s.getName());
        
        //Check consistency of set methods
        s.setUserid("h0910002");
        assertEquals("h0910002", s.getUserid());
        s.setname("John King");
        assertEquals("John King", s.getName());
    }
}
