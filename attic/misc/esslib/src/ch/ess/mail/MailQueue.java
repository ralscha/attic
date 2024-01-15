

package ch.ess.mail;

import java.util.*;

import javax.mail.*;

import org.apache.commons.logging.*;

import com.holub.asynch.*;



public class MailQueue {

  private static Log LOG = LogFactory.getLog(MailQueue.class);
  
  private static MailQueue instance = new MailQueue();
  private Blocking_queue mailQueue;
  private List errorQueue;
  private Thread sendThread;
  private Thread checkErrorQueueThread;
  private MailConfiguration config;

  private MailQueue() {
  }

  public static MailConfiguration getConfig() {
    return instance.config;
  }

  public static void init(MailConfiguration config) {

    instance.config = config;
    instance.mailQueue = new Blocking_queue();
    instance.errorQueue = Collections.synchronizedList(new ArrayList());
    instance.sendThread = new Thread(new MailSendThread(instance, config), "MailSendThread");

    instance.sendThread.setDaemon(true);
    instance.sendThread.start();

    instance.checkErrorQueueThread = new Thread(new CheckErrorQueueThread(instance, config), "CheckErrorQueueThread");

    instance.checkErrorQueueThread.setDaemon(true);
    instance.checkErrorQueueThread.start();
    
  }
  
  public static void reinit(MailConfiguration config) {
    instance.config = config;
    
    instance.sendThread.interrupt();
    instance.sendThread = new Thread(new MailSendThread(instance, config), "MailSendThread");

    instance.sendThread.setDaemon(true);
    instance.sendThread.start();

    instance.checkErrorQueueThread.interrupt();
    instance.checkErrorQueueThread = new Thread(new CheckErrorQueueThread(instance, config), "CheckErrorQueueThread");

    instance.checkErrorQueueThread.setDaemon(true);
    instance.checkErrorQueueThread.start();    
    
  }

  public static void put(MailMessage mm) throws MessagingException {

    if (instance.config.isSendMail()) {
      mm.setTimeout(instance.config.getErrorMessageWaitIntervalInSec());
      mm.setMaxTries(instance.config.getMaxTries());

      if (instance.config.getTest() != null) {
        mm.setTest(instance.config.getTest());
      }

      _put(mm);
    }
  }

  public static MailQueue getInstance() {
    return instance;
  }

  private static void _put(MailMessage mm) throws MessagingException {

    mm.checkMessage();
    mm.incTries();
    instance.mailQueue.enqueue(mm);
  }

  MailMessage dequeue() throws InterruptedException {
    return (MailMessage)mailQueue.dequeue();
  }

  public boolean isEmpty() {

    synchronized (mailQueue) {
      return mailQueue.is_empty();
    }
  }

  //ERROR QUEUE
  void addToErrorQueue(MailMessage mm) {
    mm.setNextTry();
    errorQueue.add(mm);
  }

  void removeFromErrorQueue(MailMessage mm) {
    errorQueue.remove(mm);
  }

  public int getErrorQueueSize() {
    return errorQueue.size();
  }

/*
  public static List getQueueList() {
    return instance.copyQueue();
  }
*/

  List copyQueue() {

    List tmpQueue = new ArrayList();

    synchronized (errorQueue) {
      Iterator it = errorQueue.iterator();    // Must be in synchronized block

      while (it.hasNext()) {
        tmpQueue.add(it.next());
      }
    }

    return tmpQueue;
  }

  void checkErrorQueue() {

    LOG.debug("checking error queue");

    List tmpQueue = copyQueue();
    long now = System.currentTimeMillis();

    for (int i = 0; i < tmpQueue.size(); i++) {
      MailMessage mm = (MailMessage)tmpQueue.get(i);
      long nextTry = mm.getNextTry();

      if (nextTry > 0) {
        if (now >= nextTry) {

          //nochmals versuchen
          try {
            removeFromErrorQueue(mm);
            _put(mm);
          } catch (MessagingException me) {
            LOG.error("try again", me);
          }
        }
      } else {

        //mail konnte trotz mehrfachen Versuchen nicht zugestellt werden
        LOG.error(mm);
        removeFromErrorQueue(mm);
      }
    }
  }
}
