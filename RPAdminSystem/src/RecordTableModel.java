
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author eujing
 */
public class RecordTableModel extends AbstractTableModel {

    private String[] columnNames;
    private Object[][] data;
    
    public void setColumnNames (String[] columnNames) {
        this.columnNames = columnNames;
        this.fireTableStructureChanged();
    }
    
    public void setData (Object[][] data) {
        this.data = data;
        this.fireTableDataChanged();
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
        fireTableCellUpdated(row, col);
    }
}
