import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


public class TestSWT {

  public static void main(String[] args) {
    final Display display = new Display();
    final Shell shell = new Shell();
    final GridLayout gridLayout = new GridLayout();
    gridLayout.numColumns = 2;
    shell.setLayout(gridLayout);
    shell.setText("SWT Application");
    {
    	final Button button = new Button(shell, SWT.NONE);
    	final GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
    	gridData.horizontalSpan = 5;
    	button.setLayoutData(gridData);
    	button.setText("button");
    }
    // DESIGNER: Add controls before this line.
    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch())
        display.sleep();
    }
  }
}
