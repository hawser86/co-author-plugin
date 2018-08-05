package hu.hawser.coauthorplugin;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorTableModel extends AbstractTableModel {

    private class RowData {
        boolean selected;
        String author;

        RowData(boolean selected, String author) {
            this.selected = selected;
            this.author = author;
        }
    }

    private List<RowData> tableData;


    AuthorTableModel(List<String> authors) {
        tableData = authors.stream()
                .map(author -> new RowData(false, author))
                .collect(Collectors.toList());
    }


    @Override
    public int getRowCount() {
        return tableData.size();
    }


    @Override
    public int getColumnCount() {
        return 2;
    }


    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        RowData rowData = tableData.get(rowIndex);
        return columnIndex == 0 ? rowData.selected : rowData.author;
    }


    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            tableData.get(rowIndex).selected = (boolean)value;
        }
        if (columnIndex == 1) {
            tableData.get(rowIndex).author = (String)value;
        }
    }


    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }


    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }


    List<String> getSelectedAuthors() {
        return tableData.stream()
                .filter(rowData -> rowData.selected)
                .map(rowData -> rowData.author)
                .collect(Collectors.toList());
    }


    List<String> getAllAuthor() {
        return tableData.stream()
                .map(rowData -> rowData.author)
                .collect(Collectors.toList());
    }
}