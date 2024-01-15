

import java.io.PrintWriter;

import org.apache.commons.net.ProtocolCommandEvent;
import org.apache.commons.net.ProtocolCommandListener;

/***
 * This is a support class for some of the example programs.  It is
 * a sample implementation of the ProtocolCommandListener interface
 * which just prints out to a specified stream all command/reply traffic.
 * <p>
 ***/

public class PrintCommandListener implements ProtocolCommandListener {
  private PrintWriter writer;

  public PrintCommandListener(PrintWriter writer) {
    this.writer = writer;
  }

  public void protocolCommandSent(ProtocolCommandEvent event) {
    this.writer.print(event.getMessage());
    this.writer.flush();
  }

  public void protocolReplyReceived(ProtocolCommandEvent event) {
    this.writer.print(event.getMessage());
    this.writer.flush();
  }
}