package books;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class Test4 implements SelectionListener {

  public void widgetDefaultSelected(SelectionEvent arg0) {
    //
  }

  public void widgetSelected(SelectionEvent arg0) {
//
  }

  Test4() {
    final Display display = new Display();
    final Shell shell = new Shell();
    final GridLayout gridLayout = new GridLayout();
    gridLayout.numColumns = 2;
    shell.setLayout(gridLayout);
    shell.setText("SWT Application");
       {
    	final Button button_1 = new Button(shell, SWT.NONE);
    	button_1.setText("button");
    }

      Button button = new Button(shell, SWT.NONE);
      button.setText("button");
  
      Text text = new Text(shell, SWT.BORDER);
      text.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
      text.setText("text2");
 
 
      button = new Button(shell, SWT.NONE);
      button.addSelectionListener(this);
      button.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL));
      button.setText("button");
    
      button = new Button(shell, SWT.NONE);
      button.addSelectionListener(this);
      button.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL));
      button.setText("button");
      
      
      
 

  
      text = new Text(shell, SWT.BORDER);
      text.setLayoutData(new GridData(GridData.FILL_BOTH));
      text.setText("text");
   

    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch())
        display.sleep();
    }  
  }
  
  public static void main(String[] args) {
    new Test4();
  }
}
