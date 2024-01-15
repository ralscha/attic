package ch.ess.sandbox.facelets;

// JSE imports
import java.util.ArrayList;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UIPanel;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;


/**
 * JSF backing bean for the rightSidebar-component.xthml view.
 * Used in examples 5-7, 5-8 and 5-9
 * @author Robert Swarr
 */
@Name("rightSidebarBean")
public class RightSidebarBean {

  private UIComponent rightSidebar;
  private UIComponent siteServices;
  private List<ListItem> itemList;

  @In 
  private FacesContext facesContext;
  
  /**
   * Default constructor
   */
  public RightSidebarBean() {
    super();
  }

  /**
   * Accessor method for the rightSidebar property. 
   * It is used as the target of an EL expression
   * for the binding attribute of a Facelets ui:component tag.
   * @return UIComponent
   */
  public UIComponent getRightSidebar() {
    return rightSidebar;
  }

  /**
   * Mutator method for the rightSidebar property. 
   * It is used as the target of an EL expression
   * for the binding attribute of a Facelets ui:component tag.
   */
  public void setRightSidebar(UIComponent rightSidebar) {
    this.rightSidebar = rightSidebar;
  }

  /**
   * JSF action method that is the target of an action attribute.
   * @return String
   */
  public String commandLinkAction() {
    return "";
  }

  /**
   * Accessor method for the itemList property.
   * Target of a value binding in a Facelets ui:repeat tag.\
   * @return List
   */
  public List<ListItem> getItemList() {
    if (itemList == null || itemList.isEmpty()) {
      List<ListItem> items = new ArrayList<ListItem>();
      ListItem listItem = new ListItem();
      listItem.setType("outputText");
      listItem.setLabel("News & Features");
      items.add(listItem);
      listItem = new ListItem();
      listItem.setType("commandLink");
      listItem.setLabel("Food News");
      listItem.setAction("commandLinkAction");
      items.add(listItem);
      listItem = new ListItem();
      listItem.setType("commandLink");
      listItem.setLabel("Food Features");
      listItem.setAction("commandLinkAction");
      items.add(listItem);
      listItem = new ListItem();
      listItem.setType("outputText");
      listItem.setLabel("RSS Feeds");
      items.add(listItem);
      listItem = new ListItem();
      listItem = new ListItem();
      listItem.setType("commandLink");
      listItem.setLabel("Food News");
      listItem.setAction("commandLinkAction");
      items.add(listItem);
      listItem = new ListItem();
      listItem.setType("commandLink");
      listItem.setLabel("Food Columns");
      listItem.setAction("commandLinkAction");
      items.add(listItem);
      itemList = items;
    }
    return itemList;
  }

  /**
   * Mutator method for the itemList property.
   * Target of a value binding in a Facelets ui:repeat tag.
   * @param anItemList
   */
  public void setItemList(List<ListItem> anItemList) {
    itemList = anItemList;
  }

  /**
   * Accessor method for the siteServices property.
   * Target of a binding attribute in a Facelets ui:fragment tag.
   * @return UIComponent
   */
  public UIComponent getSiteServices() {
    if (siteServices == null) {
      siteServices = facesContext.getApplication().createComponent(UIPanel.COMPONENT_TYPE);
      HtmlPanelGroup panelGroup = (HtmlPanelGroup)facesContext.getApplication().createComponent(HtmlPanelGroup.COMPONENT_TYPE);
      HtmlOutputText outputText = (HtmlOutputText)facesContext.getApplication().createComponent(HtmlOutputText.COMPONENT_TYPE);
      outputText.setValue("- reqister <br />");
      outputText.setEscape(false);
      panelGroup.getChildren().add(outputText);
      outputText = (HtmlOutputText)facesContext.getApplication().createComponent(HtmlOutputText.COMPONENT_TYPE);
      outputText.setValue("- login <br />");
      outputText.setEscape(false);
      panelGroup.getChildren().add(outputText);
      outputText = (HtmlOutputText)facesContext.getApplication().createComponent(HtmlOutputText.COMPONENT_TYPE);
      outputText.setValue("- forum <br />");
      outputText.setEscape(false);
      panelGroup.getChildren().add(outputText);
      siteServices.getChildren().add(panelGroup);
    }
    return siteServices;
  }

  /**
   * Mutator method for the siteServices property.
   * @param siteServices UIComponent
   */
  public void setSiteServices(UIComponent siteServices) {
    this.siteServices = siteServices;
  }
}
