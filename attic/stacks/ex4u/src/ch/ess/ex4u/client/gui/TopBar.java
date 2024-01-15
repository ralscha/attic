package ch.ess.ex4u.client.gui;

import com.google.gwt.user.client.Window;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class TopBar extends ToolStrip {
  
  protected MainLayout mainPanel;

  public TopBar(MainLayout parent) {
    super();
    this.mainPanel = parent;
    
    setHeight(33);
    setWidth100();

    addSpacer(6);
    ImgButton homeButton = new ImgButton();
    homeButton.setSrc("ess/ess_essonly_logo_small_2.png");
    homeButton.setWidth(61);
    homeButton.setHeight(18);
    homeButton.setPrompt("ESS Home Page");
    homeButton.setHoverStyle("interactImageHover");
    homeButton.setShowRollOver(false);
    homeButton.setShowDownIcon(false);
    homeButton.setShowDown(false);

    homeButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

      public void onClick(@SuppressWarnings("unused") ClickEvent event) {
        com.google.gwt.user.client.Window.open("http://www.ess.ch", "ESS", null);
      }
    });
    addMember(homeButton);

    addSpacer(12);
    Label title = new Label("TIME4U");
    title.setStyleName("sgwtTitle");
    title.setAutoWidth();
    addMember(title);

    addSpacer(6);
    Label version = new Label("Version 0.1.0");
    version.setAutoWidth();
    version.setWrap(false);
    addMember(version);

    // Adds a LayoutSpacer to the ToolStrip to take up space such like a normal member, without actually drawing anything. This causes the "next" member added to the toolstip to be right / bottom justified depending on the alignment of the ToolStrip.
    addFill();
    
    // Drucken
    ToolStripButton printButton = new ToolStripButton();
    printButton.setTitle("Print");
    printButton.setIcon("silk/printer.png");
    printButton.addClickHandler(new ClickHandler() {

      public void onClick(@SuppressWarnings("unused") ClickEvent event) {
        Canvas.showPrintPreview(mainPanel.getCurrentViewPanel());
      }
    });
    addMember(printButton);

    // Sprachauswahl
    addSeparator();
    addSpacer(3);
    ImgButton lang1Button = new ImgButton();
    lang1Button.setWidth(18);
    lang1Button.setHeight(18);
    lang1Button.setSrc("flags/24/GM.png");
    lang1Button.setShowRollOver(false);
    lang1Button.setPrompt("Deutsch");
    lang1Button.setHoverWidth(110);
    lang1Button.setHoverStyle("interactImageHover");
    addMember(lang1Button);
    addSpacer(6);
    ImgButton lang2Button = new ImgButton();
    lang2Button.setWidth(18);
    lang2Button.setHeight(18);
    lang2Button.setSrc("flags/24/FR.png");
    lang2Button.setShowRollOver(false);
    lang2Button.setPrompt("Französisch");
    lang2Button.setHoverWidth(110);
    lang2Button.setHoverStyle("interactImageHover");
    addMember(lang2Button);
    addSpacer(6);
    ImgButton lang3Button = new ImgButton();
    lang3Button.setWidth(18);
    lang3Button.setHeight(18);
    lang3Button.setSrc("flags/24/IT.png");
    lang3Button.setShowRollOver(false);
    lang3Button.setPrompt("Italienisch");
    lang3Button.setHoverWidth(110);
    lang3Button.setHoverStyle("interactImageHover");
    addMember(lang3Button);
    addSpacer(6);
    ImgButton lang4Button = new ImgButton();
    lang4Button.setWidth(18);
    lang4Button.setHeight(18);
    lang4Button.setSrc("flags/24/UK.png");
    lang4Button.setShowRollOver(false);
    lang4Button.setPrompt("Englisch");
    lang4Button.setHoverWidth(110);
    lang4Button.setHoverStyle("interactImageHover");
    addMember(lang4Button);
    addSpacer(3);
    addSeparator();

    ToolStripButton devConsoleButton = new ToolStripButton();
    devConsoleButton.setTitle("Developer Console");
    devConsoleButton.setIcon("silk/bug.png");

    devConsoleButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

      public void onClick(@SuppressWarnings("unused") ClickEvent event) {
        SC.showConsole();
      }
    });
    addButton(devConsoleButton);

    addSeparator();

    addSpacer(3);
    Label username = new Label(parent.getPrincipal() != null ? parent.getPrincipal().getName(): "NULL NULL");
    username.setAutoWidth();
    username.setTooltip(parent.getPrincipal().getFullName());
    addMember(username);

    addSpacer(3);
    ImgButton imgButton = new ImgButton();
    imgButton.setWidth(18);
    imgButton.setHeight(18);
    imgButton.setSrc("ess/48/logout.png");
    imgButton.setShowFocused(false);
    imgButton.setShowFocusedIcon(false);
    imgButton.setShowRollOver(false);
    imgButton.setPrompt("Logout");
    imgButton.setHoverWidth(110);
    imgButton.setHoverStyle("interactImageHover");

    imgButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

      public void onClick(@SuppressWarnings("unused") ClickEvent event) {
        Window.Location.assign("/logout");
      }
    });
    
    addMember(imgButton);

    addSpacer(6);
  }
}
