
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableColumn;

/**
 *
 * @author eujing
 */
public class RecordTableController {
    RecordTableModel model = new RecordTableModel ();
    JTable tbRecords;
    String currentDisplay;
    
    public RecordTableController (JTable tbRecords) {
        this.tbRecords = tbRecords;
    }
    
    public RecordTableModel getModel () {
        return model;
    }
    
    public void changeDisplay (String option) {
        if (model.getChanged()) {
            int reply = JOptionPane.showConfirmDialog(null, "There are unsaved edits, would you like to export the current table?");
            if (reply == JOptionPane.YES_OPTION) {
                if (currentDisplay != null) {
                    exportRecords (currentDisplay);
                }
            }
            model.setChanged(false);
        }
        
        JFileChooser chooser = new JFileChooser ();
        FileNameExtensionFilter filter = new FileNameExtensionFilter ("Excel 2007", "xls");
        chooser.setFileFilter (filter);
        try {
            switch (option) {
                case "RIE Records":
                    chooser.setDialogTitle ("Open RIE records file");
                    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                        displayRIERecords (chooser.getSelectedFile());
                        currentDisplay = "RIE Records";
                    }
                    break;
                case "Non submissions":
                    File records = null;
                    File students = null;
                    chooser.setDialogTitle ("Open RIE records file");
                    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                        records = chooser.getSelectedFile();
                    }
                    chooser.setDialogTitle ("Open students list file");
                    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                        students = chooser.getSelectedFile();
                    }
                    if (records != null && students != null) {
                        displayNonSubmissions (records, students);
                        currentDisplay = "Non submissions";
                    }
                    break;
                case "Synopsis Discrepencies":
                    break;
            }
        }
        catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    private void displayRIERecords (File inFile) {
        try {
            model.setColumnNames(Record.columnNames);
            TableColumn gradeColumn = tbRecords.getColumnModel().getColumn(7);
            JComboBox cbGrades = new JComboBox();
            cbGrades.addItem("");
            cbGrades.addItem("Excellent");
            cbGrades.addItem("Merit");
            cbGrades.addItem("Satisfactory");
            gradeColumn.setCellEditor(new DefaultCellEditor(cbGrades));
            model.setRIERecords(model.getResourcesManager().readRIERecords(inFile));
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void displayNonSubmissions (File recordsInFile, File studentsInFile) {
        try {
            model.setColumnNames(Student.columnNames);
            ArrayList<Student> students = model.getResourcesManager().readStudents(studentsInFile);
            ArrayList<Record> records = model.getResourcesManager().readRIERecords(recordsInFile);
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
        JFileChooser chooser = new JFileChooser ();
        chooser.setDialogTitle("Save as");
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                switch (option) {
                    case "RIE Records":
                        model.exportRIERecords(chooser.getSelectedFile());
                        JOptionPane.showMessageDialog(null, "RIE Records successfully exported!");
                        break;
                    case "Non submissions":
                        model.exportStudents(chooser.getSelectedFile());
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
}
