
package com.example.gxttest.client.control;

import java.util.List;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.data.BaseTreeLoader;
import com.extjs.gxt.ui.client.data.BaseTreeModel;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.TreeLoader;
import com.extjs.gxt.ui.client.data.TreeModelReader;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.CenterLayout;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayoutData;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

public class AppView {

//  public static final String WEST_PANEL = "west";
//  public static final String VIEWPORT = "viewport";
//  public static final String CENTER_PANEL = "center";

  private Viewport viewport;
  private ContentPanel west;
  private ContentPanel center;
  AppControl appControl;
  private LabelField answerLabel;
  
  public AppView(AppControl appControl) {
    this.appControl = appControl;
    
    viewport = new Viewport();
    viewport.setLayout(new BorderLayout());

    createNorth();
    createWest();
    createCenter();

    // registry serves as a global context
//    Registry.register(VIEWPORT, viewport);
//    Registry.register(WEST_PANEL, west);
//    Registry.register(CENTER_PANEL, center);

    RootPanel.get().add(viewport);
  }

  private void createNorth() {
    LayoutContainer appHeading = new LayoutContainer();
    appHeading.addStyleName("appHeading");
    appHeading.setWidth("100%");

    HBoxLayout layout = new HBoxLayout();
    appHeading.setLayout(layout);
    
    HBoxLayoutData flex = new HBoxLayoutData(new Margins(0, 5, 0, 0));  
    flex.setFlex(1); 
    appHeading.add(new Text("gxttest"));
    appHeading.add(new Text(), flex);
    
    Button logoutButton = new Button("Logout");
    logoutButton.addSelectionListener(new SelectionListener<ButtonEvent>() {
      @Override
      public void componentSelected(ButtonEvent ce) {
        Window.open("j_spring_security_logout", "_self", "");
      }
    });
    appHeading.add(logoutButton);
    
    
    BorderLayoutData northData = new BorderLayoutData(LayoutRegion.NORTH, 33);    
    viewport.add(appHeading, northData);
  }

  private void createWest() {
    BorderLayoutData westData = new BorderLayoutData(LayoutRegion.WEST, 200, 150, 350);
    westData.setMargins(new Margins(5, 0, 5, 5));
    westData.setSplit(true);
    westData.setCollapsible(true);
    
    west = new ContentPanel();
    west.setBodyBorder(false);
    west.setLayout(new FitLayout());
    west.setLayoutOnChange(true);
    west.setHeading("Menu");

    createTreeContent(west);
    
    viewport.add(west, westData);
  }

  private void createCenter() {
    center = new ContentPanel();
    center.setHeading("Main");
    center.setLayout(new CenterLayout());

    BorderLayoutData centerData = new BorderLayoutData(LayoutRegion.CENTER);
    centerData.setMargins(new Margins(5, 5, 5, 5));

    viewport.add(center, centerData);    
    
    
    final FormPanel simple = new FormPanel();  
    simple.setHeading("Send Message to Server");  
    simple.setFrame(true);  
    simple.setWidth(350);  
       
    final TextField<String> messageTF = new TextField<String>();  
    messageTF.setFieldLabel("Message");  
    messageTF.setAllowBlank(false);  
    simple.add(messageTF);  
    
    answerLabel = new LabelField();
    answerLabel.setFieldLabel("Answer from Server: ");
    simple.add(answerLabel);
    
    Button b = new Button("Submit");  
    simple.addButton(b);  
    simple.setButtonAlign(HorizontalAlignment.CENTER);  
    
    b.addSelectionListener(new SelectionListener<ButtonEvent>() {
      
      @Override
      public void componentSelected(ButtonEvent ce) {
        if (simple.isValid()) {
          appControl.sendMessage(messageTF.getValue());
        }
      }
    });
    
    center.add(simple);
    
  }
  

  protected void updateGreeting(String result) {
    answerLabel.setValue(result);
  }
  
  
  private void createTreeContent(LayoutContainer container) {
    TreeLoader<ModelData> loader = new BaseTreeLoader<ModelData>(new TreeModelReader<List<ModelData>>()) {
//      @Override
//      public boolean hasChildren(ModelData parent) {
//        return parent instanceof Category;
//      }

    };
    
    BaseTreeModel treeModel = new BaseTreeModel();

    TreeStore<ModelData> treeStore = new TreeStore<ModelData>(loader);

    TreePanel<ModelData> tree = new TreePanel<ModelData>(treeStore);
    tree.getStyle().setLeafIcon(IconHelper.createStyle("icon-list"));
    tree.setAutoLoad(true);
    tree.setDisplayProperty("name");

//    SelectionService.get().addListener(new SourceSelectionChangedListener(tree.getSelectionModel()));
//    SelectionService.get().register(tree.getSelectionModel());

//    filter.bind(treeStore);
    loader.load(treeModel);

    container.add(tree);
  }


}
