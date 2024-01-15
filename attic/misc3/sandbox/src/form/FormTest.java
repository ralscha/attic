package form;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.AbstractTableModel;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.managestar.recurrance.Recurrance;

public class FormTest extends JFrame {

  private JFormattedTextField startDate;
  private JFormattedTextField startTime;

  private JFormattedTextField endDate; 
  private JFormattedTextField endTime;
          
  private JFormattedTextField fromDate;
  private JFormattedTextField fromTime;    
    
  private JFormattedTextField toDate;   
  private JFormattedTextField toTime;
  private JComboBox rule; 
  private DefaultComboBoxModel ruleCombo; 

  

  private JTable table;
  private MyTableModel tableModel;

  private JTextField status;
  

  public FormTest() {
    
    try {
      initComponents();
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  private void initComponents() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
    
    UIManager.setLookAndFeel("com.jgoodies.plaf.plastic.PlasticXPLookAndFeel");
    
    setTitle("FormTest");
    setSize(300, 500);
    getContentPane().setLayout(new BorderLayout());
    
  
    Calendar cal = Calendar.getInstance();
    cal.setMinimalDaysInFirstWeek(4);
      
        
    
    startDate = new JFormattedTextField(SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT));
    startDate.setValue(cal.getTime());
    startTime = new JFormattedTextField(new SimpleDateFormat("HH:mm:ss"));
    startTime.setValue(cal.getTime());


    endDate = new JFormattedTextField(SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT));
    endDate.setValue(cal.getTime());
    endTime = new JFormattedTextField(new SimpleDateFormat("HH:mm:ss"));
    endTime.setValue(cal.getTime());

        
    fromDate = new JFormattedTextField(SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT));
    fromDate.setValue(cal.getTime());
    fromTime = new JFormattedTextField(new SimpleDateFormat("HH:mm:ss"));
    fromTime.setValue(cal.getTime());    
    
    toDate = new JFormattedTextField(SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT));
    toDate.setValue(cal.getTime());
    toTime = new JFormattedTextField(new SimpleDateFormat("HH:mm:ss"));
    toTime.setValue(cal.getTime()); 
    
    
    ruleCombo = new DefaultComboBoxModel();
    ruleCombo.addElement("FREQ=YEARLY");
    ruleCombo.addElement("FREQ=MONTHLY");
    ruleCombo.addElement("FREQ=DAILY");
    ruleCombo.addElement("FREQ=HOURLY");
    ruleCombo.addElement("FREQ=MINUTELY");  
    ruleCombo.addElement("FREQ=SECONDLY");
    rule = new JComboBox(ruleCombo);  
    
    rule.setEditable(true);  
        
    FormLayout layout = new FormLayout(
    "right:max(30dlu;p), 4dlu, p, 4dlu, p, 4dlu, p, 1px:grow", "p, 2dlu, p, 3dlu, p, 3dlu, p, 7dlu, " +
                                                               "p, 2dlu, p, 3dlu, p, 10dlu, p, 7dlu," +
                                                               "p, 2dlu, d:grow");
        
    PanelBuilder builder = new PanelBuilder(layout);
    builder.setDefaultDialogBorder();
    CellConstraints cc = new CellConstraints();
    
    builder.addSeparator("Rule", cc.xywh(1, 1, 8, 1));
    builder.addLabel("Start", cc.xy(1, 3));
    builder.add(startDate, cc.xy(3, 3));
    builder.add(startTime, cc.xy(5, 3));

    builder.addLabel("End", cc.xy(1, 5));
    builder.add(endDate, cc.xy(3, 5));
    builder.add(endTime, cc.xy(5, 5));

    builder.addLabel("Rule", cc.xy(1,7));
    builder.add(rule, cc.xywh(3,7,6,1));

    builder.addSeparator("Filter", cc.xywh(1, 9, 8, 1));
    
    
    builder.addLabel("From", cc.xy(1,11));
    builder.add(fromDate, cc.xy(3,11));
    builder.add(fromTime, cc.xy(5,11));
    
    builder.addLabel("To", cc.xy(1,13));
    builder.add(toDate, cc.xy(3,13));
    builder.add(toTime, cc.xy(5,13));
    
        
    JButton show = new JButton("Show");
    show.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        compute();
      }
    });
    builder.add(show, cc.xywh(3,15,3,1));
    
    builder.addSeparator("Result", cc.xywh(1,17,8,1));
    
    
    tableModel = new MyTableModel();
    table = new JTable(tableModel);
    JScrollPane scrollPane = new JScrollPane(table);
    
    table.setFont(new Font("Courier New", Font.PLAIN, 11));
    
    builder.add(scrollPane, cc.xywh(1,19,8,1));
   
    
    
    

    
    
    getContentPane().add(builder.getPanel(), BorderLayout.CENTER);
    
    
    status = new JTextField("Ready");
    status.setEditable(false);
    getContentPane().add(status, BorderLayout.SOUTH);

    

    
    //Menu
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu();
    fileMenu.setText("File");
    
    JMenuItem exitItem = new JMenuItem();
    exitItem.setText("Exit");
    exitItem.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
           System.exit(0);
        }
    });

    fileMenu.add(exitItem);
    menuBar.add(fileMenu);
    setJMenuBar(menuBar); 
    
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    
    //pack();
  }

  protected void compute() {
    String ruleText = (String)rule.getSelectedItem();
    if ((ruleText != null) && (!ruleText.trim().equals(""))) {
      
      
      Calendar startCal = getCal((Date)startDate.getValue(), (Date)startTime.getValue());
      Calendar endCal = getCal((Date)endDate.getValue(), (Date)endTime.getValue());
      
      Calendar fromCal = getCal((Date)fromDate.getValue(), (Date)fromTime.getValue());
      Calendar toCal = getCal((Date)toDate.getValue(), (Date)toTime.getValue());

      
      if (ruleCombo.getIndexOf(ruleText) < 0) {
        ruleCombo.addElement(ruleText);
      }
      
      Recurrance r = new Recurrance(ruleText, startCal, endCal);
      
      if ((fromCal != null) && (toCal != null)) {
        tableModel.setList(r.getAllMatchingDatesOverRange(fromCal, toCal));
      } else {
        tableModel.setList(r.getAllMatchingDates());
      }
      
  
    }
    
  }

  private Calendar getCal(Date date, Date time) {
    
    Calendar cal = null;
    
    if (date != null) {
      cal = new GregorianCalendar();
      cal.clear();
      
      Calendar tmp = new GregorianCalendar();
      tmp.setTime(date);
      cal.set(Calendar.DATE, tmp.get(Calendar.DATE));
      cal.set(Calendar.MONTH, tmp.get(Calendar.MONTH));
      cal.set(Calendar.YEAR, tmp.get(Calendar.YEAR));
    
      if (time != null) {
        tmp = new GregorianCalendar();
        tmp.setTime(time);      
    
        cal.set(Calendar.HOUR_OF_DAY, tmp.get(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, tmp.get(Calendar.MINUTE));
        cal.set(Calendar.SECOND, tmp.get(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, 0);                      
      }        
                     
    }
    return cal;
  }

  public static void main(String[] args) {
    
    new FormTest().setVisible(true);
  }
  
  
  
  class MyTableModel extends AbstractTableModel {
      final String[] columnNames = new String[]{"Events"};
      java.util.List rows = new ArrayList();

      public int getColumnCount() {
          return columnNames.length;
      }
    
      public int getRowCount() {
          return rows.size();
      }

      public String getColumnName(int col) {
          return columnNames[col];
      }

      public Object getValueAt(int row, int col) {        
          return ((Calendar)rows.get(row)).getTime().toString();
      }


      public void setList(java.util.List l) {
        rows = l;
        fireTableDataChanged();
      }
  }

}
