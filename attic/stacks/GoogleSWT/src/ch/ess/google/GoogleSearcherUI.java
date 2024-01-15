package ch.ess.google;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

public class GoogleSearcherUI {

  Mediator mediator;

  public void setMediator(Mediator mediator) {
    this.mediator = mediator;
  }  
  

  public Shell buildUI() {
    Display display = new Display();
    final Shell shell = new Shell(display);
    
    shell.setSize(750, 400);
    shell.setLayout(new GridLayout());

    shell.setText("Google Searcher");
    {
    	Menu menubar=new Menu(shell, SWT.BAR);
    	shell.setMenuBar(menubar);
    	{
    		final MenuItem menuItem = new MenuItem(menubar, SWT.CASCADE);
    		menuItem.setText("&File");
    		{
    			Menu popupmenu=new Menu(menuItem);
    			menuItem.setMenu(popupmenu);
    			{
    				final MenuItem menuItem_1 = new MenuItem(popupmenu, SWT.NONE);
    				menuItem_1.addSelectionListener(new SelectionAdapter() {
    					public void widgetSelected(SelectionEvent e) {
                System.exit(0);
    					}
    				});
    				menuItem_1.setText("E&xit");                       
    			}
    		}
    	}
    }
    {
      final Group group = new Group(shell, SWT.NONE);
      final GridLayout gridLayout = new GridLayout();
      gridLayout.numColumns = 3;
      group.setLayout(gridLayout);
      group.setText("Input");
      group.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL));

      {
      	final Text searchText = new Text(group, SWT.BORDER);
      	searchText.addModifyListener(new ModifyListener() {
      		public void modifyText(ModifyEvent e) {
            mediator.setSearchText(searchText.getText());
      		}
      	});
      	searchText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));         
      }
      {
        Button button = new Button(group, SWT.NONE);
        button.addSelectionListener(new StartAction(mediator));
        button.setText("Search");
        shell.setDefaultButton(button);
        mediator.setSearchButton(button);
      }
      {
        final Button button = new Button(group, SWT.NONE);
        button.addSelectionListener(new StopAction(mediator));
        button.setEnabled(false);
        button.setText("Cancel");
        mediator.setCancelButton(button);
      }
    }
    {
      final Group group = new Group(shell, SWT.NONE);
      group.setLayout(new GridLayout());
      group.setText("Result");
      group.setLayoutData(new GridData(GridData.FILL_BOTH));
      {
        final Table table = new Table(group, SWT.BORDER);
        table.setLayoutData(new GridData(GridData.FILL_BOTH));
        table.setLinesVisible(true);
        table.setHeaderVisible(true);
        {
          final TableColumn tableColumn = new TableColumn(table, SWT.NONE);
          tableColumn.setWidth(407);
          tableColumn.setText("Titel");
        }
        {
          final TableColumn tableColumn = new TableColumn(table, SWT.NONE);
          tableColumn.setWidth(301);
          tableColumn.setText("URL");
        }
        mediator.setTable(table);
      }
    }
    {
    	final Composite composite = new Composite(shell, SWT.BORDER);
    	composite.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
    	final GridLayout gridLayout = new GridLayout();
    	gridLayout.horizontalSpacing = 0;
    	gridLayout.marginWidth = 2;
    	gridLayout.marginHeight = 1;
    	gridLayout.numColumns = 2;
    	composite.setLayout(gridLayout);    	
    	{
    		Label statusLabel = new Label(composite, SWT.NONE);
    		statusLabel.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL));
    		statusLabel.setText("Ready");
        mediator.setStatusLabel(statusLabel);
    	}
      {
        ProgressBar progress = new ProgressBar(composite, SWT.INDETERMINATE | SWT.SMOOTH);          
        progress.setVisible(false);
      
        mediator.setProgress(progress);                 
      }
    }
    
    return shell;
  }


}
