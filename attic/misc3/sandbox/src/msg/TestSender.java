package msg;

import java.io.InputStream;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.ubermq.jms.client.PipeConnectionFactory;
import com.ubermq.jms.server.MessageServer;

public class TestSender {

  public static void main(String[] args) {
    try {
      
      
      InputStream is = TestSender.class.getResourceAsStream("server.properties");
      Properties props = new Properties();
      props.load(is);
      is.close();
      
      props.list(System.out);
      
      
      MessageServer server = new MessageServer(props){
        public void addStandardProtocols()
        {
          add(new DefaultProtocol(getDatagramFactory(),
                                  DefaultProtocol.getProtocolPort()));
          //add(new AdminProtocol());
          //add(new SSLProtocol(getDatagramFactory(),
          //                    this,
          //                    SSLProtocol.getProtocolPort()));
            
        }
      };
      server.addStandardProtocols();
      server.run();    
      
      
      
      PipeConnectionFactory factory = new PipeConnectionFactory(server);
      
            
      Connection conn = factory.createConnection();      
      Session sess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);            
      Queue queue = sess.createQueue("myqueue");            


      MessageConsumer consumer = sess.createConsumer(queue);
      
      consumer.setMessageListener(new MessageListener() {
        public void onMessage(Message msg) {
          try {
            System.out.println(((TextMessage)msg).getText());
          } catch (JMSException e) {            
            e.printStackTrace();
          }
        }
      });
      
      MessageProducer producer = sess.createProducer(queue);
            
      TextMessage msg = sess.createTextMessage();
      msg.setText("this is a text");      
      producer.send(msg);
      
      producer.close();
      
      sess.close();
      conn.close();
         
      
      Thread.sleep(10000);
      
      server.stop();
      System.out.println("the end");
      
    } catch (Exception e) {      
      e.printStackTrace();
    } 
  }
}
