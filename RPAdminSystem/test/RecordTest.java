/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import RPAdminSystem.RIERecord;
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
public class RecordTest {
    String userid = "h0910000";
    String category = "Test category";
    String title = "Test title";
    String desc1 = "The quick brown fox jumped over the lazy dogs";
    String desc2 = "Lorem ipsum dolor sit amet, ...";
    String award = "Gold";
    int year = 2014;
    String grade = "Merit";
    
    RIERecord r;
    
    public RecordTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        r = new RIERecord (userid, category, title, desc1, desc2, award, year, grade);
    }
    
    @After
    public void tearDown() {
        r = null;
    }

    @Test
    public void arrayTests() {
        Object[] array = new Object[] {
            (Object) userid,
            (Object) category,
            (Object) title,
            (Object) desc1,
            (Object) desc2,
            (Object) award,
            (Object) year,
            (Object) grade
        };
        
        
        
        Assert.assertArrayEquals(array, r.toArray());
        
        assertEquals(r, RIERecord.fromArray(array));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void badArrayTest() {
        Object[] array = new Object [] {(Object) userid, (Object) grade};
        
        assertEquals(r, RIERecord.fromArray(array));
    }
    
    @Test
    public void recordAttributes () {
        assertEquals (userid, r.getUserid());
        assertEquals (category, r.getCategory());
        assertEquals (title, r.getTitle());
        assertEquals (desc1, r.getDesc1());
        assertEquals (desc2, r.getDesc2());
        assertEquals (award, r.getAward());
        assertEquals (year, r.getYear());
        assertEquals (grade, r.getGrade());
        
        r.setUserid ("h0910001");
        assertEquals ("h0910001", r.getUserid());
        r.setCategory ("Another category");
        assertEquals ("Another category", r.getCategory());
        r.setTitle ("Some other title");
        assertEquals ("Some other title", r.getTitle());
        r.setDesc1 ("The authors examine two aspects of brand loyalty, purchase loyalty and attitudinal loyalty, ...");
        assertEquals ("The authors examine two aspects of brand loyalty, purchase loyalty and attitudinal loyalty, ...", r.getDesc1());
        r.setDesc2 ("This description is useless");
        assertEquals ("This description is useless", r.getDesc2());
        r.setAward ("Silver");
        assertEquals ("Silver", r.getAward());
        r.setYear(2010);
        assertEquals (2010, r.getYear());
        r.setGrade ("Excellent");
        assertEquals ("Excellent", r.getGrade());
    }
}
