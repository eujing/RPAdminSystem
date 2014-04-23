
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
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
    
    public void changeDisplay (String option) {
        switch (option) {
            case "RIE Records":
                displayRIERecords ();
                break;
            case "Non submissions":
                displayNonSubmissions ();
                break;
            case "Synopsis Discrepencies":
                break;
        }
    }
    
    private void displayRIERecords () {
        try {
            model.setColumnNames(Record.columnNames);
            TableColumn gradeColumn = tbRecords.getColumnModel().getColumn(7);
            JComboBox cbGrades = new JComboBox();
            cbGrades.addItem("");
            cbGrades.addItem("Excellent");
            cbGrades.addItem("Merit");
            cbGrades.addItem("Satisfactory");
            gradeColumn.setCellEditor(new DefaultCellEditor(cbGrades));
            model.setRIERecords(model.getResourcesManager().readRIERecords());
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void displayNonSubmissions () {
        try {
            model.setColumnNames(Student.columnNames);
            ArrayList<Student> students = model.getResourcesManager().readStudents();
            ArrayList<Record> records = model.getResourcesManager().readRIERecords();
            ArrayList<Student> nonSubmissions = new ArrayList <> ();
            final String ARP_DETAILS = "Details of Advanced Research Project";
            
            for (Student s : students) {
                boolean submitted = false;
                
                for (Record r : records) {
                    if (r.getCategory().equals(ARP_DETAILS) && s.getUserid().equals(r.getUserid())) {
                        submitted = true;
                        break;
                    }
                }
                
                if (!submitted) {
                    nonSubmissions.add(s);
                }
            }
            
            model.setStudents(nonSubmissions);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void exportRecords (String option) {
        try {
            switch (option) {
                case "RIE Records":
                    model.exportRIERecords();
                    JOptionPane.showMessageDialog(null, "RIE Records successfully exported!");
                    break;
                case "Non submissions":
                    model.exportStudents();
                    JOptionPane.showMessageDialog(null, "Non submissions successfully exported!");
                    break;
                case "Synopsis Discrepencies":
                    break;
            }
        }
        catch (IOException ex) {
            ex.printStackTrace ();
        }
    }
}
