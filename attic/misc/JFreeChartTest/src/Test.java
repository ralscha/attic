import java.awt.Color;
import java.awt.Paint;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

import Acme.JPM.Encoders.GifEncoder;

public class Test {

  public final static Color GREEN = new Color(153, 204, 0);
  public final static Color YELLOW = new Color(255, 255, 0);
  public final static Color RED = new Color(255, 0, 0);
  
  public static void main(String[] args) {

    // row keys...
    String series1 = "First";

    // column keys...
    String category1 = "4. Q. 2004";
    String category2 = "1. Q. 2005";
    String category3 = "2. Q. 2005";
    String category4 = "3. Q. 2005";
    String category5 = "4. Q. 2005";

    // create the dataset...
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    dataset.addValue(78, series1, category1);
    dataset.addValue(92, series1, category2);
    //dataset.addValue(85, series1, category3);
    //dataset.addValue(50, series1, category4);
    //dataset.addValue(50, series1, category5);

    JFreeChart chart = ChartFactory.createBarChart(" ", // chart title
        "Quartale", // domain axis label
        "Note", // range axis label
        dataset, // data
        PlotOrientation.VERTICAL, // orientation
        false, // include legend
        false, // tooltips?
        false // URLs?
        );

    
    
    CustomRenderer customrenderer = new CustomRenderer(new Paint[]{YELLOW, GREEN, YELLOW, RED});
    customrenderer.setItemLabelsVisible(true);
    customrenderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
    chart.setBackgroundPaint(Color.white);
    customrenderer.setMaximumBarWidth(0.15D);
    
    CategoryPlot plot = chart.getCategoryPlot();
    plot.setRenderer(customrenderer);
    
    
    NumberAxis numberaxis = (NumberAxis)plot.getRangeAxis();
    numberaxis.setRange(0.0D, 100D);
   
    numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    numberaxis.setTickUnit(new NumberTickUnit(10));
    BufferedImage bi = chart.createBufferedImage(800, 400, BufferedImage.TYPE_BYTE_INDEXED, null);

    try {

      OutputStream output = new BufferedOutputStream(new FileOutputStream("c:/chart.gif"));
      GifEncoder encode = new GifEncoder(bi, output, true);
      encode.encode();
      output.close();
    } catch (IOException ioe) {
      System.out.println(ioe);
    }
    
    
    //////////////////////////////////////////////
    
    

    // column keys...
    category1 = "10";
    category2 = "20";
    category3 = "75";
    category4 = "100";

    // create the dataset...
    dataset = new DefaultCategoryDataset();

    dataset.addValue(1, series1, category1);
    dataset.addValue(2, series1, category2);
    dataset.addValue(1, series1, category3);
    dataset.addValue(1, series1, category4);

    chart = ChartFactory.createBarChart(" ", // chart title
        "Note", // domain axis label
        "Anzahl Bewertungen", // range axis label
        dataset, // data
        PlotOrientation.VERTICAL, // orientation
        false, // include legend
        false, // tooltips?
        false // URLs?
        );

    
    
    customrenderer = new CustomRenderer(new Paint[]{RED, RED, YELLOW, GREEN});
    customrenderer.setItemLabelsVisible(true);
    customrenderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
    chart.setBackgroundPaint(Color.white);

    plot = chart.getCategoryPlot();
    plot.setRenderer(customrenderer);
    
    plot.setRangeGridlinesVisible(false);
    
    BarRenderer barrenderer = (BarRenderer)plot.getRenderer();
    barrenderer.setMaximumBarWidth(0.05D);
    
    
    numberaxis = (NumberAxis)plot.getRangeAxis();   
    numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
    
    
    bi = chart.createBufferedImage(800, 400, BufferedImage.TYPE_BYTE_INDEXED, null);

    try {

      OutputStream output = new BufferedOutputStream(new FileOutputStream("c:/chart2.gif"));
      GifEncoder encode = new GifEncoder(bi, output, true);
      encode.encode();
      output.close();
    } catch (IOException ioe) {
      System.out.println(ioe);
    }

    //////////////////////////////////////////////
    
    series1 = "Red";    
    String series2 = "Yellow";
    String series3 = "Green";
    
    // column keys...
    category1 = "1. Q. 2004";
    category2 = "2. Q. 2004";
    category3 = "3. Q. 2004";
    category4 = "4. Q. 2004";
    category5 = "1. Q. 2005";

    // create the dataset...
    dataset = new DefaultCategoryDataset();

    dataset.addValue(50, series1, category1);
    dataset.addValue(0, series1, category2);
    dataset.addValue(33.3333, series1, category3);
    dataset.addValue(50, series1, category4);
    dataset.addValue(0, series1, category5);
    
    dataset.addValue(50, series2, category1);
    dataset.addValue(0, series2, category2);
    dataset.addValue(33.3333, series2, category3);
    dataset.addValue(20, series2, category4);
    dataset.addValue(0, series2, category5);

    dataset.addValue(0, series3, category1);
    dataset.addValue(0, series3, category2);
    dataset.addValue(33.3333, series3, category3);
    dataset.addValue(30, series3, category4);
    dataset.addValue(0, series3, category5);
    

    chart = ChartFactory.createStackedBarChart(" ", // chart title
        "Quartale", // domain axis label
        "", // range axis label
        dataset, // data
        PlotOrientation.VERTICAL, // orientation
        false, // include legend
        false, // tooltips?
        false // URLs?
        );

    plot = chart.getCategoryPlot();
    plot.setRangeGridlinesVisible(false);

    
    StackedBarRenderer renderer = (StackedBarRenderer)plot.getRenderer();
    renderer.setSeriesPaint(0, Color.GREEN);
    renderer.setSeriesPaint(1, Color.YELLOW);
    renderer.setSeriesPaint(2, Color.RED);
    
    renderer.setItemLabelsVisible(true);
    renderer.setItemLabelGenerator(new PercentCategoryItemLabelGenerator());
    
    ItemLabelPosition itemlabelposition = new ItemLabelPosition(ItemLabelAnchor.INSIDE12, TextAnchor.TOP_CENTER);
    renderer.setPositiveItemLabelPosition(itemlabelposition);

    
    numberaxis = (NumberAxis)plot.getRangeAxis();
    numberaxis.setRange(0.0D, 100D);
    numberaxis.setVisible(false);

    
    
    bi = chart.createBufferedImage(800, 400, BufferedImage.TYPE_BYTE_INDEXED, null);

    try {

      OutputStream output = new BufferedOutputStream(new FileOutputStream("c:/chart3.gif"));
      GifEncoder encode = new GifEncoder(bi, output, true);
      encode.encode();
      output.close();
    } catch (IOException ioe) {
      System.out.println(ioe);
    }


  }

  
  static class CustomRenderer extends BarRenderer {

    public Paint getItemPaint(int i, int j) {
      return colors[j % colors.length];
    }

    private Paint colors[];

    public CustomRenderer(Paint apaint[]) {
      colors = apaint;
    }
  }

}
