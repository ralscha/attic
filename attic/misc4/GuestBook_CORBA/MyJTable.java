import java.awt.*;
import java.awt.event.*;
import com.sun.java.swing.*;
import com.sun.java.swing.event.*;
import com.sun.java.swing.table.*;
class MyJTable extends JTable implements ChangeListener {
    protected JScrollPane scrollPane = null;
    protected JScrollBar bar = null;
    protected boolean toLast = false;
        
    public MyJTable(TableModel data) {
        super(data);
    }

    public void tableChanged(TableModelEvent event) {
        super.tableChanged(event);
        if (event.getType() == TableModelEvent.INSERT) {
            toLast = true;
        }
    }
        
    public void stateChanged(ChangeEvent event) {
        if (toLast) {
            toLast = false;
            selectLastRow();
        }
    }

    public void selectLastRow() {
        int newIndex = getRowCount() - 1;
        Rectangle rect = getCellRect(newIndex, 0, true);
        bar.setValue(rect.y);
        //              scrollPane.getVerticalScrollBar().setValue(scroll);
        setRowSelectionInterval(newIndex, newIndex);
    }
                                
    public JScrollPane createOwnScrollPane() {
        scrollPane = new JScrollPane(this);
        bar = scrollPane.getVerticalScrollBar();
        bar.getModel().addChangeListener(this);
        return scrollPane;
    }
}