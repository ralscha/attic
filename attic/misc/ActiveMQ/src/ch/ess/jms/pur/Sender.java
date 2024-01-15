package ch.ess.jms.pur;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.codehaus.activemq.ActiveMQConnectionFactory;
import org.codehaus.activemq.message.ActiveMQQueue;

public class Sender {

  public static void main(String[] args) {

    try {

      ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("zeroconf:_activemq.broker.development.");
      connectionFactory.setUseEmbeddedBroker(true);
      

      connectionFactory.setBrokerXmlConfig("file:src/bdb1.xml");
      
      Destination destination = new ActiveMQQueue("SampleQueue1");
   
      //    Set up the JMS objects needed
      Connection con = connectionFactory.createQueueConnection();
      //    This session is not transacted.
      Session senderSession = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
      MessageProducer producer = senderSession.createProducer(destination);
      //    enable message transfer
      con.start();
      //    Create a text message, and send it 100 times over.
      TextMessage textMessage;
      for (int counter = 0; counter < 100; counter++) {
        textMessage = senderSession.createTextMessage("MantaRay test msg #" + counter);
        producer.send(textMessage);
        Thread.sleep(1000);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
