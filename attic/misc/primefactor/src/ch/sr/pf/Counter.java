package ch.sr.pf;
  public class Counter {
    private int count;

    public Counter() {
      count = 1;
    }
    
    public void inc() {
      count++;
    }

    public int getCount() {
      return count;
    }
  }