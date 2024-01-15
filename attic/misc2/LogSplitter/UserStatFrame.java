

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.print.*;
import java.util.*;
import java.text.*;
import java.io.*;
import javax.swing.*;
import javax.swing.table.*;

//import com.jrefinery.chart.*;
//import com.jrefinery.chart.data.*;
//import com.jrefinery.chart.ui.*;
//import com.jrefinery.util.ui.*;
import com.objectplanet.chart.*;

public class UserStatFrame extends JApplet {
  
  private boolean inAnApplet = true;

  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
  public final static DecimalFormat percentFormat  = new DecimalFormat("#,##0.00 '%'");

  private static final String[] TITLE = new String[]{"EMA", "Kunden", "Lieferanten", "PBroker"};

  private JTabbedPane tabbedPane;


  private UserStatReport us;

	DateFormatSymbols symbols = new DateFormatSymbols(); 
	String[] MONTHNAMES = symbols.getShortMonths();



  public UserStatFrame() {
    

    JPanel content = new JPanel(new BorderLayout());


    try {
      us = new UserStatReport(this);
    } catch (InterruptedIOException iioe) {
      System.exit(0);
    }

    tabbedPane = new JTabbedPane();

    JTabbedPane totalTabbedPane = new JTabbedPane();
    totalTabbedPane.add("Diagramm", getPieChart2());
    	
    totalTabbedPane.add("Tabelle", getTotalTable());
    tabbedPane.add("Total", totalTabbedPane);


    JTabbedPane inTabbedPane = new JTabbedPane();


    //HOUR
    JTabbedPane hourAlleTabbedPane = new JTabbedPane();
    hourAlleTabbedPane.add("Diagramm", getChart2(UserStatReport.STAT_HOUR, 0));
    hourAlleTabbedPane.add("Tabelle", getChartTable(UserStatReport.STAT_HOUR, 0));
    inTabbedPane.addTab("Alle", hourAlleTabbedPane);

    JTabbedPane hourEMATabbedPane = new JTabbedPane();
    hourEMATabbedPane.add("Diagramm", getChart2(UserStatReport.STAT_HOUR, StatItem.EMA_TYP));
    hourEMATabbedPane.add("Tabelle", getChartTable(UserStatReport.STAT_HOUR, StatItem.EMA_TYP));
    inTabbedPane.addTab("EMA", hourEMATabbedPane);

    JTabbedPane hourKundenTabbedPane = new JTabbedPane();
    hourKundenTabbedPane.add("Diagramm", getChart2(UserStatReport.STAT_HOUR, StatItem.KUNDE_TYP));
    hourKundenTabbedPane.add("Tabelle", getChartTable(UserStatReport.STAT_HOUR, StatItem.KUNDE_TYP));
    inTabbedPane.addTab("Kunden", hourKundenTabbedPane);

    JTabbedPane hourLieferantenTabbedPane = new JTabbedPane();
    hourLieferantenTabbedPane.add("Diagramm", getChart2(UserStatReport.STAT_HOUR, StatItem.LIEFERANT_TYP));
    hourLieferantenTabbedPane.add("Tabelle", getChartTable(UserStatReport.STAT_HOUR, StatItem.LIEFERANT_TYP));
    inTabbedPane.addTab("Lieferanten", hourLieferantenTabbedPane);
    
    JTabbedPane hourPBrokerTabbedPane = new JTabbedPane();
    hourPBrokerTabbedPane.add("Diagramm", getChart2(UserStatReport.STAT_HOUR, StatItem.PBROKER_TYP));
    hourPBrokerTabbedPane.add("Tabelle", getChartTable(UserStatReport.STAT_HOUR, StatItem.PBROKER_TYP));
    inTabbedPane.addTab("PBroker", hourPBrokerTabbedPane);

    tabbedPane.addTab("Stunden", inTabbedPane);


    //WEEKDAY
    inTabbedPane = new JTabbedPane();

    JTabbedPane weekdayAlleTabbedPane = new JTabbedPane();
    weekdayAlleTabbedPane.add("Diagramm", getChart2(UserStatReport.STAT_WEEKDAY, 0));
    weekdayAlleTabbedPane.add("Tabelle", getChartTable(UserStatReport.STAT_WEEKDAY, 0));
    inTabbedPane.addTab("Alle", weekdayAlleTabbedPane);
    
    JTabbedPane weekdayEMATabbedPane = new JTabbedPane();
    weekdayEMATabbedPane.add("Diagramm", getChart2(UserStatReport.STAT_WEEKDAY, StatItem.EMA_TYP));
    weekdayEMATabbedPane.add("Tabelle", getChartTable(UserStatReport.STAT_WEEKDAY, StatItem.EMA_TYP));
    inTabbedPane.addTab("EMA", weekdayEMATabbedPane);

    JTabbedPane weekdayKundenTabbedPane = new JTabbedPane();
    weekdayKundenTabbedPane.add("Diagramm", getChart2(UserStatReport.STAT_WEEKDAY, StatItem.KUNDE_TYP));
    weekdayKundenTabbedPane.add("Tabelle", getChartTable(UserStatReport.STAT_WEEKDAY, StatItem.KUNDE_TYP));
    inTabbedPane.addTab("Kunden", weekdayKundenTabbedPane);

    JTabbedPane weekdayLieferantenTabbedPane = new JTabbedPane();
    weekdayLieferantenTabbedPane.add("Diagramm", getChart2(UserStatReport.STAT_WEEKDAY, StatItem.LIEFERANT_TYP));
    weekdayLieferantenTabbedPane.add("Tabelle", getChartTable(UserStatReport.STAT_WEEKDAY, StatItem.LIEFERANT_TYP));
    inTabbedPane.addTab("Lieferanten", weekdayLieferantenTabbedPane);

    JTabbedPane weekdayPBrokerTabbedPane = new JTabbedPane();
    weekdayPBrokerTabbedPane.add("Diagramm", getChart2(UserStatReport.STAT_WEEKDAY, StatItem.PBROKER_TYP));
    weekdayPBrokerTabbedPane.add("Tabelle", getChartTable(UserStatReport.STAT_WEEKDAY, StatItem.PBROKER_TYP));
    inTabbedPane.addTab("PBroker", weekdayPBrokerTabbedPane);
            
    tabbedPane.addTab("Wochentage", inTabbedPane);


    //DAY

    inTabbedPane = new JTabbedPane();
    JTabbedPane dayAlleTabbedPane = new JTabbedPane();
    dayAlleTabbedPane.add("Diagramm", getChart2(UserStatReport.STAT_DAY, 0));
    dayAlleTabbedPane.add("Tabelle", getChartTable(UserStatReport.STAT_DAY, 0));
    inTabbedPane.addTab("Alle", dayAlleTabbedPane);

    JTabbedPane dayEMATabbedPane = new JTabbedPane();
    dayEMATabbedPane.add("Diagramm", getChart2(UserStatReport.STAT_DAY, StatItem.EMA_TYP));
    dayEMATabbedPane.add("Tabelle", getChartTable(UserStatReport.STAT_DAY, StatItem.EMA_TYP));
    inTabbedPane.addTab("EMA", dayEMATabbedPane);

    JTabbedPane dayKundenTabbedPane = new JTabbedPane();
    dayKundenTabbedPane.add("Diagramm", getChart2(UserStatReport.STAT_DAY, StatItem.KUNDE_TYP));
    dayKundenTabbedPane.add("Tabelle", getChartTable(UserStatReport.STAT_DAY, StatItem.KUNDE_TYP));
    inTabbedPane.addTab("Kunden", dayKundenTabbedPane);

    JTabbedPane dayLieferantenTabbedPane = new JTabbedPane();
    dayLieferantenTabbedPane.add("Diagramm", getChart2(UserStatReport.STAT_DAY, StatItem.LIEFERANT_TYP));
    dayLieferantenTabbedPane.add("Tabelle", getChartTable(UserStatReport.STAT_DAY, StatItem.LIEFERANT_TYP));
    inTabbedPane.addTab("Lieferanten", dayLieferantenTabbedPane);

    JTabbedPane dayPBrokerTabbedPane = new JTabbedPane();
    dayPBrokerTabbedPane.add("Diagramm", getChart2(UserStatReport.STAT_DAY, StatItem.PBROKER_TYP));
    dayPBrokerTabbedPane.add("Tabelle", getChartTable(UserStatReport.STAT_DAY, StatItem.PBROKER_TYP));
    inTabbedPane.addTab("PBroker", dayPBrokerTabbedPane);


    tabbedPane.addTab("Tage", inTabbedPane);


    //MONTH

    inTabbedPane = new JTabbedPane();

    JTabbedPane monthAlleTabbedPane = new JTabbedPane();
    monthAlleTabbedPane.add("Diagramm", getChart2(UserStatReport.STAT_MONTH, 0));
    monthAlleTabbedPane.add("Tabelle", getChartTable(UserStatReport.STAT_MONTH, 0));
    inTabbedPane.addTab("Alle", monthAlleTabbedPane);

    JTabbedPane monthEMATabbedPane = new JTabbedPane();
    monthEMATabbedPane.add("Diagramm", getChart2(UserStatReport.STAT_MONTH, StatItem.EMA_TYP));
    monthEMATabbedPane.add("Tabelle", getChartTable(UserStatReport.STAT_MONTH, StatItem.EMA_TYP));
    inTabbedPane.addTab("EMA", monthEMATabbedPane);

    JTabbedPane monthKundenTabbedPane = new JTabbedPane();
    monthKundenTabbedPane.add("Diagramm", getChart2(UserStatReport.STAT_MONTH, StatItem.KUNDE_TYP));
    monthKundenTabbedPane.add("Tabelle", getChartTable(UserStatReport.STAT_MONTH, StatItem.KUNDE_TYP));
    inTabbedPane.addTab("Kunden", monthKundenTabbedPane);

    JTabbedPane monthLieferantenTabbedPane = new JTabbedPane();
    monthLieferantenTabbedPane.add("Diagramm", getChart2(UserStatReport.STAT_MONTH, StatItem.LIEFERANT_TYP));
    monthLieferantenTabbedPane.add("Tabelle", getChartTable(UserStatReport.STAT_MONTH, StatItem.LIEFERANT_TYP));
    inTabbedPane.addTab("Lieferanten", monthLieferantenTabbedPane);

    JTabbedPane monthPBrokerTabbedPane = new JTabbedPane();
    monthPBrokerTabbedPane.add("Diagramm", getChart2(UserStatReport.STAT_MONTH, StatItem.PBROKER_TYP));
    monthPBrokerTabbedPane.add("Tabelle", getChartTable(UserStatReport.STAT_MONTH, StatItem.PBROKER_TYP));
    inTabbedPane.addTab("PBroker", monthPBrokerTabbedPane);
    
    tabbedPane.addTab("Monate", inTabbedPane);


    //DAUER

    inTabbedPane = new JTabbedPane();

    JTabbedPane durationAlleTabbedPane = new JTabbedPane();
    durationAlleTabbedPane.add("Diagramm", getChart2(UserStatReport.STAT_DURATION, 0));
    durationAlleTabbedPane.add("Tabelle", getChartTable(UserStatReport.STAT_DURATION, 0));
    inTabbedPane.addTab("Alle", durationAlleTabbedPane);

    JTabbedPane durationEMATabbedPane = new JTabbedPane();
    durationEMATabbedPane.add("Diagramm", getChart2(UserStatReport.STAT_DURATION, StatItem.EMA_TYP));
    durationEMATabbedPane.add("Tabelle", getChartTable(UserStatReport.STAT_DURATION, StatItem.EMA_TYP));
    inTabbedPane.addTab("EMA", durationEMATabbedPane);

    JTabbedPane durationKundenTabbedPane = new JTabbedPane();
    durationKundenTabbedPane.add("Diagramm", getChart2(UserStatReport.STAT_DURATION, StatItem.KUNDE_TYP));
    durationKundenTabbedPane.add("Tabelle", getChartTable(UserStatReport.STAT_DURATION, StatItem.KUNDE_TYP));
    inTabbedPane.addTab("Kunden", durationKundenTabbedPane);

    JTabbedPane durationLieferantenTabbedPane = new JTabbedPane();
    durationLieferantenTabbedPane.add("Diagramm", getChart2(UserStatReport.STAT_DURATION, StatItem.LIEFERANT_TYP));
    durationLieferantenTabbedPane.add("Tabelle", getChartTable(UserStatReport.STAT_DURATION, StatItem.LIEFERANT_TYP));
    inTabbedPane.addTab("Lieferanten", durationLieferantenTabbedPane);

    JTabbedPane durationPBrokerTabbedPane = new JTabbedPane();
    durationPBrokerTabbedPane.add("Diagramm", getChart2(UserStatReport.STAT_DURATION, StatItem.PBROKER_TYP));
    durationPBrokerTabbedPane.add("Tabelle", getChartTable(UserStatReport.STAT_DURATION, StatItem.PBROKER_TYP));
    inTabbedPane.addTab("PBroker", durationPBrokerTabbedPane);
    
    tabbedPane.addTab("Dauer", inTabbedPane);



    content.add(tabbedPane);

    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(content, BorderLayout.CENTER);

    JPanel titlePanel = new JPanel();
    titlePanel.setLayout(new BorderLayout());

    StringBuffer sb = new StringBuffer();
    sb.append(dateFormat.format(us.getReportFrom().getTime()));
    sb.append(" - ");
    sb.append(dateFormat.format(us.getReportTo().getTime()));

    titlePanel.add(new JLabel("Report User Logons: "+sb.toString(), SwingConstants.CENTER ), BorderLayout.CENTER);

    getContentPane().add(titlePanel, BorderLayout.NORTH);

   	  

  }

