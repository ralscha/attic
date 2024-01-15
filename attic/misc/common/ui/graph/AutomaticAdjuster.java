package common.ui.graph;

public class AutomaticAdjuster extends Thread
{
  protected boolean stop = false;
  protected GraphAdjuster adjuster;
  protected int interval;
  
  public AutomaticAdjuster(GraphAdjuster adjuster)
  {
    this(adjuster, 100);
  }
  
  public AutomaticAdjuster(GraphAdjuster adjuster, int interval)
  {
    this.adjuster = adjuster;
    this.interval = interval;
    start();
  }
  
  public void run()
  {
    while (!stop)
    {
      adjuster.adjust();
      try
      {
        Thread.sleep(interval);
      }
      catch (InterruptedException e)
      {
        break;
      }
    }
  }

  public void quit()
  {
    stop = true;
  }
}

