

public class HelloWorld implements HelloWorldMBean {
  private int howManyTimes;

  public void reloadConfiguration() {
    System.out.println("reloadConfiguration");
    howManyTimes++;
  }

  public int getHowManyTimes() {
    return howManyTimes;
  }

}