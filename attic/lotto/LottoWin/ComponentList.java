import java.awt.*;
import java.util.*;

class ComponentList {
    private Vector list;

    public ComponentList(int numb) {
        list = new Vector(numb);
    }

    public ComponentList() {
        list = new Vector();
    }

    public void reset() {
        list.removeAllElements();
    }

    public void addComponent(Component c) {
        
        list.addElement(c);
    }

    public Component nextComponent(Object c)
    {
        int ix;

        if (list.isEmpty())
            return (null);

        ix = list.indexOf(c);

        if (ix == -1)
            return (null);

        if (ix < list.size()-1)
            return((Component)list.elementAt(ix+1));

        return((Component)list.elementAt(0));
    }

    public Component prevComponent(Object c)
    {
        int ix;

        if (list.isEmpty())
            return (null);

        ix = list.indexOf(c);

        if (ix == -1)
            return (null);

        if (ix >= 1)
            return((Component)list.elementAt(ix-1));

        return((Component)list.elementAt(list.size()-1));
    }
}