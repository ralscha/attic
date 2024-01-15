package ch.ess.ex4u.client.gui.samples;

import ch.ess.ex4u.client.PanelFactory;
import ch.ess.ex4u.client.gui.BasePanel;
import ch.ess.ex4u.client.gui.MainLayout;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

public class DialogsSample extends BasePanel {

  public DialogsSample(MainLayout parent) {
    super(parent);
  }

  private static final String DESCRIPTION = "Click \"Confirm\" and \"Ask\" to show two of the pre-built, skinnable Smart GWT Dialogs for common interactions.";

  public static class Factory implements PanelFactory {

    private String id;

    public Canvas create(MainLayout main) {
      DialogsSample panel = new DialogsSample(main);
      id = panel.getID();
      return panel;
    }

    public String getID() {
      return id;
    }

    public String getDescription() {
      return DESCRIPTION;
    }
  }

  @Override
  public Canvas getViewPanel() {

    final Label labelAnswer = new Label("Your answer here...");
    labelAnswer.setTop(50);
    labelAnswer.setWidth(300);

    IButton buttonConfirm = new IButton("Confirm");
    buttonConfirm.addClickHandler(new ClickHandler() {

      public void onClick(@SuppressWarnings("unused") ClickEvent event) {
        SC.confirm("Proceed with Operation get AJAX?", new BooleanCallback() {

          public void execute(Boolean value) {
            if (value != null && value) {
              labelAnswer.setContents("OK");
            } else {
              labelAnswer.setContents("Cancel");
            }
          }
        });
      }
    });

    IButton buttonAsk = new IButton("Ask");
    buttonAsk.setLeft(150);
    buttonAsk.addClickHandler(new ClickHandler() {

      public void onClick(@SuppressWarnings("unused") ClickEvent event) {
        SC.ask("Are you going to stop writing great code?", new BooleanCallback() {

          public void execute(Boolean value) {
            if (value != null && value) {
              labelAnswer.setContents("Yes");
            } else {
              labelAnswer.setContents("No");
            }
          }
        });
      }
    });

    Canvas main = new Canvas();
    main.addChild(labelAnswer);
    main.addChild(buttonConfirm);
    main.addChild(buttonAsk);

    return main;
  }

  @Override
  public String getIntro() {
    return DESCRIPTION;
  }
}