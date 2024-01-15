
package @packageProject@.client.control;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Text;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.AccordionLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.CenterLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayoutData;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

public class AppView {

  public static final String WEST_PANEL = "west";
  public static final String VIEWPORT = "viewport";
  public static final String CENTER_PANEL = "center";

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
    Registry.register(VIEWPORT, viewport);
    Registry.register(WEST_PANEL, west);
    Registry.register(CENTER_PANEL, center);

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
    appHeading.add(new Text("@projectName@"));
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
    west.setLayout(new AccordionLayout());
    west.setLayoutOnChange(true);
    west.setHeading("Menu");

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
  


}
