package ch.ess.common.mail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import EDU.oswego.cs.dl.util.concurrent.Channel;
import EDU.oswego.cs.dl.util.concurrent.LinkedQueue;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.07.2003 
  */
public class MailQueue {

  private static Log LOG = LogFactory.getLog(MailQueue.class);

  private static MailQueue instance = new MailQueue();
  private Channel mailQueue;
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
    instance.mailQueue = new LinkedQueue();
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

  public static void put(MailMessage mm) throws MessagingException, InterruptedException {

    if (instance.config.isSendMail()) {
      mm.setTimeout(instance.config.getErrorMessageWaitIntervalInSec());
      mm.setMaxTries(instance.config.getMaxTries());

      if (instance.config.getTest() != null) {
        mm.setTest(instance.config.getTest());
      }

      putInternal(mm);
    }
  }

  public static MailQueue getInstance() {
    return instance;
  }

  private static void putInternal(MailMessage mm) throws MessagingException, InterruptedException {

    mm.checkMessage();
    mm.incTries();

    instance.mailQueue.put(mm);

  }

  MailMessage dequeue() throws InterruptedException {
    return (MailMessage)mailQueue.take();
  }

  public boolean isEmpty() {
    return (mailQueue.peek() == null);
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

  List copyQueue() {

    List tmpQueue = new ArrayList();

    synchronized (errorQueue) {
      Iterator it = errorQueue.iterator(); // Must be in synchronized block

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

          //try again
          try {
            removeFromErrorQueue(mm);
            putInternal(mm);
          } catch (MessagingException me) {
            LOG.error("try again", me);
          } catch (InterruptedException e) {
            LOG.error("try again", e);
          }
        }
      } else {

        //mail could not be delivered
        LOG.error(mm);
        removeFromErrorQueue(mm);
      }
    }
  }
}
