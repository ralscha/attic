import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.jfree.chart.labels.AbstractCategoryItemLabelGenerator;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.data.category.CategoryDataset;
import org.jfree.util.PublicCloneable;

public class PercentCategoryItemLabelGenerator extends AbstractCategoryItemLabelGenerator
    implements
      CategoryItemLabelGenerator,
      Cloneable,
      PublicCloneable,
      Serializable {

  /** The default format string. */
  public static final String DEFAULT_LABEL_FORMAT_STRING = "{2}";
  
  public static final DecimalFormat SIMPLE_PERCENT_FORMAT = new DecimalFormat("#,##0 '%'");
  
  /**
   * Creates a new generator with a default number formatter.
   */
  public PercentCategoryItemLabelGenerator() {
    
      super(DEFAULT_LABEL_FORMAT_STRING, SIMPLE_PERCENT_FORMAT);
  }


  public PercentCategoryItemLabelGenerator(String labelFormat, 
                                            NumberFormat formatter) {
      super(labelFormat, formatter);
  }
  

  public PercentCategoryItemLabelGenerator(String labelFormat, 
                                            DateFormat formatter) {
      super(labelFormat, formatter);
  }
  

  public String generateLabel(CategoryDataset dataset, int row, int column) {
      return generateLabelString(dataset, row, column);
  }

}
