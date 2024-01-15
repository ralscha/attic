package books;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class Test2 {

  public static void main(String[] args) {
    final Display display = new Display();
    final Shell shell = new Shell();
    final GridLayout gridLayout = new GridLayout();
    gridLayout.numColumns = 2;
    shell.setLayout(gridLayout);
    shell.setText("SWT Application");

    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch())
        display.sleep();
    }
    {
    	final Text text = new Text(shell, SWT.BORDER);
    	text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    	text.setText("text");
    }
    {
    	final CoolBar coolBar = new CoolBar(shell, SWT.NONE);
    	{
    		final CoolItem coolItem = new CoolItem(coolBar, SWT.PUSH);
    		coolItem.setText("New item");
    	}
    }
    {
    	final Button button = new Button(shell, SWT.NONE);
    	button.setText("button");
    }
    {
    	final List list = new List(shell, SWT.BORDER);
    	final GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.FILL_VERTICAL);
    	gridData.horizontalSpan = 2;
    	list.setLayoutData(gridData);
    }
  }
}
