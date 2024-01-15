package ch.ess.sonne.server;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import org.apache.commons.io.IOUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

public class FetchImageJob implements Job {

  public void execute(JobExecutionContext ctx) {
    
    try {
      Properties props = System.getProperties();
      Session session = Session.getInstance(props, null);
      Store store = session.getStore("pop3");
      store.connect("192.168.20.199", "pic", "gruenerdaumen99");

      Folder folder = store.getFolder("Inbox");

      if (folder == null || !folder.exists()) {
        System.out.println("Invalid folder");
        System.exit(1);
      }

      folder.open(Folder.READ_WRITE);
      Message[] msgs = folder.getMessages();

      FetchProfile fp = new FetchProfile();
      fp.add(FetchProfile.Item.CONTENT_INFO);
      fp.add("Subject");
      folder.fetch(msgs, fp);

      for (int m = 0; m < msgs.length; m++) {

        if (msgs[m].getSubject() == null || msgs[m].getSubject().trim().equals("")) {
          if (msgs[m].getContent() instanceof Multipart) {

            RenderableMessage rm = new RenderableMessage(msgs[m]);

            for (int i = 0; i < rm.getAttachmentCount(); i++) {
              Attachment at = rm.getAttachment(i);
              
              File imageDirectory = new File(ApplicationConextListener.IMAGE_DIRECTORY);
              File imgFile = new File(imageDirectory, System.currentTimeMillis() + "_" + at.getFilename());
              
              if (!imgFile.exists()) {              
                FileOutputStream fos = new FileOutputStream(imgFile);
                IOUtils.copy(new ByteArrayInputStream(at.getContent()), fos);
                fos.close();
              }
              
              msgs[m].setFlag(Flags.Flag.DELETED, true);

            }
          }
        }
      }

      folder.close(true);
      store.close();

    } catch (NoSuchProviderException e) {
      e.printStackTrace();
    } catch (MessagingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } 

  }

}
