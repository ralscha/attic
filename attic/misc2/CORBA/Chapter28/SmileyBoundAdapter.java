import java.awt.*;
import java.beans.*;
public class SmileyBoundAdapter implements PropertyChangeListener {
    private SmileyBean target;

    SmileyBoundAdapter(SmileyBean t) {
        target = t;
    }

    public void propertyChange(PropertyChangeEvent arg0) {
      target.toggleSmile();
    }
}
