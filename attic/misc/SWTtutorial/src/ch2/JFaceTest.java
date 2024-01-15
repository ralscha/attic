package ch2;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

/**
 * @author sr
 */
public class JFaceTest extends ApplicationWindow {

  public JFaceTest() {
    super(null);
  }

  
  
  protected Control createContents(Composite parent) {
    Text helloText = new Text(parent, SWT.CENTER);
    helloText.setText("Hello SWT");
    parent.pack();
    return parent;  
  }
  
  
  public static void main(String[] args) {
    JFaceTest test = new JFaceTest();
    test.setBlockOnOpen(true);

    test.open();
    Display.getCurrent().dispose();
  }
}
