import java.awt.BorderLayout;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.SimpleTimePeriod;
import org.jfree.data.time.TimePeriodValues;
import org.jfree.data.time.TimePeriodValuesCollection;

public class TestFrame extends JFrame {

  public TestFrame() {

    getContentPane().setLayout(new BorderLayout());

    TimePeriodValues values = new TimePeriodValues("test");
    int counter = 0;
    for (int i = 0; i < 10; i++) {
      counter += 10;
      Calendar cal1 = new GregorianCalendar(2006, Calendar.JANUARY, 1, i, 0, 0);
      Calendar cal2 = new GregorianCalendar(2006, Calendar.JANUARY, 1, i, counter, 0);

      SimpleTimePeriod periode = new SimpleTimePeriod(cal1.getTimeInMillis(), cal2.getTimeInMillis());

      values.add(periode, 100 + i * 10);
    }

    TimePeriodValuesCollection coll = new TimePeriodValuesCollection(values);

    JFreeChart chart = ChartFactory.createXYBarChart("Chart Titel", "xAxis", // domain axis label
        true, "yAxis", // range axis label
        coll, // data
        PlotOrientation.VERTICAL, // orientation
        false, // include legend
        false, // tooltips?
        false // URLs?
        );

    ChartPanel cp = new ChartPanel(chart);

    getContentPane().add(cp, BorderLayout.CENTER);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    setSize(200, 200);
    setVisible(true);
  }

  public static void main(String[] args) {
    new TestFrame();
  }
}