  public String getAppletInfo() {
	  return "UserStat\n author: Ralph Schaer";
  }

  private Chart getPieChart2() {
    
    Integer[][] reportValues = us.getTotal();
    double values[] = new double[reportValues.length];
    for (int i = 0; i < reportValues.length; i++) {
      values[i] = reportValues[i][0].intValue();
    }
    
    PieChart chart = new PieChart(values.length);
    chart.setSampleValues(0, values);
    chart.setSampleLabels(TITLE);

    chart.setLegendOn(true);
    chart.setSampleLabelsOn(true);
    chart.setValueLabelsOn(true);
    chart.setPercentLabelsOn(true);
    chart.setPercentDecimalCount(2);

    return chart;

  }


  private JComponent getTotalTable() {

    Integer[][] total = us.getTotal();
    Object[][] tableTotal = new Object[total.length][2];
    
    int grandeTotal = 0;
    
    for (int i = 0; i < total.length; i++) {
      tableTotal[i][0] = total[i][0];
      grandeTotal += total[i][0].intValue();
    }

    for (int i = 0; i < total.length; i++) {
      int itemNum = ((Integer)tableTotal[i][0]).intValue();
      tableTotal[i][1] = percentFormat.format(100.0 / grandeTotal * itemNum);
    }

    ReportTableModel model = new ReportTableModel(TITLE, new String[]{"", "Anzahl Logons", "Prozent"}, tableTotal);

    JTable totalTable = new JTable(model);

		for (int i = 0; i < model.getColumnCount(); i++) {
			TableColumn col = totalTable.getColumn(model.getColumnName(i));

			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
			dtcr.setHorizontalAlignment(model.getAlignment(i));
			col.setCellRenderer(dtcr);
			col.setPreferredWidth(model.getWidth(i));
	
		}

		//priviledgesTable.setPreferredScrollableViewportSize(new Dimension(150, 220));
		JScrollPane scrollPane = new JScrollPane(totalTable);

    return scrollPane;  
  }

