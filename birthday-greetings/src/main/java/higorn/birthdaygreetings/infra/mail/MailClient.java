package higorn.birthdaygreetings.infra.mail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.function.Supplier;

public class MailClient {
  private final Session session;

  public MailClient(Supplier<Session> sessionSupplier) {
    this.session = sessionSupplier.get();
  }

  public void sendMessage(String recipient, String subject, String msg) {
    try {
      Transport.send(getMimeMessage(recipient, subject, msg));
    } catch (MessagingException e) {
      e.printStackTrace();
      throw new MailException();
    }
  }

  private Message getMimeMessage(String recipient, String subject, String msg) {
    try {
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress("higorn@gmail.com"));
      message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
      message.setSubject(subject);
      message.setContent(getMimeMultipart(msg));
      return message;
    } catch (MessagingException e) {
      e.printStackTrace();
      throw new MailException();
    }
  }

  private MimeMultipart getMimeMultipart(String msg) {
    try {
      var mimeBodyPart = new MimeBodyPart();
      mimeBodyPart.setContent(msg, "text/html; charset=utf-8");
      var multipart = new MimeMultipart();
      multipart.addBodyPart(mimeBodyPart);
      return multipart;
    } catch (MessagingException e) {
      e.printStackTrace();
      throw new MailException();
    }
  }

  public static class MailException extends RuntimeException {
  }
}
