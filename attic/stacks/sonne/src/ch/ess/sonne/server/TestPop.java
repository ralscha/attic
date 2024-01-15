package ch.ess.sonne.server;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifDirectory;

public class TestPop {

  public static void main(String[] args) {
    
    DateFormat df = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
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
      System.out.println("FOUND " + msgs.length + " MESSAGES");

      FetchProfile fp = new FetchProfile();
      fp.add(FetchProfile.Item.CONTENT_INFO);
      fp.add("Subject");
      folder.fetch(msgs, fp);

      for (int m = 0; m < msgs.length; m++) {

        if (msgs[m].getSubject() == null || msgs[m].getSubject().trim().equals("")) {
          if (msgs[m].getContent() instanceof Multipart) {

            RenderableMessage rm = new RenderableMessage(msgs[m]);

            System.out.println(rm.getAttachmentCount() + " attachments");
            for (int i = 0; i < rm.getAttachmentCount(); i++) {
              Attachment at = rm.getAttachment(i);
              
              File f = new File("c:/temp/"+at.getFilename());
              if (f.exists()) {
                f.delete();
              }
              
              FileOutputStream fos = new FileOutputStream(f);
              IOUtils.copy(new ByteArrayInputStream(at.getContent()), fos);
              fos.close();

              Metadata metadata = JpegMetadataReader.readMetadata(f);
              Directory exifDirectory = metadata.getDirectory(ExifDirectory.class);
              String cameraMake = exifDirectory.getString(ExifDirectory.TAG_DATETIME);
              try {
                Date d = df.parse(cameraMake);
                System.out.println(d);
              } catch (ParseException e) {                
                e.printStackTrace();
              }
              
              
            }
            
            msgs[m].setFlag(Flags.Flag.DELETED, true);
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
    } catch (JpegProcessingException e) {      
      e.printStackTrace();
    }

  }

}
