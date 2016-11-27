package @packageProject@.service;

import java.util.Date;
import java.util.List;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.granite.tide.annotations.TideEnabled;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;
import @packageProject@.entity.User;

@Name("newPasswordCreator")
@TideEnabled
public class NewPasswordCreator {

  @In
  private EntityManager entityManager;

  @In
  private Session mailSession;

  @SuppressWarnings("unchecked")
  @Transactional
  public void requestNewPassword(String userName) throws AddressException, MessagingException {
    List<User> users = entityManager.createQuery("select u from User u where u.userName = :userName").setParameter("userName", userName)
        .getResultList();

    if (!users.isEmpty()) {
      String newPassword = RandomStringUtils.randomAlphanumeric(10);
      users.get(0).setPasswordHash(DigestUtils.shaHex(newPassword));
      entityManager.flush();
      Message msg = new MimeMessage(mailSession);

      msg.setFrom(new InternetAddress("test@test.ch"));
      msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(users.get(0).getEmail(), false));

      msg.setSubject("New Password");
      msg.setText("Your new password is: " + newPassword);
      msg.setSentDate(new Date());

      Transport.send(msg);

    }
  }

}
