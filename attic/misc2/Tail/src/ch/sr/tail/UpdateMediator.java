package ch.sr.tail;
import org.eclipse.swt.widgets.*;

public class UpdateMediator {

  private final static int SHOW_LINES = 500;

  private Display display;
  private List list;
  

  public UpdateMediator(Display display, List list) {
    this.display = display;
    this.list = list;
  }

  public Display getDisplay() {
    return display;
  }

  public void setList(List list) {
    this.list = list;
  }

  public void addLine(final String line) {
    display.asyncExec(new Runnable() {
      public void run() {
        list.add(line);
        
        int itemCount = list.getItemCount();
        
        if (itemCount > SHOW_LINES) {
          list.remove(0, itemCount - SHOW_LINES);
        }
        
        int ix = list.getItemCount() - 1;
        list.select(ix);
        list.showSelection();
        list.deselect(ix);
      }
    });
  }

}
