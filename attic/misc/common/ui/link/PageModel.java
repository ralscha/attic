package common.ui.link;

import javax.swing.event.*;

public interface PageModel
{
  public Page getThisPage();
  
  public int getInboundLinkCount();
  public Page getInboundLink(int index);
  
  public int getOutboundLinkCount();
  public Page getOutboundLink(int index);
  
  public void addChangeListener(
    ChangeListener listener);
  public void removeChangeListener(
    ChangeListener listener);
}

