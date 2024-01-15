package common.ui.link;
import java.util.*;
import javax.swing.event.*;

public class DefaultPageModel
  implements PageModel
{
  protected Page thisPage;
  protected List inboundLinks = new ArrayList();
  protected List outboundLinks = new ArrayList();
  protected ArrayList listeners = new ArrayList();

  public DefaultPageModel()
  {
    thisPage = new Page("center-page", "center-page.html");
  
    inboundLinks.add(new Page("in-link-1", "in-link-1.html"));
    inboundLinks.add(new Page("in-link-2", "in-link-2.html"));
    inboundLinks.add(new Page("in-link-3", "in-link-3.html"));
    inboundLinks.add(new Page("in-link-4", "in-link-4.html"));
    
    outboundLinks.add(new Page("out-link-1", "out-link-1.html"));
    outboundLinks.add(new Page("out-link-2", "out-link-2.html"));
    outboundLinks.add(new Page("out-link-3", "out-link-3.html"));
    outboundLinks.add(new Page("out-link-4", "out-link-4.html"));
    outboundLinks.add(new Page("out-link-5", "out-link-5.html"));
  }
  
  public Page getThisPage()
  {
    return thisPage;
  }
  
  public void setThisPage(Page page)
  {
    thisPage = page;
    fireChangeEvent();
  }
  
  public int getInboundLinkCount()
  {
    return inboundLinks.size();
  }
  
  public Page getInboundLink(int index)
  {
    return (Page)inboundLinks.get(index);
  }
  
  public void addInboundLink(Page page)
  {
    inboundLinks.add(page);
    fireChangeEvent();
  }
  
  public int getOutboundLinkCount()
  {
    return outboundLinks.size();
  }
  
  public Page getOutboundLink(int index)
  {
    return (Page)outboundLinks.get(index);
  }

  public void addOutboundLink(Page page)
  {
    outboundLinks.add(page);
    fireChangeEvent();
  }
  
  public void addChangeListener(ChangeListener listener)
  {
    listeners.add(listener);
  }
  
  public void removeChangeListener(ChangeListener listener)
  {
    listeners.remove(listener);
  }
  
  public void fireChangeEvent()
  {
    List list = (ArrayList)listeners.clone();
    ChangeEvent event = new ChangeEvent(this);
    for (int i = 0; i < list.size(); i++)
    {
      ChangeListener listener = (ChangeListener)list.get(i);
      listener.stateChanged(event);
    }
  }
}

