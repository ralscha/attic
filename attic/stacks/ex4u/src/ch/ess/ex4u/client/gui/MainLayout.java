package ch.ess.ex4u.client.gui;

import java.util.LinkedHashMap;
import ch.ess.ex4u.client.PanelFactory;
import ch.ess.ex4u.client.data.CommandTreeNode;
import ch.ess.ex4u.client.data.NavigationTreeNode;
import ch.ess.ex4u.shared.PrincipalDetail;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.smartgwt.client.core.KeyIdentifier;
import com.smartgwt.client.types.TabBarControls;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ShowContextMenuEvent;
import com.smartgwt.client.widgets.events.ShowContextMenuHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemIfFunction;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.client.widgets.tree.events.LeafClickEvent;
import com.smartgwt.client.widgets.tree.events.LeafClickHandler;

// Deprecated. use History.addValueChangeHandler(com.google.gwt.event.logical.shared.ValueChangeHandler)  instead!

public class MainLayout implements ValueChangeHandler<String> {
  
  protected TabSet mainTabSet;
  private SideNavTree sideNav;
  private PrincipalDetail principalDetail;

  public MainLayout(PrincipalDetail principal) {
    principalDetail = principal;
    
    final String initToken = History.getToken();

    // Add history listener
    History.addValueChangeHandler(this);

    // Setup overall layout / viewport
    final VLayout main = new VLayout() {

      @Override
      protected void onInit() {
        super.onInit();
        if (initToken.length() != 0) {
          // Now that we've setup our listener, fire the initial history state.
          History.fireCurrentHistoryState();
        }
      }
    };

    main.setWidth100();
    main.setHeight100();
    main.setStyleName("tabSetContainer");

    ToolStrip topBar = new TopBar(this);
    main.addMember(topBar);

    HLayout hLayout = makeAppLayout();
    main.addMember(hLayout);

    main.draw();
  }


  private HLayout makeAppLayout() {
    final HLayout hLayout = new HLayout();

    hLayout.setLayoutMargin(5);
    hLayout.setWidth100();
    hLayout.setHeight100();

    VLayout sideNavLayout = makeNaviLayout();
    hLayout.addMember(sideNavLayout);

    mainTabSet = makeTabSet();

    Canvas canvas = new Canvas();
    canvas.setBackgroundImage("[SKIN]/shared/background.gif");
    canvas.setWidth100();
    canvas.setHeight100();
    canvas.addChild(mainTabSet);

    hLayout.addMember(canvas);

    return hLayout;
  }

