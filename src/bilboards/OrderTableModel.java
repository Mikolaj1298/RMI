package bilboards;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class OrderTableModel extends AbstractTableModel {
	private String[] columnNames = {"ID", "Actual Advert", "Capacity", "Interval", "Enabled"};
	private List<ArrayList<Object>> data = new ArrayList<ArrayList<Object>>();

	@Override
	public int getRowCount() {
		return data.size();
	}
	
	@Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override
	public boolean isCellEditable(int row, int col) {
		return col == 4 || col == 5;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data.get(rowIndex).get(columnIndex);
	}
	
	@Override
    public void setValueAt(Object value, int row, int col) {
    	data.get(row).set(col, value);
        fireTableCellUpdated(row, col);
    }
	
	@Override
	public Class<? extends Object> getColumnClass(int c) {
		if(c == 4)
			return String.class;
		
        return getValueAt(0, c).getClass();
    }
	
	public void addRow(Object[] newData) {
		ArrayList<Object> newRow = new ArrayList<>();
		
		for (int i = 0; i < newData.length; i++)
			newRow.add(newData[i]);
			
		data.add(newRow);
	}
	
	public void removeRow(int id) {
		ArrayList<Object> toRemove = null;
		
		for (ArrayList<Object> row : data)
			if ((int) row.get(0) == id) {
				toRemove = row;
				break;
			}
		
		data.remove(toRemove);
	}
	
	public int getRow(int id) {
		for (ArrayList<Object> row : data)
			if ((int) row.get(0) == id)
				return data.indexOf(row);
		
		return -1;
	}
}
