
import java.io.IOException;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author eujing
 */
public class RecordTableController {
    RecordTableModel model = new RecordTableModel ();
    JTable tbRecords;
    
    public RecordTableController (JTable tbRecords) {
        this.tbRecords = tbRecords;
    }
    
    public RecordTableModel getModel () {
        return model;
    }
    
    public void displayRIERecords () {
        try {
            model.setColumnNames(new String[] {"UserID", "Category", "Title", "Desc1", "Desc2", "Award", "Year", "Grade"});
            TableColumn gradeColumn = tbRecords.getColumnModel().getColumn(7);
            JComboBox cbGrades = new JComboBox();
            cbGrades.addItem("");
            cbGrades.addItem("Excellent");
            cbGrades.addItem("Merit");
            cbGrades.addItem("Satisfactory");
            gradeColumn.setCellEditor(new DefaultCellEditor(cbGrades));
            model.setRIERecords();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
