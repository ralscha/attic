import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.mortbay.http.HttpContext;
import org.mortbay.http.HttpServer;
import org.mortbay.http.SocketListener;
import org.mortbay.http.handler.ResourceHandler;
import org.mortbay.jetty.servlet.ServletHandler;

public class Server {

  static HttpServer server;
  static Button startButton;
  static Button stopButton;
  
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
          // Create the server
          server=new HttpServer();
            
          // Create a port listener
          SocketListener listener=new SocketListener();
          listener.setPort(8181);
          server.addListener(listener);

          // Create a context 
          HttpContext context = new HttpContext();
          context.setContextPath("/services/*");
          server.addContext(context);
            
          // Create a servlet container
          ServletHandler servlets = new ServletHandler();
          context.addHandler(servlets);

          // Map a servlet onto the container
          servlets.addServlet("hello","/hello/*",BasicService.class.getName());
            
          // Serve static content from the context
          String home = System.getProperty("jetty.home",".");
          context.setResourceBase(home+"/demo/webapps/jetty/tut/");
          context.addHandler(new ResourceHandler());

          // Start the http server
          server.start ();         

          startButton.setEnabled(false);
          stopButton.setEnabled(true);

        } catch (Exception e) {
          e.printStackTrace();
        }
      }

    };

    SelectionListener stopListener = new SelectionAdapter() {
      public void widgetSelected(SelectionEvent event) {
        try {
          server.stop();
          
          startButton.setEnabled(true);
          stopButton.setEnabled(false);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }        
      }
    };
    
    startButton = new Button(shell, SWT.PUSH);
    startButton.setText("Start Server");
    startButton.addSelectionListener(startListener);
    
    stopButton = new Button(shell, SWT.PUSH);
    stopButton.setText("Stop Server");
    stopButton.addSelectionListener(stopListener);
    stopButton.setEnabled(false);
    
    shell.setText("Server");
    shell.pack();

    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch())
        display.sleep();
    }
    display.dispose();

    
    


    
  }

}
