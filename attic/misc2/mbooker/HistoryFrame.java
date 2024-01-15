
package gtf.mbooker;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.awt.*;
import com.klg.jclass.util.swing.JCSortableTable;
import common.swing.ExcelAdapter;

public class HistoryFrame extends JFrame {

	public HistoryFrame(CLTableModel cltablemodel, FLTableModel fltablemodel) {
		super("Liability History");
		addWindowListener(new HistoryFrameWindowAdapter());
			
		JCSortableTable contTable = new JCSortableTable(cltablemodel);
		JCSortableTable firmTable = new JCSortableTable(fltablemodel);
	
		new ExcelAdapter(contTable, true);
		new ExcelAdapter(firmTable, true);		

	
/*
	// We use the last name if the first name is the same.
    int sort0[] = {0, 1};
    table.setKeyColumns(0, sort0);

	// We use the first name if the last name is the same.
    int sort1[] = {1, 0};
    table.setKeyColumns(1, sort1);

	// We use person's name if the department is the same.
    int sort2[] = {2, 0, 1};
    table.setKeyColumns(2, sort2);
*/		
	
		for (int i = 0; i < fltablemodel.getColumnCount(); i++) {
	       TableColumn col = firmTable.getColumn(fltablemodel.getColumnName(i));            
	       DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
	       dtcr.setHorizontalAlignment(fltablemodel.getAlignment(i));                
	       col.setCellRenderer(dtcr);   
	       col.setPreferredWidth(fltablemodel.getWidth(i));
		}
		for (int i = 0; i < cltablemodel.getColumnCount(); i++) {            
		    TableColumn col = contTable.getColumn(cltablemodel.getColumnName(i));            
		    DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		    dtcr.setHorizontalAlignment(cltablemodel.getAlignment(i));                
		    col.setCellRenderer(dtcr);   
		    col.setPreferredWidth(cltablemodel.getWidth(i));
		}
		
		contTable.setPreferredScrollableViewportSize(new Dimension(600, 70));
		firmTable.setPreferredScrollableViewportSize(new Dimension(600, 70));

		JTabbedPane historyTabbedPane = new JTabbedPane();
		
		JScrollPane clScrollPane = new JScrollPane(contTable);
		JScrollPane flScrollPane = new JScrollPane(firmTable);
		
		contTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		firmTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		historyTabbedPane.add("Contingent", clScrollPane);
		historyTabbedPane.add("Firm", flScrollPane);
		
		getContentPane().add(historyTabbedPane);
		
		setSize(610,200);
	}
	
	
	private class HistoryFrameWindowAdapter extends WindowAdapter {
		public void windowClosing(WindowEvent windowevent) {
			JFrame frame = (JFrame)windowevent.getSource();
			frame.setVisible(false);
		}
	}
}