  private TabSet makeTabSet() {
    final TabSet tabSet = new TabSet();

    Layout paneContainerProperties = new Layout();
    paneContainerProperties.setLayoutMargin(0);
    paneContainerProperties.setLayoutTopMargin(1);
    tabSet.setPaneContainerProperties(paneContainerProperties);

    tabSet.setWidth100();
    tabSet.setHeight100();
    tabSet.addTabSelectedHandler(new TabSelectedHandler() {

      public void onTabSelected(TabSelectedEvent event) {
        Tab selectedTab = event.getTab();
        String historyToken = selectedTab.getAttribute("historyToken");
        if (historyToken != null) {
          History.newItem(historyToken, false);
        } else {
          History.newItem("main", false);
        }
      }
    });

    LayoutSpacer layoutSpacer = new LayoutSpacer();
    layoutSpacer.setWidth(5);

    SelectItem selectItem = new SelectItem();
    selectItem.setHeight(21);
    selectItem.setWidth(130);
    LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
    valueMap.put("EnterpriseBlue", "Enterprise Blue");
    valueMap.put("Enterprise", "Enterprise Gray");
    valueMap.put("Graphite", "Graphite");

    selectItem.setValueMap(valueMap);

    String currentSkin = Cookies.getCookie("skin_name");
    if (currentSkin == null) {
      currentSkin = "EnterpriseBlue";
    }
    selectItem.setDefaultValue(currentSkin);
    selectItem.setShowTitle(false);
    selectItem.addChangeHandler(new ChangeHandler() {

      public void onChange(ChangeEvent event) {
        Cookies.setCookie("skin_name", (String)event.getValue());
        com.google.gwt.user.client.Window.Location.reload();
      }
    });

    DynamicForm form = new DynamicForm();
    form.setPadding(0);
    form.setMargin(0);
    form.setCellPadding(1);
    form.setNumCols(1);
    form.setFields(selectItem);

    tabSet.setTabBarControls(TabBarControls.TAB_SCROLLER, TabBarControls.TAB_PICKER, layoutSpacer, form);

    final Menu contextMenu = createContextMenu();
    tabSet.addShowContextMenuHandler(new ShowContextMenuHandler() {

      public void onShowContextMenu(ShowContextMenuEvent event) {
        int selectedTab = tabSet.getSelectedTabNumber();
        if (selectedTab != 0) {
          contextMenu.showContextMenu();
        }
        event.cancel();
      }
    });

    Tab tab = new Tab();
    tab.setTitle("Home&nbsp;&nbsp;");
    tab.setIcon("pieces/16/cube_green.png");
    tab.setWidth(80);

    HLayout mainPanel = new HLayout();
    mainPanel.setHeight100();
    mainPanel.setWidth100();

    if (SC.hasFirebug()) {
      Label label = new Label();
      label
          .setContents("<p>Firebug can make the Showcase run slow.</p><p>For the best performance, we suggest disabling Firebug for this site.</p>");
      label.setWidth100();
      label.setHeight("auto");
      label.setMargin(20);
      Window fbWindow = new Window();
      fbWindow.setShowHeader(false);
      fbWindow.addItem(label);
      fbWindow.setWidth(220);
      fbWindow.setHeight(130);

      LayoutSpacer spacer = new LayoutSpacer();
      spacer.setWidth100();
      mainPanel.addMember(spacer);
      mainPanel.addMember(fbWindow);
    }

    tab.setPane(mainPanel);

    tabSet.addTab(tab);
    return tabSet;
  }

  private VLayout makeNaviLayout() {
    final VLayout sideNavLayout = new VLayout();

    sideNavLayout.setHeight100();
    sideNavLayout.setWidth(185);
    sideNavLayout.setShowResizeBar(true);

    sideNav = new SideNavTree();
    sideNav.addLeafClickHandler(new LeafClickHandler() {

      public void onLeafClick(LeafClickEvent event) {
        TreeNode node = event.getLeaf();
        showSample(node);
      }
    });

    sideNavLayout.addMember(sideNav);
    return sideNavLayout;
  }


  private Menu createContextMenu() {
    Menu menu = new Menu();
    menu.setWidth(140);

    MenuItemIfFunction enableCondition = new MenuItemIfFunction() {

      @SuppressWarnings({"hiding", "unused"})
      public boolean execute(Canvas target,  Menu menu, MenuItem item) {
        int selectedTab = mainTabSet.getSelectedTabNumber();
        return selectedTab != 0;
      }
    };

    MenuItem closeItem = new MenuItem("<u>C</u>lose");
    closeItem.setEnableIfCondition(enableCondition);
    closeItem.setKeyTitle("Alt+C");
    KeyIdentifier closeKey = new KeyIdentifier();
    closeKey.setAltKey(true);
    closeKey.setKeyName("C");
    closeItem.setKeys(closeKey);
    closeItem.addClickHandler(new ClickHandler() {

      public void onClick(@SuppressWarnings("unused") MenuItemClickEvent event) {
        int selectedTab = mainTabSet.getSelectedTabNumber();
        mainTabSet.removeTab(selectedTab);
        mainTabSet.selectTab(selectedTab - 1);
      }
    });

    MenuItem closeAllButCurrent = new MenuItem("Close All But Current");
    closeAllButCurrent.setEnableIfCondition(enableCondition);
    closeAllButCurrent.addClickHandler(new ClickHandler() {

      public void onClick(@SuppressWarnings("unused") MenuItemClickEvent event) {
        int selected = mainTabSet.getSelectedTabNumber();
        Tab[] tabs = mainTabSet.getTabs();
        int[] tabsToRemove = new int[tabs.length - 2];
        int cnt = 0;
        for (int i = 1; i < tabs.length; i++) {
          if (i != selected) {
            tabsToRemove[cnt] = i;
            cnt++;
          }
        }
        mainTabSet.removeTabs(tabsToRemove);
      }
    });

    MenuItem closeAll = new MenuItem("Close All");
    closeAll.setEnableIfCondition(enableCondition);
    closeAll.addClickHandler(new ClickHandler() {

      public void onClick(@SuppressWarnings("unused") MenuItemClickEvent event) {
        Tab[] tabs = mainTabSet.getTabs();
        int[] tabsToRemove = new int[tabs.length - 1];

        for (int i = 1; i < tabs.length; i++) {
          tabsToRemove[i - 1] = i;
        }
        mainTabSet.removeTabs(tabsToRemove);
        mainTabSet.selectTab(0);
      }
    });

    menu.setItems(closeItem, closeAllButCurrent, closeAll);
    return menu;
  }

