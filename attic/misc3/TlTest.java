import java.util.*; 

public class TlTest { 

  static class Singleton { 
    private static Object o; 
    public static synchronized Object getSomething() { 
      if (o == null) 
        o = new Object(); 
      return o; 
    } 
  } 

  static class NSingleton { 
    private static Object o; 
    public static Object getSomething() { 
      if (o == null) 
        o = new Object(); 
      return o; 
    } 
  } 

  static class TlSingleton { 
    private static ThreadLocal tl = new ThreadLocal(); 
    public static Object getSomething() { 
      Object o = tl.get(); 
      if (o == null) { 
        synchronized (TlSingleton.class) { 
          o = Singleton.getSomething(); 
        } 
        tl.set(o); 
      } 
      return o; 
    } 
  } 

  static int iterations=200000; 
  static int nThreads = 5; 

  static class SThread extends Thread { 
    public void run() { 
      for (int i=0; i<iterations; i++) { 
        Object o = Singleton.getSomething(); 
      } 
    } 
  } 
  static class TThread extends Thread { 
    public void run() { 
      for (int i=0; i<iterations; i++) { 
        Object o = TlSingleton.getSomething(); 
      } 
    } 
  } 
  static class NThread extends Thread { 
    public void run() { 
      for (int i=0; i<iterations; i++) { 
        Object o = NSingleton.getSomething(); 
      } 
    } 
  } 

  public static void main(String[] args) throws Exception { 
    Timer t = new Timer(); 

    if (args.length >= 2) 
      nThreads = Integer.parseInt(args[1]); 
    if (args.length >= 3) 
      iterations = Integer.parseInt(args[2]); 

    Thread threads[] = new Thread[nThreads]; 

    for (int i=0; i<nThreads; i++) { 
      if (args[0].equals("S")) 
        threads[i] = new SThread(); 
      else if (args[0].equals("N")) 
        threads[i] = new NThread(); 
      else if (args[0].equals("T")) 
        threads[i] = new TThread(); 
    } 

    t.start(); 
    for (int i=0; i<nThreads; i++) 
      threads[i].start(); 

    for (int i=0; i<nThreads; i++) 
      threads[i].join(); 

    t.stop(); 
    double sec = t.getTime() / 1000.0; 
    System.out.println("Total time: " + sec + "s" 
                       + " (" + nThreads + " threads, " + iterations + 
" iterations)"); 
  } 
} 

 class Timer { 
  long startTime, endTime, lastPauseTime, totalPauseTime; 
  boolean running = false, paused = false; 

  public Timer() { 
  } 

  public void start() { 
    startTime = System.currentTimeMillis(); 
    running = true; 
  } 

  public void stop() { 
    endTime = System.currentTimeMillis(); 
    running = false; 
  } 

  public void pause() { 
    lastPauseTime = System.currentTimeMillis(); 
    paused = true; 
  } 

  public void resume() { 
    if (paused) { 
      long now = System.currentTimeMillis(); 
      totalPauseTime += (now-lastPauseTime); 
      paused = false; 
    }; 
  } 

  public long getTime() { 
    if (running) 
      return System.currentTimeMillis() - startTime - totalPauseTime; 
    else 
      return endTime - startTime - totalPauseTime; 
  } 
} 