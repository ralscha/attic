/*
 *  Grundgeruest.java 18/09/02
 *  (c) 2002 by HaraX
 *   
 *  Ein Grundgerüst für eine SWT-Applikation.
 * 
 */


package ch.sr.tail;


import java.io.*;

import org.eclipse.swt.*;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

import com.javadude.swt.layouts.*;

public class Tail {
  private Display d;
  private Shell s;
  private File file;

  public Tail(String[] args) {
    
    boolean hasArg = ((args != null) && (args.length > 0));
    
    d = new Display();    
    s = new Shell(d);


    //Drag and Drop
    DropTarget dropTarget = new DropTarget(s, DND.DROP_MOVE);
    dropTarget.setTransfer(new Transfer[] { FileTransfer.getInstance() });
    dropTarget.addDropListener(new DropTargetAdapter() {
      public void drop(DropTargetEvent event) {
        System.out.println("Droping data: " + event.data);
        for (int i = 0; i < ((String[])event.data).length; i++) {
          System.out.println(((String[])event.data)[i]);
        }
      }
      public void dragLeave(DropTargetEvent event){
        System.out.println("Drag Leave");
      }
      public void dropAccept(DropTargetEvent event){
        System.out.println("Drop Accept");
      }
      public void dragOver(DropTargetEvent event){
        //System.out.println("Drag Over");
      }
    });


    BorderLayout layout = new BorderLayout();
    s.setLayout(layout);
    
    final Font f = new Font(d, new FontData("Courier New", 8, SWT.NORMAL));
    
    s.addDisposeListener (new DisposeListener () {
      public void widgetDisposed (DisposeEvent e) {
        f.dispose();
      }
    });    
    
    final List l = new List(s, SWT.SINGLE | SWT.V_SCROLL);
    l.setLayoutData(BorderLayout.CENTER);


    l.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        if (e.character == 'V' - 0x40) {
          Clipboard clip = new Clipboard(d);
          
          Object contents = clip.getContents(FileTransfer.getInstance());
          if (contents != null) {
            String[] files = (String[])contents;
            for (int i = 0; i < files.length; i++) {
              System.out.println(files[i]);
            }
          }
          
          clip.dispose();
        }
      } 
    
    });


    l.setFont(f);
    final TailWorker tail = new TailWorker();
    final UpdateMediator mediator = new UpdateMediator(d, l);


    
    s.setSize(1000, 200); 
    s.setText("Tail"); 

    Menu menuBar = new Menu(s, SWT.BAR);
    s.setMenuBar(menuBar);
    
    MenuItem item = new MenuItem(menuBar, SWT.CASCADE);
    item.setText("&File");

    
    Menu menu = new Menu(s, SWT.DROP_DOWN);
    item.setMenu(menu);
    
    MenuItem openItem = new MenuItem(menu, SWT.NULL);
    openItem.setText("&Open ...");    
    
    MenuItem subItem = new MenuItem(menu, SWT.SEPARATOR);    
    
    final MenuItem startItem = new MenuItem(menu, SWT.NULL);
    startItem.setText("&Start");
    startItem.setEnabled(false);
  
    final MenuItem stopItem = new MenuItem(menu, SWT.NULL);
    stopItem.setText("S&top");
    stopItem.setEnabled(false);  

    
    startItem.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent e) {
        stopItem.setEnabled(true);
        startItem.setEnabled(false);
        try {
          tail.stopTail();          
          l.remove(0, l.getItemCount()-1);          
          tail.startTail(file, mediator);
        } catch (IOException ioe) {
          ioe.printStackTrace();
        }
        
      }
    });

    stopItem.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent e) {
        stopItem.setEnabled(false);
        startItem.setEnabled(true);
        try {
          tail.stopTail();
        } catch (IOException ioe) {
          ioe.printStackTrace();
        }
      }
    });

    subItem = new MenuItem(menu, SWT.SEPARATOR);    

    subItem = new MenuItem(menu, SWT.NULL);
    subItem.setText("E&xit");
    subItem.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent e) {
        s.close();
      }
    });
    

    openItem.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent e) {
        FileDialog fd = new FileDialog(s, SWT.OPEN);
        fd.open();
        
        String fn = fd.getFileName();
        if ((fn != null) && !fn.trim().equals("")) {
          try {
            file = new File(fd.getFilterPath(), fd.getFileName());
            s.setText("Tail - " + file.getPath());
            tail.stopTail();
            
            l.remove(0, l.getItemCount()-1);
            
            tail.startTail(file, mediator);
            
            stopItem.setEnabled(true);
          } catch (IOException ioe) {
            ioe.printStackTrace();
          }
        }
        
      }
       
    });


    s.open();
                  
      
    if (hasArg) {
      try {
        file = new File(args[0]);
        s.setText("Tail - " + file.getPath());
        tail.stopTail();
        
        l.remove(0, l.getItemCount()-1);
        
        tail.startTail(file, mediator);
        
        stopItem.setEnabled(true);  
      } catch (IOException e) {
        s.close();
        e.printStackTrace();
      }    
    }  
      
      
    while (!s.isDisposed()) { //ist das Fenster noch sichtbar?
      if (!d.readAndDispatch()) //lies ein Event aus der Queue und verarbeite es weiter
        d.sleep(); //auf das nächste Event warten
    } //end while
    
    try {
      tail.stopTail();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    new Tail(args);
  }
}