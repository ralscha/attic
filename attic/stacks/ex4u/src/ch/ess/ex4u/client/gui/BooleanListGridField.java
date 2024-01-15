package ch.ess.ex4u.client.gui;

import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.ListGridField;

public class BooleanListGridField extends ListGridField {

  public BooleanListGridField(String name, String title, int width) {
    super(name, title, width);
    setup();
  }

  public BooleanListGridField(String name, String title) {
    super(name, title);
    setup();
  }

  private void setup() {
    setType(ListGridFieldType.BOOLEAN);
  }
}
