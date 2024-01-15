package ch.ess.ex4u.client.gui;

import com.google.gwt.i18n.client.NumberFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class NumberListGridField extends ListGridField {

  public static final String DEFAULT_NUMBER_FORMAT = "#,##0.00#";
  public static final String SIMPLE_FLOAT_FORMAT = "#0.00#";
  public static final String SIMPLE_INT_FORMAT = "#0";
  public static final String SIMPLE_CURRENCY_FORMAT = "\u00A4 #,##0.00";

  protected String numberFormat = DEFAULT_NUMBER_FORMAT;

  public NumberListGridField(String name, String title, int width, String formatString) {
    super(name, title, width);
    numberFormat = formatString;
    setup(formatString);
  }

  public NumberListGridField(String name, String title, int width) {
    super(name, title, width);
    setup(DEFAULT_NUMBER_FORMAT);
  }

  public NumberListGridField(String name, String title, String formatString) {
    super(name, title);
    numberFormat = formatString;
    setup(formatString);
  }

  public NumberListGridField(String name, String title) {
    super(name, title);
    setup(DEFAULT_NUMBER_FORMAT);
  }

  private void setup(String formatString) {
    if (formatString.contains(".")) {
      setType(ListGridFieldType.FLOAT);
    } else {
      setType(ListGridFieldType.INTEGER);
    }

    setCellFormatter(new CellFormatter() {
      @SuppressWarnings("unused")
      @Override
      public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
        if (value != null) {
          NumberFormat nf = NumberFormat.getFormat(numberFormat);
          try {
            return nf.format(((Number)value).longValue());
          } catch (Exception e) {
            return value.toString();
          }
        }
        return null;
      }
    });
  }
}
