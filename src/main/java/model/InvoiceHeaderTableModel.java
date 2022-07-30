package model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Mahmoud
 */
public class InvoiceHeaderTableModel extends AbstractTableModel {

    private ArrayList<InvoiceHeader> data;
    private String cols[] = {"ID", "Customer Name", "Invoice Date"};

    public InvoiceHeaderTableModel(ArrayList<InvoiceHeader> data) {
        this.data = data;
    }
    
    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return cols.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceHeader header = data.get(rowIndex);
        switch(columnIndex){
            case 0: 
                return header.getInvoiceNum();
            case 1:
                return header.getCustomerName();
            case 2:
                return header.getInvoiceDate();
        }
        return "";
    }

    @Override
    public String getColumnName(int column) {
        return cols[column];
    }
    
    
}
