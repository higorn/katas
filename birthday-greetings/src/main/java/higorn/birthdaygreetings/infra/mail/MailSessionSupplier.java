package higorn.birthdaygreetings.infra.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;
import java.util.function.Supplier;

public class MailSessionSupplier implements Supplier<Session> {
  private final Properties props;

  public MailSessionSupplier() {
    props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");
  }

  @Override
  public Session get() {
    return Session.getDefaultInstance(props, new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication("higorn@gmail.com", "oapbcxepwfsmptyb");
      }
    });
  }
}
