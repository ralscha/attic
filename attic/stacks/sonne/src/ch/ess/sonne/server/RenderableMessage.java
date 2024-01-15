package ch.ess.sonne.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;

import org.apache.commons.io.IOUtils;

public class RenderableMessage {

  List attachments;

  public RenderableMessage(Message m) throws MessagingException, IOException {
    attachments = new ArrayList();
    extractPart(m);
  }

  private void extractPart(final Part part) throws MessagingException, IOException {
    if (part.getContent() instanceof Multipart) {
      Multipart mp = (Multipart)part.getContent();
      for (int i = 0; i < mp.getCount(); i++) {
        extractPart(mp.getBodyPart(i));
      }
      return;
    }
    
    
    if (part.getContentType() != null) {
      if (part.getContentType().toLowerCase().startsWith("image/jpeg") || 
          part.getContentType().toLowerCase().startsWith("application/octet-stream")) {
        if (part.getFileName() != null && part.getFileName().toLowerCase().endsWith("jpg")) {
          Attachment attachment = new Attachment();
          attachment.setContenttype(part.getContentType());
          attachment.setFilename(part.getFileName());
  
          InputStream in = part.getInputStream();
          ByteArrayOutputStream bos = new ByteArrayOutputStream();
  
          IOUtils.copy(in, bos);
  
          in.close();
          attachment.setContent(bos.toByteArray());
          attachments.add(attachment);
        }
      }
    }
  }

  public int getAttachmentCount() {
    if (attachments == null) {
      return 0;
    }
    return attachments.size();
  }

  public Attachment getAttachment(int i) {
    return (Attachment)attachments.get(i);
  }

}
