package ch.ess.jsf;

import java.util.List;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

public class HelloBean {
  private int numControls;
  private HtmlPanelGrid controlPanel;

  public HtmlPanelGrid getControlPanel() {
    return controlPanel;
  }

  public void setControlPanel(HtmlPanelGrid controlPanel) {
    this.controlPanel = controlPanel;
  }

  public int getNumControls() {
    return numControls;
  }

  public void setNumControls(int numControls) {
    this.numControls = numControls;
  }
  
  public void addControls(@SuppressWarnings("unused")  ActionEvent actionEvent) {
    Application application = FacesContext.getCurrentInstance().getApplication();
    
    List<UIComponent> children = controlPanel.getChildren();
    children.clear();
    
    for (int count = 0; count < numControls; count++) {
      HtmlOutputText output = (HtmlOutputText)application.createComponent(HtmlOutputText.COMPONENT_TYPE);
      output.setValue(" " + count + " ");
      output.setStyle("color: blue");
      children.add(output);
    }
  }
  
  public String goodbye() {
    return "success";
  }

}
