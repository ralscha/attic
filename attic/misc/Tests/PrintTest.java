
import java.awt.*;
import java.util.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import inetsoft.report.*;
import inetsoft.report.lens.swing.*;
import inetsoft.report.j2d.*;
import java.awt.print.*;

/**
* This example shows print a JTable (swing).
*
* @version 1.0, 5/23/98
* @author InetSoft Technology Corp
*/
public class PrintTest extends Frame {
	public PrintTest() {
		super("JTable Report");
		final String header[] = {"Company","Close","P/E", "Yield", "ROE"};
		final String[][] data = { {"ADE CORPORATION", "8.38", "13.7", "0.0", "4.8"}, {"AEHR TEST SYSTEMS",
                          		"3.63", "10.1", "0.0", "6.0"}, {"AG ASSOCIATES INC", "2.75", "NE", "0.0",
                                                          		"NE"},{"RELIABILITY INC", "4.47", "3.3", "0.0", "39.3"}, {"SEMITOOL INC",
                                                                  		"6.44", "8.9", "0.0", "15.3"}, };
		// create testing data
		TableModel model = new AbstractTableModel() {
                   			public Object getValueAt(int r, int c) {
               				if (r >= data.length) {
                   					return null;
                   				}
               				if (c >= data[r].length) {
                   					return null;
                   				}
                   				return data[r][c];
                   			}
                   			public int getRowCount() {
                   				return data.length;
                   			}
                   			public int getColumnCount() {
                   				return data[0].length;
                   			}
                   			public String getColumnName(int c) {
                   				return header[c];
                   			}
                   			public Class getColumnClass(int c) {
                   				return String.class;
                   			}
                   		};
		table = new JTable(model);
		add(JTable.createScrollPaneForTable(table), "Center");
		Panel pnl = new Panel();
		add(pnl, "South");
		Button printB = new Button("Print");
		pnl.add(printB);
		printB.addActionListener(new ActionListener() {
                         			public void actionPerformed(ActionEvent e) {
                         				StyleSheet report = createReport();
											
											try {
											PDFPrinter pdf = new PDFPrinter(new File("report.pdf"));
											report.print(pdf.getPrintJob());
											pdf.close(); // or pdf.getPrintJob().end();
											} catch(IOException ioe) { System.err.println(ioe); }
											/*
											PrinterJob job = StylePrinter.getPrinterJob();
											PageFormat fmt = new PageFormat();
											fmt = job.pageDialog(fmt);
											
											StyleBook book = new StyleBook(report, fmt);
											job.setPageable(book);
											if (job.printDialog()) {
												try {
												job.print();
												} catch(PrinterException pe) { 
													System.err.println(pe);
												}
											}
											*/
											/*
											PreviewView previewer = Previewer.createPreviewer();
											previewer.pack();
											previewer.setVisible(true);
											previewer.print(report);
											*/
                         			}
                         		}
                        		);
		Button quitB = new Button("Quit");
		pnl.add(quitB);
		quitB.addActionListener(new ActionListener() {
                        			public void actionPerformed(ActionEvent e) {
                        				System.exit(0);
                        			}
                        		}
                       		);
	}
	public StyleSheet createReport() {
		StyleSheet report = new StyleSheet();
		// create a table lens
		JTableLens lens = new JTableLens(table);
		
		report.addTable(new inetsoft.report.style.Elegant(lens));
		
		return report;
	}
	private JTable table;
	public static void main(String[] args) {
		PrintTest win = new PrintTest();
		win.pack();
		win.setVisible(true);
	}
}