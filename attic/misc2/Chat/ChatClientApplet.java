
import java.awt.*;
import java.rmi.*;
import java.awt.event.*;
import java.applet.*;
import java.util.Vector;
import java.util.StringTokenizer;

public class ChatClientApplet extends Applet implements Runnable {
    List user_list = new List();
    TextArea chat_area = new TextArea();
    TextField chat_field = new TextField();
    TextField login_name = null;
    ChatServer server = null;
    PopupMenu popup;
    boolean stopThread = false;  
    Thread thread = null;
  
    public void init() {
        
        //System.runFinalizersOnExit(true);
        
        Panel p = new Panel();
        Panel main = new Panel();
    
        p.setLayout(new BorderLayout ());
    
        chat_area.setEditable (false);
        chat_area.setForeground (Color.blue);
        user_list.addMouseListener (new ClientMouseListener (this));
    
        p.add ("Center",chat_area);
        p.add ("East",user_list);
        
        main.setLayout (new BorderLayout ());
        main.add ("Center",p);
        main.add ("South",chat_field);
        chat_field.addActionListener (new ClientActionListener (this,ClientActionListener.CHAT));
        
        setLayout (new CardLayout ());
        add("login", createLoginScreen ());
        add("main", main);

        popup=new PopupMenu ();
             
        MenuItem item=new MenuItem ("Logout");
        item.addActionListener (new ClientActionListener (this,ClientActionListener.LOGOUT));
             
        popup.add (item);
        add (popup);
        
        System.out.println ("Trying to connect to server...");    
        try {
          server=(ChatServer) Naming.lookup ("//" + getCodeBase().getHost() + ":2005/Chat");
        } catch (Exception e)
        {  chat_area.setText ("Could not open connection to server."); return; }
        
        
        System.out.println ("Connection established.");
    }
  
  Panel createLoginScreen ()
  {
    Panel p=new Panel ();
    p.setLayout (new FlowLayout ());
    
    Button b=new Button ("Login");
    b.addActionListener (new ClientActionListener (this,ClientActionListener.LOGIN));
    
    p.add ("Center",login_name=new TextField (20));
    login_name.addActionListener (new ClientActionListener (this,ClientActionListener.LOGIN));

    p.add ("East",b);
    
    return p;
  }
  
    public void finalize() {
        System.out.println("finalize");
        stopAndLogout();        
    }
  
    void stopAndLogout() {
        if (!stopThread) {
            try {
                server.logout(login_name.getText());
            } catch (Exception e) {}
            stopThread = true;
        }         
    }
  
    public void destroy() {
        System.out.println("destroy");
        stopAndLogout();
    }
  
    public void run() {
        ChatServerEvent[] events;
        ChatServerEvent evt = null;
        
        try {
            while(!stopThread) {
                while ((events = server.waitForEvent(login_name.getText())) == null);
        
                for (int i = 0; i < events.length; i++) {
                    
                    evt = events[i];
                    
                    switch (evt.getCommand ()) {
                        case evt.LIST_UPDATE:
                            Vector args = (Vector) evt.getArgs ();
                            fillUserList (args);
                            break;
                        case evt.LOGOUT:
                            System.out.println("Logged out.");
                            ((CardLayout)getLayout()).show(this, "login");
                            thread.stop();
                            thread = null;                  
                            return;
                        case evt.MESSAGE:
                            String string=(String) evt.getArgs();
                            String current="";
               
                            StringTokenizer t=new StringTokenizer (string," ");
                            while (t.hasMoreTokens()) {
                                string=t.nextToken();
                                if (chat_area.getFontMetrics(chat_area.getFont()).stringWidth (current+string+10)>chat_area.getSize().width) {
                                    chat_area.append (current+"\n");
                                    current=string+" ";
                                } else
                                    current+=string+" ";
                            }
                
                            chat_area.append (current+"\n");
                            break;
                    }
                }
            }
        } catch (Exception e) {}
    }
  
  void fillUserList (Vector names)
  {
    user_list.removeAll ();
    
    for (int i=0;i<names.size();i++)
      if (names.elementAt (i).equals (login_name.getText()))
        user_list.add ("< " + names.elementAt (i) + " >");
      else
        user_list.add (names.elementAt (i) + "");
  }
   
    public void onLogin() {
        try {
            if (server.login (login_name.getText())) {
                ((CardLayout) getLayout()).show (this,"main");
                thread = new Thread(this);
                thread.start();
            }
            else
                login_name.requestFocus();
        } catch (Exception e) { }
    }
  
    public void onChat() {
        try {
          server.tell(login_name.getText(),chat_field.getText ());
          chat_field.setText("");
        } catch (Exception e) {}
  }
  
    public void onLogout() {
        try {
            server.logout(login_name.getText ());
        } catch (Exception e) {}
    }
  
    public void showPopup(MouseEvent e) {
        if (e.isPopupTrigger())
           popup.show(e.getComponent(), e.getX(), e.getY());
    }
  
  class ClientMouseListener extends MouseAdapter
  {
    ChatClientApplet applet;
    
    public ClientMouseListener (ChatClientApplet applet)
    {
      this.applet=applet;
    }
    
    public void mouseReleased (MouseEvent e)
    {
       applet.showPopup (e);
     }
   }
}