  void showSample(TreeNode node) {
    boolean isExplorerTreeNode = node instanceof NavigationTreeNode;
    if (node instanceof CommandTreeNode) {
      CommandTreeNode commandTreeNode = (CommandTreeNode)node;
      commandTreeNode.getCommand().execute();
    } else if (isExplorerTreeNode) {
      NavigationTreeNode explorerTreeNode = (NavigationTreeNode)node;
      PanelFactory factory = explorerTreeNode.getFactory();
      if (factory != null) {
        String panelID = factory.getID();
        Tab tab = null;
        if (panelID != null) {
          String tabID = panelID + "_tab";
          tab = mainTabSet.getTab(tabID);
        }
        if (tab == null) {
          Canvas panel = factory.create(this);
          tab = new Tab();
          tab.setID(factory.getID() + "_tab");
          //store history token on tab so that when an already open is selected, one can retrieve the
          //history token and update the URL
          tab.setAttribute("historyToken", explorerTreeNode.getNodeID());

          String sampleName = explorerTreeNode.getName();

          String icon = explorerTreeNode.getIcon();
          if (icon == null) {
            icon = "silk/plugin.png";
          }
          String imgHTML = Canvas.imgHTML(icon, 16, 16);
          tab.setTitle("<span>" + imgHTML + "&nbsp;" + sampleName + "</span>");
          tab.setPane(panel);
          tab.setCanClose(true);
          mainTabSet.addTab(tab);
          mainTabSet.selectTab(tab);
        } else {
          mainTabSet.selectTab(tab);
        }
        History.newItem(explorerTreeNode.getNodeID(), false);
      }
    }
  }

  @Override
  public void onValueChange(ValueChangeEvent<String> event) {
    String historyToken = event.getValue();
    historyChangedAction(historyToken);
  }

  public void historyChangedAction(String historyToken) {
    if (historyToken == null || historyToken.equals("")) {
      mainTabSet.selectTab(0);
    } else {
      NavigationTreeNode[] showcaseData = sideNav.getNavigationData();
      for (NavigationTreeNode explorerTreeNode : showcaseData) {
        if (explorerTreeNode.getNodeID().equals(historyToken)) {
          showSample(explorerTreeNode);
          sideNav.selectRecord(explorerTreeNode);
          Tree tree = sideNav.getData();
          TreeNode categoryNode = tree.getParent(explorerTreeNode);
          while (categoryNode != null && !"/".equals(tree.getName(categoryNode))) {
            tree.openFolder(categoryNode);
            categoryNode = tree.getParent(categoryNode);
          }
        }
      }
    }
  }
  
  public Canvas getCurrentViewPanel() {
    return mainTabSet.getSelectedTab().getPane();
  }

  
  public PrincipalDetail getPrincipal() {
    return principalDetail;
  }
}
