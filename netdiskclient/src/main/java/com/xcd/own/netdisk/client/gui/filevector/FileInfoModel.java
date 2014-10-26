package com.xcd.own.netdisk.client.gui.filevector;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;

/**
 * Created by Administrator on 14-10-26.
 */
public class FileInfoModel extends AbstractTableModel {
    private Vector rowData, columnNames; //rowData用来存放行数据，colunnNames存放列名

    public FileInfoModel(Vector resultSet) {
        columnNames = new Vector();
        columnNames.add("文件名");
        columnNames.add("文件路径");
        columnNames.add("文件大小");
        columnNames.add("文件拥有者");
        columnNames.add("IP地址");
        columnNames.add("端口号");
        columnNames.add("是否在线");

        rowData = new Vector();
        if (resultSet != null) {
            rowData = resultSet;
        }
    }

    public Vector getRowData() {
        return rowData;
    }

    public void setRowData(Vector rowData) {
        this.rowData = rowData;
    }

    public Vector getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(Vector columnNames) {
        this.columnNames = columnNames;
    }

    @Override
    public int getColumnCount() {
        // TODO Auto-generated method stub
        return this.columnNames.size();
    }

    @Override
    public int getRowCount() {
        // TODO Auto-generated method stub
        return this.rowData.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        // TODO Auto-generated method stub
        return (String) ((Vector) this.rowData.get(rowIndex)).get(columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        // TODO Auto-generated method stub
        return (String) this.columnNames.get(column);
    }
}
