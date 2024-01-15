package ch.sr.tail;
import java.io.*;

public class TailWorker implements Runnable {

  private int refreshRate = 1; // in seconds
  private BufferedReader br;
  private File file;
  private Thread tailThread;
  private UpdateMediator mediator;

  public void startTail(String fileName, UpdateMediator mediator) throws IOException {
    startTail(new File(fileName), mediator);    
  }
  
  public void startTail(File file, UpdateMediator mediator) throws IOException {
    this.mediator = mediator;
    this.file = file;
    br = new BufferedReader(new FileReader(file));

    tailThread = new Thread(this);
    tailThread.start();
  }
  
  public void stopTail() throws IOException {    
    if (tailThread != null) {
      tailThread.interrupt();
    }

    if (br != null) {
      br.close();
    }   
    
  }

  public void run() {
    String line = null;
    long lastLength = 0;
    long currentLength = 0;

    while (true) {

      do {
        try {
          Thread.sleep(refreshRate * 1000L);
        } catch (InterruptedException ie) {
          return;
        }

        currentLength = file.length();
      } while (lastLength == currentLength);

      lastLength = currentLength;

      do {
        try {
          line = br.readLine();
        } catch (IOException ioe) {
          ioe.printStackTrace();
        }

        if (line != null) {
          mediator.addLine(line);
        }
      } while (line != null);
    }
  }


}
