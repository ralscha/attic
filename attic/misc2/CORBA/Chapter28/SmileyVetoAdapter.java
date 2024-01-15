import java.awt.*;
import java.beans.*;

public class SmileyVetoAdapter implements VetoableChangeListener
{
  private SmileyBean target;

  SmileyVetoAdapter(SmileyBean t)
  {
      target = t;
  }

  public void vetoableChange(PropertyChangeEvent arg0) throws PropertyVetoException
  {
      target.vetoableChange(arg0);
  }
}