  private Chart getChart2(int stat_typ, int typ) {

    String[] rowTitle = null;
    switch (stat_typ) {
      case UserStatReport.STAT_HOUR :
        rowTitle =  new String[]{"00", "01", "02", "03", "04","05", "06", "07", "08", "09", "10",
                                  "11", "12", "13", "14", "15","16", "17", "18", "19", "20",
                                  "21", "22", "23"};                                                                    
        break;
      case UserStatReport.STAT_WEEKDAY :
        rowTitle = new String[]{"Mo", "Di", "Mi", "Do", "Fr", "Sa", "So"};
        break;
      case UserStatReport.STAT_DAY :         
        String[] days = new String[31];
        for (int i = 0; i < days.length; i++) {
          days[i] = String.valueOf(i+1);
        }
        rowTitle = days;
        break;
      case UserStatReport.STAT_MONTH : 
        String[] months = new String[12];
        for (int i = 0; i < months.length; i++) {
          months[i] = MONTHNAMES[i];
        }
        rowTitle = months;
        break;
      case UserStatReport.STAT_DURATION :         
        String[] minutes = new String[121];        
        for (int i = 0; i < 120; i++) {
          if (i % 5 == 0)
            minutes[i] = String.valueOf(i);
          else
            minutes[i] = "";
        }
        minutes[120] = ">= 120";
        rowTitle = minutes;
        break;
    }

    Integer[][] total = us.getData(stat_typ, typ);
    double[] values = new double[total[0].length];
   
        
    for (int i = 0; i < total[0].length; i++) {
      values[i] = total[0][i].intValue();
    }


    BarChart chart = new BarChart(values.length);
    chart.setSampleValues(0,values);
    chart.setSampleLabels(rowTitle);

    chart.setValueLinesOn(true);
    chart.setBarLabelsOn(true);
    chart.setRange(us.getMax(stat_typ));
    
    return chart;
  
  }

