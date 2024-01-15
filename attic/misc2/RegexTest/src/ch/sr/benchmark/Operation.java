
package ch.sr.benchmark;

public interface Operation {
   public void warmUp();
   public void execute();
   public int getIterationCount();
}
