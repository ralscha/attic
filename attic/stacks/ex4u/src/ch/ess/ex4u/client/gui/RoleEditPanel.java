package ch.ess.ex4u.client.gui;

import ch.ess.ex4u.client.PanelFactory;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.VLayout;

public class RoleEditPanel extends BasePanel {
  
  public RoleEditPanel(MainLayout main) {
    super(main);
  }

  public static class Factory implements PanelFactory {

    private String id;

    public Canvas create(MainLayout main) {
      RoleEditPanel panel = new RoleEditPanel(main);
      id = panel.getID();
      return panel;
    }

    public String getID() {
      return id;
    }

    public String getDescription() {
      return null;
    }
  }

  @Override
  public Canvas getViewPanel() {

    DataSource roleDS = DataSource.get("roleDMI");

    VLayout canvas = new VLayout();
    canvas.setHeight100();
    canvas.setMargin(10);
    canvas.setMembersMargin(10);

    SimpleEditPanel roleEditor = new SimpleEditPanel("Rollen", roleDS, "100%", null);
    canvas.addMember(roleEditor);

    return canvas;
  }

  @Override
  public String getIntro() {
    return null;
  }
}