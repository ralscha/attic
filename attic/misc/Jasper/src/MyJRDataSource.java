import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRField;


/**
 * @author sr
 */
public class MyJRDataSource implements JRDataSource {

  private int count = -1;
  private List data = new ArrayList();
  
  public MyJRDataSource() {
    data.add(new RowBean("col1", "col2_1"));
    data.add(new RowBean("col1", "col2_2"));
    data.add(new RowBean("col1", "col2_3"));
    data.add(new RowBean("col1", "col2_4"));
    data.add(new RowBean("col1", "col2_5"));
    data.add(new RowBean("col2", "col22_1"));
    data.add(new RowBean("col2", "col22_2"));
    
  }
  
  public boolean next() {
    count++;
    return (count < data.size());
  }

  public Object getFieldValue(JRField field) {
    RowBean r = (RowBean)data.get(count);
    if (field.getName().equals("col1")) {
      return r.getCol1();
    }
    else if (field.getName().equals("col2")) {
      return r.getCol2();
    }
    else if (field.getName().equals("value")) {
      return new BigDecimal(2);
    }    
    return null;
  }

}
