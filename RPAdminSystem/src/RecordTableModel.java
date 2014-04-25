
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author eujing
 */
public class RecordTableModel extends AbstractTableModel {

    private String[] columnNames;
    private Object[][] data;
    private ResourcesManager rm;
    private boolean changed;
    
    public RecordTableModel () {
        rm = new ResourcesManager ();
        changed = false;
    }
    
    public void setRIERecords (ArrayList<Record> records) throws IOException {
        Object[][] array = new Object[records.size()][];
        for (int i = 0; i < array.length; i++) {
            array[i] = records.get(i).toArray();
        }
        setData (array);
    }
    
    public void exportRIERecords (File outFile) throws IOException {
        if (!Arrays.equals(columnNames, Record.columnNames)) {
            throw new IllegalStateException ("Not storing RIE Records now");
        }
        ArrayList<Record> records = new ArrayList<> ();
        for (Object[] row : data) {
            records.add(Record.fromArray(row));
        }
        rm.writeRIERecords(records, outFile);
    }
    
    public void setStudents (ArrayList<Student> students) throws IOException {
        Object[][] array = new Object[students.size()][];
        for (int i = 0; i < array.length; i++) {
            array[i] = students.get(i).toArray();
        }
        setData (array);
    }
    
    public void exportStudents (File outFile) throws IOException {
        if (!Arrays.equals(columnNames, Student.columnNames)) {
            throw new IllegalStateException ("Not storing Students now");
        }
        ArrayList<Student> students = new ArrayList<> ();
        for (Object[] row : data) {
            students.add(Student.fromArray(row));
        }
        rm.writeStudents(students, outFile);
    }
    
    public void setColumnNames (String[] columnNames) {
        this.columnNames = columnNames;
        this.fireTableStructureChanged();
    }
    
    public void setData (Object[][] data) {
        this.data = data;
        this.fireTableDataChanged();
    }
    
    public Object[][] getData () {
        return data;
    }
    
    public ResourcesManager getResourcesManager () {
        return rm;
    }
    
    public boolean getChanged () {
        return changed;
    }
    
    public void setChanged (boolean changed) {
        this.changed = changed;
    }
    
    @Override
    public int getRowCount() {
        if (data == null) {
            return 0;
        }
        return data.length;
    }

    @Override
    public int getColumnCount() {
        if (columnNames == null) {
            return 0;
        }
        return columnNames.length;
    }
    
    @Override
    public String getColumnName(int index) {
        if (columnNames == null) {
            return super.getColumnName(index);
        }
        return columnNames[index];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }
    
    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
    
    @Override
    public boolean isCellEditable(int row, int col) {
        return true;
    }
    
    @Override
    public void setValueAt (Object value, int row, int col) {
        data[row][col] = value;
        changed = true;
        fireTableCellUpdated(row, col);
    }
}
