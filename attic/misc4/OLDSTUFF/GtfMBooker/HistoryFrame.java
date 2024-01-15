
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.awt.*;

public class HistoryFrame extends JFrame {

	public HistoryFrame(CLTableModel cltablemodel, FLTableModel fltablemodel) {
		super("Liability History");
		addWindowListener(new HistoryFrameWindowAdapter());
		
		JTable contTable = new JTable();
		JTable firmTable = new JTable();
		
		contTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
		firmTable.setPreferredScrollableViewportSize(new Dimension(500, 70));


		TableSorter clSorter = new TableSorter(cltablemodel);
		contTable.setModel(clSorter);
		clSorter.addMouseListenerToHeaderInTable(contTable);
            
		for (int i = 0; i < cltablemodel.getColumnCount(); i++) {            
		    TableColumn col = contTable.getColumn(cltablemodel.getColumnName(i));            
		    DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		    dtcr.setHorizontalAlignment(cltablemodel.getAlignment(i));                
		    col.setCellRenderer(dtcr);   
		    col.setPreferredWidth(cltablemodel.getWidth(i));
		}
		clSorter.sortByColumn(2) ;
		
		TableSorter flSorter = new TableSorter(fltablemodel);
		firmTable.setModel(flSorter);
		flSorter.addMouseListenerToHeaderInTable(firmTable);
		
		for (int i = 0; i < fltablemodel.getColumnCount(); i++) {
	       TableColumn col = firmTable.getColumn(fltablemodel.getColumnName(i));            
	       DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
	       dtcr.setHorizontalAlignment(fltablemodel.getAlignment(i));                
	       col.setCellRenderer(dtcr);   
	       col.setPreferredWidth(fltablemodel.getWidth(i));
		}
		flSorter.sortByColumn(1);
		
		JTabbedPane historyTabbedPane = new JTabbedPane();
		
		JScrollPane clScrollPane = new JScrollPane(contTable);
		JScrollPane flScrollPane = new JScrollPane(firmTable);
		
		contTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		firmTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		historyTabbedPane.add("Contingent", clScrollPane);
		historyTabbedPane.add("Firm", flScrollPane);
		
		getContentPane().add(historyTabbedPane);
		
		setSize(500,200);
	}
	
	public void showFrame() {
		setVisible(true);
	}
	
	private class HistoryFrameWindowAdapter extends WindowAdapter {
		public void windowClosing(WindowEvent windowevent) {
			JFrame frame = (JFrame)windowevent.getSource();
			frame.setVisible(false);
		}
	}
}