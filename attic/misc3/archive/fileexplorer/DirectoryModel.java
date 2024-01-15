package fileexplorer;

import javax.swing.*;
import javax.swing.table.*;
import java.io.*;

public class DirectoryModel extends AbstractTableModel {
    protected File directory;
    protected String[] children;
    protected int rowCount;
    protected Object dirIcon;
    protected Object fileIcon;

    public DirectoryModel() {
        init();
    }

    public DirectoryModel( File dir ) {
        init();
        directory = dir;
        children = dir.list();
        rowCount = children.length;
    }

    protected void init() {
        dirIcon = UIManager.get( "FileView.directoryIcon" );
        fileIcon = UIManager.get( "FileView.fileIcon" );
    }

    public void setDirectory( File dir ) {
        if ( dir != null ) {
            directory = dir;
            children = dir.list();
            rowCount = children.length;
        }
        else {
            directory = null;
            children = null;
            rowCount = 0;
        }
        fireTableDataChanged();
    }

    public int getRowCount() {
        return children != null ? rowCount : 0;
    }

    public int getColumnCount() {
        return children != null ? 3 :0;
    }

    public Object getValueAt(int row, int column){
        if ( directory == null || children == null ) {
            return null;
        }

        File fileSysEntity = new File( directory, children[row] );

        switch ( column ) {
        case 0:
            return fileSysEntity.isDirectory() ? dirIcon : fileIcon;
        case 1:
            return fileSysEntity.getName();
        case 2:
            if ( fileSysEntity.isDirectory() ) {
                return "--";
            }
            else {
                return new Long( fileSysEntity.length() );
            }
        default:
            return "";
        }
    }

    public String getColumnName( int column ) {
        switch ( column ) {
        case 0:
            return "Type";
        case 1:
            return "Name";
        case 2:
            return "Bytes";
        default:
            return "unknown";
        }
    }

    public Class getColumnClass( int column ) {
        if ( column == 0 ) {
            return getValueAt( 0, column).getClass();
        }
        else {
            return super.getColumnClass( column );
        }
    }
}                   

