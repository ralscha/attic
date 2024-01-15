import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;

public class JabberTest {

  public static void main(String[] args) {

    try {

      //XMPPConnection.DEBUG_ENABLED = true;

      XMPPConnection con = new XMPPConnection("jabber.org");
      con.login("ralsa", "gartenanbau98");
      //con.createChat("rasch@swissjabber.ch").sendMessage("Howdy!");

      Chat chat = con.createChat("rasch@swissjabber.ch");
      chat.sendMessage("hey doodie.");

      while (true) {
        System.out.println(chat.nextMessage().getBody());
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
