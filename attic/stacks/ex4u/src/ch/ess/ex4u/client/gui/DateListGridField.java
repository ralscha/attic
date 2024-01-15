package ch.ess.ex4u.client.gui;

import java.util.Date;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class DateListGridField extends ListGridField {

  public static final String DEFAULT_DATE_FORMAT = "dd.MM.yyyy";
  public static final String DEFAULT_LONG_DATE_FORMAT = "EEEE, dd. MMMM yyyy";
  public static final String DEFAULT_TIME_FORMAT = "HH:mm";

  protected String dateFormat = DEFAULT_DATE_FORMAT;

  public DateListGridField(String name, String title, int width, String formatString, boolean useDateItem) {
    super(name, title, width);
    dateFormat = formatString;
    setup(useDateItem);
  }

  public DateListGridField(String name, String title, int width, boolean useDateItem) {
    super(name, title, width);
    setup(useDateItem);
  }

  public DateListGridField(String name, String title, String formatString, boolean useDateItem) {
    super(name, title);
    dateFormat = formatString;
    setup(useDateItem);
  }

  public DateListGridField(String name, String title, String formatString) {
    super(name, title);
    dateFormat = formatString;
    setup(false);
  }

  public DateListGridField(String name, String title, boolean useDateItem) {
    super(name, title);
    setup(useDateItem);
  }

  public DateListGridField(String name, String title) {
    super(name, title);
    setup(false);
  }

  private void setup(boolean useDateItem) {
    setType(ListGridFieldType.DATE);

    if (useDateItem) {
      DateItem dateItem = new DateItem();
      dateItem.setUseTextField(false);
      setEditorType(dateItem);
    }

    setCellFormatter(new CellFormatter() {

      @SuppressWarnings("unused")
      @Override
      public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
        if (value != null) {
          DateTimeFormat fmt = DateTimeFormat.getFormat(dateFormat);
          try {
            return fmt.format((Date)value);
          } catch (Exception e) {
            return value.toString();
          }
        }
        return null;
      }
    });
  }
}
