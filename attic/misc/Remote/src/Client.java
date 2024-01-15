import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.caucho.hessian.client.HessianProxyFactory;

public class Client {

  static Label resultLabel = null;
  
  public static void main(String[] args) {

    Display display = new Display();
    Shell shell = new Shell(display);

    
    RowLayout layout = new RowLayout(SWT.VERTICAL);
    layout.wrap = true;
    layout.fill = true;
    layout.justify = false;
    shell.setLayout(layout);


    
    
    SelectionListener startListener = new SelectionAdapter() {
      public void widgetSelected(SelectionEvent event) {
        try {
          String url = "http://localhost:8181/services/hello";

          HessianProxyFactory factory = new HessianProxyFactory();
          HelloInterface basic = (HelloInterface)factory.create(HelloInterface.class, url);

          resultLabel.setText(basic.hello());
        } catch (Exception e) {
          resultLabel.setText(e.toString());
        }
      }

    };

    Button startButton = new Button(shell, SWT.PUSH);
    startButton.setText("Get Message from Server");
    startButton.addSelectionListener(startListener);
    
    Label testLabel = new Label(shell, SWT.CENTER);
    testLabel.setText("Die Antwort vom Server ist: ");
    
    resultLabel = new Label(shell, SWT.CENTER);
    resultLabel.setText("");
    

    shell.setText("Client");
    shell.pack();

    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch())
        display.sleep();
    }
    display.dispose();

  }

}
