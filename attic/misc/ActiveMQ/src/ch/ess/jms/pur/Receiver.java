package ch.ess.jms.pur;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.codehaus.activemq.ActiveMQConnectionFactory;
import org.codehaus.activemq.message.ActiveMQQueue;

public class Receiver implements MessageListener{

  public Receiver() {
    try {
      ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("zeroconf:_activemq.broker.development.");
      connectionFactory.setUseEmbeddedBroker(true);
      
      connectionFactory.setBrokerXmlConfig("file:src/bdb2.xml");

      Destination destination = new ActiveMQQueue("SampleQueue1");
      
      //Set up the JMS objects needed.
      Connection con = connectionFactory.createQueueConnection();
      //    This session is not transacted.
      Session receiverSession = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
      MessageConsumer consumer = receiverSession.createConsumer(destination);
      consumer.setMessageListener(this);
      //    enable message transfer
      con.start();
      //    Receive the message
      //TextMessage textMessage;
      //while(true) {
        //textMessage = (TextMessage)consumer.receive();
        //System.out.println("Consumer received a message: " + textMessage.getText());
        Thread.sleep(1000000);
      //}
    } catch (Exception badStuffHappens) {
      badStuffHappens.printStackTrace();
    }  
  }
  
  public static void main(String[] args) {
    new Receiver();
  }

  public void onMessage(Message msg) {
    try {
      TextMessage o = (TextMessage)msg;
      System.out.println(o.getText());
    } catch (JMSException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
  }

}
