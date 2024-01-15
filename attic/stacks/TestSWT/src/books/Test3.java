package books;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Test3 extends ApplicationWindow {

  public Test3() {
    super(null);
    createActions();
    addToolBar(SWT.NONE);
    addMenuBar();
  }
  protected Control createContents(Composite parent) {
    Composite composite = new Composite(parent, SWT.NONE);
    {
    	final Button button = new Button(composite, SWT.NONE);
    	button.setBounds(30, 30, 220, 20);
    	button.setText("button");
    }
    {
    	final Button button = new Button(composite, SWT.NONE);
    	button.setBounds(30, 60, 220, 110);
    	button.setText("button");
    }
    // DESIGNER: Add controls before this line.
    return composite;
  }
  private void createActions() {
    //no action
  }
  protected MenuManager createMenuManager() {
    MenuManager result = new MenuManager("menu");
    // DESIGNER: Add controls before this line.
    return result;
  }
  protected ToolBarManager createToolBarManager(int arg0) {
    ToolBarManager toolBarManager = new ToolBarManager(SWT.FLAT | SWT.WRAP);
    // DESIGNER: Add controls before this line.
    return toolBarManager;
  }
  public void run() {
    open();
    Display display = Display.getCurrent();
    Shell shell = getShell();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch())
        display.sleep();
    }
  }
  public static void main(String args[]) {
    //Display display = new Display();
    Test3 window = new Test3();
    window.run();
  }
  protected void configureShell(Shell newShell) {
    super.configureShell(newShell);
    newShell.setText("New Application");
  }
}
