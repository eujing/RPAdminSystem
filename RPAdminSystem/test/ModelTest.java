import RPAdminSystem.RIERecord;
import RPAdminSystem.Student;
import RPAdminSystem.ResourcesManager;
import RPAdminSystem.RecordTableModel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author eujing
 */
public class ModelTest {
    
    RecordTableModel model;
    
    public ModelTest() {
    }
    
    @Before
    public void setUp() {
        model = new RecordTableModel ();
    }
    
    @After
    public void tearDown() {
        model = null;
    }

    /*
    Tests the getting and setting methods of the model 
    to ensure the consistency of records after being stored in the model
    */
    @Test
    public void dataTest () {
        //Check the model is empty before any data is set
        assertEquals (0, model.getRowCount());
        
        ResourcesManager rm = new ResourcesManager ();
        try {
            //Retrieve records from the file system (Original Records)
            ArrayList<RIERecord> records = rm.readRIERecords(new File ("RIE_records.csv"));
            
            //Set records as current data in the model
            model.setRIERecords(records);
            
            //Retrieve records from the model (Retrieved Records)
            Object[][] modelData = model.getData();
            
            //Check the number of records is consistent
            assertEquals (records.size(), model.getRowCount());
            assertEquals (records.size(), modelData.length);
            
            //Compare each record against the original for consistency
            for (int i = 0; i < records.size(); i++) {
                Object[] row = records.get(i).toArray();
                Assert.assertArrayEquals (row, modelData[i]);
                for (int j = 0; j < modelData[i].length; j++) {
                    assertEquals (row[j], model.getValueAt(i, j));
                }
            }
            
            //Ensure edited data is consistent
            model.setValueAt("Test", 0, 0);
            assertEquals ("Test", model.getValueAt(0, 0));
            
            //Retrieve records from the file system (Original Records)
            ArrayList<Student> students = rm.readStudents(new File ("students.csv"));
            
            //Set records as current data in the model
            model.setStudents(students);
            
            //Retrieve records from the model (Retrieved Records)
            modelData = model.getData();
            
            //Check the number of records is consistent
            assertEquals (students.size(), model.getRowCount());
            assertEquals (students.size(), modelData.length);
            
            //Compare each record against the original for consistency
            for (int i = 0; i < students.size(); i++) {
                Object[] row = students.get(i).toArray();
                Assert.assertArrayEquals(row, modelData[i]);
                for (int j = 0; j < modelData[i].length; j++) {
                    assertEquals (row[j], model.getValueAt(i, j));
                }
            }
            
            //Ensure edited data is consistent
            model.setValueAt("Test", 0, 0);
            assertEquals ("Test", model.getValueAt(0, 0));
        }
        catch (IOException ex) {
            ex.printStackTrace ();
        }
    }
    
    /*
    Test the dynamic column names functionality
    */
    @Test
    public void columnTest () {
        //Check there are no columns before columns are set
        assertEquals (0, model.getColumnCount());
        
        //Set RIE Record columns as the current columns
        model.setColumnNames(RIERecord.columnNames);
        
        //Check the number of columns is consistent
        assertEquals (RIERecord.columnNames.length, model.getColumnCount());
        //Check each column name is consistent
        for (int i = 0; i < model.getColumnCount(); i++) {
            assertEquals (RIERecord.columnNames[i], model.getColumnName(i));
        }
        
        //Set Student columns as the current columns
        model.setColumnNames(Student.columnNames);
        
        //Check the number is consistent
        assertEquals (Student.columnNames.length, model.getColumnCount());
        //Check each column name is consistent
        for (int i = 0; i < model.getColumnCount(); i++) {
            assertEquals (Student.columnNames[i], model.getColumnName(i));
        }
    }
}
