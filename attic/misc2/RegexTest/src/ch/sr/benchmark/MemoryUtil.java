package ch.sr.benchmark;


public class MemoryUtil {

  public static long getUsedMemoryGC() {
    collectGarbage();
    long totalMemory = Runtime.getRuntime().totalMemory();
    long freeMemory = Runtime.getRuntime().freeMemory();
    long usedMemory = totalMemory - freeMemory;
    return usedMemory;
  }

  public static long getUsedMemory() {
    long totalMemory = Runtime.getRuntime().totalMemory();
    long freeMemory = Runtime.getRuntime().freeMemory();
    long usedMemory = totalMemory - freeMemory;
    return usedMemory;
  }


  public static void collectGarbage() {

    System.gc();
    try {
      // Give the garbage collector time to work
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      // just continue
    }

    System.runFinalization();
    try {
      // Give the garbage collector time to work
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      // just continue
    }

    System.gc();
    try {
      // Give the garbage collector time to work
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      // just continue
    }
    System.gc();
    try {
      // Give the garbage collector time to work
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      // just continue
    }

  }

}