  private JComponent getChartTable(int stat_typ, int typ) {
  
    String[] rowTitle = null;
    switch (stat_typ) {
      case UserStatReport.STAT_HOUR :
        rowTitle =  new String[]{"00:00 - 00:59", "01:00 - 01:59", "02:00 - 02:59", "03:00 - 03:59", "04:00 - 04:59", "05:00 - 05:59", "06:00 - 06:59", "07:00 - 07:59", "08:00 - 08:59", "09:00 - 09:59", "10:00 - 10:59",
                                  "11:00 - 11:59", "12:00 - 12:59", "13:00 - 13:59", "14:00 - 14:59", "15:00 - 15:59","16:00 - 16:59", "17:00 - 17:59", "18:00 - 18:59", "19:00 - 19:59", "20:00 - 20:59",
                                  "21:00 - 21:59", "22:00 - 22:59", "23:00 - 23:59"};                                                                    
        break;
      case UserStatReport.STAT_WEEKDAY :
        rowTitle = new String[]{"Mo", "Di", "Mi", "Do", "Fr", "Sa", "So"};
        break;
      case UserStatReport.STAT_DAY :         
        String[] days = new String[31];
        for (int i = 0; i < days.length; i++) {
          days[i] = String.valueOf(i+1);
        }
        rowTitle = days;
        break;
      case UserStatReport.STAT_MONTH : 
        String[] months = new String[12];
        for (int i = 0; i < months.length; i++) {
          months[i] = MONTHNAMES[i];
        }
        rowTitle = months;
        break;
      case UserStatReport.STAT_DURATION :         
        String[] minutes = new String[121];
        for (int i = 0; i < minutes.length; i++) {
          minutes[i] = String.valueOf(i);
        }
        minutes[120] = " >= 120 ";
        rowTitle = minutes;
        break;
    }
     

    String[] colTitle = null;
    switch (stat_typ) {
      case UserStatReport.STAT_HOUR : colTitle = new String[]{"Stunden", "Anzahl Logons", "Prozent"}; break;
      case UserStatReport.STAT_WEEKDAY : colTitle = new String[]{"Wochentage", "Anzahl Logons", "Prozent"}; break;
      case UserStatReport.STAT_DAY : colTitle = new String[]{"Tage", "Anzahl Logons", "Prozent"}; break;
      case UserStatReport.STAT_MONTH : colTitle = new String[]{"Monate", "Anzahl Logons", "Prozent"}; break;
      case UserStatReport.STAT_DURATION : colTitle = new String[]{"Minuten", "Anzahl Logons", "Prozent"}; break;
    }


    Integer[][] total = us.getData(stat_typ, typ);
    Object[][] tableTotal = new Object[total[0].length][2];
   
    
    int grandeTotal = 0;
    
    for (int i = 0; i < total[0].length; i++) {
      tableTotal[i][0] = total[0][i];
      grandeTotal += total[0][i].intValue();
    }

    for (int i = 0; i < total[0].length; i++) {
      int itemNum = ((Integer)tableTotal[i][0]).intValue();
      tableTotal[i][1] = percentFormat.format(100.0 / grandeTotal * itemNum);
    }

    ReportTableModel model = new ReportTableModel(rowTitle, colTitle, tableTotal);

    JTable totalTable = new JTable(model);

  	for (int i = 0; i < model.getColumnCount(); i++) {
  		TableColumn col = totalTable.getColumn(model.getColumnName(i));

  		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
  		dtcr.setHorizontalAlignment(model.getAlignment(i));
  		col.setCellRenderer(dtcr);
  		col.setPreferredWidth(model.getWidth(i));
  
  	}

  	JScrollPane scrollPane = new JScrollPane(totalTable);

    return scrollPane;  
  }





  public static void main(String[] args) {

		JFrame frame = new JFrame();

 

		frame.setTitle("PBroker Logon Statistik");
		frame.getContentPane().setLayout(new BorderLayout());


    UserStatFrame usf = new UserStatFrame();
    usf.inAnApplet = false;

    frame.getContentPane().add(usf, BorderLayout.CENTER);
    //frame.setSize(usf.getSize());
    frame.setDefaultCloseOperation(3);


		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			};
		});



  
    frame.setSize(650, 600);
		frame.setVisible(true);


  }

 

 
}