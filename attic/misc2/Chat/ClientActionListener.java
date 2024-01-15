import java.awt.event.*;

public class ClientActionListener implements ActionListener {
    ChatClientApplet applet = null;
    int command = 0;
    
    public final static int LOGIN  = 1;
    public final static int CHAT   = 2;
    public final static int LOGOUT = 3;
    
    public ClientActionListener(ChatClientApplet applet, int command) {
        this.applet = applet;
        this.command = command;      
    }
    
    public void actionPerformed(ActionEvent e) {
        switch (command) {
            case LOGIN:
              applet.onLogin();
              break;
            case CHAT:
              applet.onChat();
              break;
            case LOGOUT:
              applet.onLogout();
              break;
        }
    }
}
