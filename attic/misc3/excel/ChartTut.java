import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.f1j.swing.JBook;
import com.f1j.ss.GRObject;
import com.f1j.ss.Sheet;
import com.f1j.ss.Constants;

public class ChartTut extends javax.swing.JFrame {
    boolean frameSizeAdjusted = false;
    javax.swing.JButton Chart  = new javax.swing.JButton();
    javax.swing.JButton Load   = new javax.swing.JButton();
    com.f1j.swing.JBook JBook1 = new com.f1j.swing.JBook();
    com.f1j.swing.JBook JBook2 = new com.f1j.swing.JBook();

    public ChartTut() {
        setTitle("Charting Tutorial");
        getContentPane().setLayout(null);
        setSize(1140, 716);
        setVisible(false);
        getContentPane().add(JBook1);
        JBook1.setBounds(5, 10, 360, 240);
        getContentPane().add(JBook2);
        JBook2.setBounds(5, 255, 1100, 400);
        
        Chart.setText("Create Chart");
        Chart.setActionCommand("Create Chart");
        Chart.setBounds(620, 40, 135, 27);
        getContentPane().add(Chart);

        Load.setText("Load Data");
        Load.setActionCommand("Load Data");
        Load.setBounds(620, 10, 134, 27);
        getContentPane().add(Load);
        
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent
                event) {
                Object object = event.getSource();
                if (object == this)  System.exit(0);
            }
        });
        F1Action lF1Action = new F1Action();
        Chart.addActionListener(lF1Action);
        Load.addActionListener(lF1Action);
    }

    static public void main(String args[]) {
        try {
            (new ChartTut()).setVisible(true);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    public void addNotify() {
        Dimension size = getSize();
        super.addNotify();
        if (frameSizeAdjusted) return;
        frameSizeAdjusted = true;
    }

    class F1Action implements java.awt.event.ActionListener {
       public void actionPerformed(java.awt.event.ActionEvent event) {
            Object object = event.getSource();
            try {
                if (object == Chart) {
                    JBook2.insertSheets(0, 1);
                    JBook2.getBook().getSheet(0).setSheetType
                        (Sheet.eSheetTypeChart);
                    JBook2.setSheet(0);
                    com.f1j.ss.GRChart grc = (com.f1j.ss.GRChart)
                        JBook2.addObject(GRObject.eChart, 0, 0, 1, 1);
                    createChart(grc);
                } else if (object == Load) {
                    JBook1.setSelection("A1");
                    JBook1.read("charttut.txt");
                    JBook2.attach(JBook1);
                    
                    // Set up Chart window
                    JBook2.setShowTabs(JBook2.eTabsOff);
                    JBook2.setShowHScrollBar(JBook2.eShowOff);
                    JBook2.setShowVScrollBar(JBook2.eShowOff);
                    JBook2.setShowEditBar(false);
                }
            } catch (Exception e) {
                System.out.println("Exception occurred processing button request: "+e.getMessage());
            }
        }
    }

void createChart(com.f1j.ss.GRChart c) throws com.f1j.util.F1Exception {

  // Link the chart to the spreadsheet range
  // All charting API which can be linked back to the spreadsheet are
  // set in the GRChart object, formatting and layout are set in the
  // ChartModel.

  c.setLinkRange("Sheet1!$A$1:$B$51", false); // false = series in
                                              //         columns
  com.f1j.chart.ChartModel cm = c.getChartModel();

  // Format the Chart background
  com.f1j.chart.Format cf=cm.getChartFormat();
  cf.setPatternFG(0xFFFFFF);  // start gradient color
  cf.setPatternBG(0xBBBBBB);  // end gradient color
  cf.setPattern((short)51);   // horizontal gradient
  cf.setPatternAuto(false);   // autoPattern off to enable background
  cm.setChartFormat(cf);
  
 
  // Format the Plot background
  com.f1j.chart.Format pf = cm.getPlotFormat();
  cf.setPatternFG(0xFFFFFF);  // start gradient color
  cf.setPatternBG(0xBBBBBB);  // end gradient color
  cf.setPattern((short)51);   // horizontal gradient
  cm.setPlotFormat(cf);

  // Set and format the Chart Title
  c.setTitle("Percentage of U.S. Households with a Computer");

  // These methods are documented in the com.f1j.util.Format class
  com.f1j.chart.Format ctf = cm.getChartTitleFormat();
  ctf.setFontBold(true);
  ctf.setFontName("Copperplate Gothic Light");
  ctf.setFontSizeInPoints(14);
  ctf.setHorizontalAlignment
      (com.f1j.util.Format.eHorizontalAlignmentCenter);
  ctf.setPatternFG(0xFFFFFF);  // start gradient color
  ctf.setPatternBG(0xBBBBBB);  // end gradient color
  ctf.setPattern((short)51);   // vertical gradient
  cm.setChartTitleFormat(ctf);

  // Format the Legend in the same manner
  com.f1j.chart.Format lf = cm.getLegendFormat();
  lf.setFontName("Tahoma");
  lf.setFontSizeInPoints(10);
  c.setSeriesName(0, "Homes with Computers"); // Series name also
                                              // appears in legend
  cm.setLegendVisible(true);
  cm.setLegendPlacement(cm.eLegendPlacementCorner); // place legend
  lf.setPatternFG(0xFFFFFF);  // start gradient color
  lf.setPatternBG(0xBBBBBB);  // end gradient color
  lf.setPattern((short)51);   // horizontal gradient
  lf.setPatternAuto(false);   // autoPattern off to enable background
  cm.setLegendFormat(lf);

  // Change series 0 to be a line chart.  Because the X axis is
  // categories, this requires a change to the chart type.
  cm.setChartType(cm.eCombination);
  cm.setSeriesType(0, cm.eLine);

  // Format the Y axis
  cm.setAxisScaleAutomatic(cm.eYAxis, 0, false);
  cm.setAxisScaleValueRange(cm.eYAxis, 0, 20, 60);
  cm.setAxisScaleMajorDivisions(cm.eYAxis, 0, 4);
  c.setAxisTitle(cm.eYAxis, 0, "Percent");

  // Set up a Format object named af
  com.f1j.chart.Format af = cm.getAxisTitleFormat(cm.eYAxis, 0);
  af.setFontName("Tahoma");
  af.setFontSizeInPoints(10);
  cm.setAxisTitleFormat(cm.eYAxis, 0, af);

  // Format the X axis - reuse the the Y axis format object
  c.setAxisTitle(cm.eXAxis, 0, "States");
  cm.setAxisTitleFormat(cm.eXAxis, 0, af);

  cm.setTickLabelPosition(cm.eXAxis, 0, cm.eTickLabelPositionNone);
  cm.setTickLabelPosition(cm.eYAxis, 0, cm.eTickCross);
  cm.setMajorTickStyle(cm.eXAxis, 0, cm.eTickNone); // Hide ticks

  // Add two series
  c.addSeries();
  c.addSeries();

  // Set series names
  c.setSeriesName(1,"Homes with Phones");
  c.setSeriesName(2,"Median Household Income");

  // Link to spreadsheet cells
  c.setSeriesYValueFormula(1,"Sheet1!$C$1:$C$51");
  c.setSeriesYValueFormula(2,"Sheet1!$D$1:$D$51");

  // Set series type
  cm.setSeriesType(1, cm.eLine);
  cm.setSeriesType(2, cm.eLine);

  // Place series 1 on the right Y axis. Y axis=1
  cm.setSeriesYAxisIndex(1, 1);
  cm.setAxisScaleAutomatic(cm.eYAxis, 1, false);
  cm.setAxisScaleValueRange(cm.eYAxis,1, 85, 100);
  c.setAxisTitle(cm.eYAxis, 1, "Percent");
  cm.setAxisScaleMajorDivisions(cm.eYAxis, 1, 2);


  // Change the markers, colors, etc. used to display the data point
  // on each series.

  // Series 0
  com.f1j.chart.Format sf = cm.getSeriesFormat(0);
  sf.setMarkerAuto(false);
  sf.setMarkerSize(100);
  sf.setMarkerStyle(sf.eMarkerDiamond);
  sf.setMarkerForeground(0x000000);
  sf.setMarkerBackground(0xCCCCCC);
  cm.setSeriesFormat(0, sf);

  // Series 1
  sf.setMarkerAuto(false);
  sf.setMarkerSize(75);
  sf.setMarkerStyle(sf.eMarkerSquare);
  sf.setMarkerForeground(0xDD0000);
  sf.setMarkerBackground(0xCCCCCC);
  cm.setSeriesFormat(1, sf);

  // Finally, Series 2
  sf.setMarkerAuto(false);
  sf.setMarkerSize(80);
  sf.setMarkerStyle(sf.eMarkerDownTriangle);
  sf.setMarkerForeground(0x669900);
  sf.setMarkerBackground(0xCCCCCC);
  cm.setSeriesFormat(2, sf);

  // Change the chart title. Reset title formatting
  c.setTitle("Relationship Between Income and Access to Digital Communication");

  // Set data point labels
  // sf.setDataLabelPosition(sf.eDataLabelPositionAbove);
  // sf.setDataLabelType(sf.eDataLabelValue);
  // cm.setSeriesFormat(2, sf);
  // com.f1j.chart.Format dlf=cm.getDataLabelFormat(2);
  // dlf.setFontSizeInPoints(6);
  // cm.setDataLabelFormat(2, dlf);

  // Move series 2 to a study axis
  cm.setSeriesYAxisIndex(2, 2); // put series 2 on Y axis 2 (study)

  // Format study axis
  cm.setAxisScaleAutomatic(cm.eYAxis, 2, false);
  cm.setAxisScaleValueRange(cm.eYAxis, 2, 20, 60);
  c.setAxisTitle(cm.eYAxis, 2, "$1,000's");
  cm.setAxisTitleFormat(cm.eYAxis, 2, af);
  cm.setAxisScaleMajorDivisions(cm.eYAxis, 2, 2);

  // Adjust the relative sizes of the main plot and the study.
  cm.setAxisLengthRatio(cm.eYAxis,0, 2); // makes main axis = 2x study
  cm.setAxisLengthRatio(cm.eYAxis, 2, 1);
  }

}
