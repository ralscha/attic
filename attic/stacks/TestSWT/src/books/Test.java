package books;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Test {

  public static void main(String[] args) {
    final Display display = new Display();
    Shell shell = new Shell(display);
    
    shell.setSize(600, 400);
   
    Canvas canvas = new Canvas(shell, SWT.BORDER);
    canvas.setSize(150, 150);
    canvas.setLocation(20, 20);       
             
    canvas.addPaintListener(new PaintListener() {
      public void paintControl(PaintEvent e) {
        GC gc = e.gc;
           gc.drawRectangle(3, 5, 20, 25);
           gc.fillRectangle(30, 5, 20, 25);
           Color blue = display.getSystemColor(SWT.COLOR_BLUE);
           Color red = display.getSystemColor(SWT.COLOR_RED);
    
           gc.setForeground(blue);
    
           gc.drawLine(80, 20, 100, 80);
           gc.drawOval(40, 40, 10, 10);
           gc.drawPolygon(new int[]{100, 100, 120, 120, 140, 100});
    
           gc.setBackground(red);
           gc.fillRectangle(20, 100, 20, 20);
           gc.fillRectangle(50, 100, 20, 20);
           gc.drawRectangle(50, 100, 20, 20);

           gc.drawString("Text", 120, 20);
           gc.setClipping(40, 60, 40, 40);
           gc.fillOval(30, 50, 30, 25);
    
                  
      }
    });         
    shell.open();
    
    
    
    

    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    shell.dispose();
    
  }
}
