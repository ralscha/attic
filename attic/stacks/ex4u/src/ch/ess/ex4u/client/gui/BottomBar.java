package ch.ess.ex4u.client.gui;

import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

public class BottomBar extends ToolStrip {

  public BottomBar() {
    super();
    
    setHeight(22);
    setWidth100();

    addSpacer(6);
    addSeparator();
    addSpacer(6);

    Label title = new Label("Status Bar");
    addMember(title);

    addSpacer(6);
    addSeparator();

    // Adds a LayoutSpacer to the ToolStrip to take up space such like a normal member, without actually drawing anything. This causes the "next" member added to the toolstip to be right / bottom justified depending on the alignment of the ToolStrip.
    addFill();
  }
}
