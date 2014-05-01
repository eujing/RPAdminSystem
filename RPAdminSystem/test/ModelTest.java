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

    @Test
    public void dataTest () {
        assertEquals (0, model.getRowCount());
        
        ResourcesManager rm = new ResourcesManager ();
        try {
            ArrayList<RIERecord> records = rm.readRIERecords(new File ("RIE_records.csv"));
            model.setRIERecords(records);
            
            Object[][] modelData = model.getData();
            
            assertEquals (records.size(), model.getRowCount());
            assertEquals (records.size(), modelData.length);
            for (int i = 0; i < records.size(); i++) {
                Object[] row = records.get(i).toArray();
                Assert.assertArrayEquals (row, modelData[i]);
                for (int j = 0; j < modelData[i].length; j++) {
                    assertEquals (row[j], model.getValueAt(i, j));
                }
            }
            
            model.setValueAt("Test", 0, 0);
            assertEquals ("Test", model.getValueAt(0, 0));
            
            ArrayList<Student> students = rm.readStudents(new File ("students.csv"));
            model.setStudents(students);
            
            modelData = model.getData();
            
            assertEquals (students.size(), model.getRowCount());
            assertEquals (students.size(), modelData.length);
            for (int i = 0; i < students.size(); i++) {
                Object[] row = students.get(i).toArray();
                Assert.assertArrayEquals(row, modelData[i]);
                for (int j = 0; j < modelData[i].length; j++) {
                    assertEquals (row[j], model.getValueAt(i, j));
                }
            }
            
            model.setValueAt("Test", 0, 0);
            assertEquals ("Test", model.getValueAt(0, 0));
        }
        catch (IOException ex) {
            ex.printStackTrace ();
        }
    }
    
    @Test
    public void columnTest () {
        assertEquals (0, model.getColumnCount());
        model.setColumnNames(RIERecord.columnNames);
        
        assertEquals (RIERecord.columnNames.length, model.getColumnCount());
        for (int i = 0; i < model.getColumnCount(); i++) {
            assertEquals (RIERecord.columnNames[i], model.getColumnName(i));
        }
        
        model.setColumnNames(Student.columnNames);
        
        assertEquals (Student.columnNames.length, model.getColumnCount());
        for (int i = 0; i < model.getColumnCount(); i++) {
            assertEquals (Student.columnNames[i], model.getColumnName(i));
        }
    }
}
