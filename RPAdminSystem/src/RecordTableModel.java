
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author eujing
 */
public class RecordTableModel extends AbstractTableModel {

    private String[] columnNames;
    private ArrayList<Record> data;
    private RecordsManager rm = new RecordsManager ();
    
    public void setRIERecords () throws IOException {
        setData (rm.readRIERecords());
    }
    
    public void setColumnNames (String[] columnNames) {
        this.columnNames = columnNames;
        this.fireTableStructureChanged();
    }
    
    public void setData (ArrayList<Record> data) {
        this.data = data;
        this.fireTableDataChanged();
    }
    
    @Override
    public int getRowCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
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
        return data.get(rowIndex).toArray()[columnIndex];
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
        Object[] array = data.get(row).toArray();
        array[col] = value;
        data.set(row, Record.fromArray(array));
        fireTableCellUpdated(row, col);
    }
}
