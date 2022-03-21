package higorn.birthdaygreetings.infra.mail;

import org.junit.jupiter.api.BeforeEach;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;
import java.util.function.Supplier;

public class MailClientTest implements Supplier<Session> {

  private Properties props;

  @BeforeEach
  void setUp() {
    props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");
  }

//  @Test
  void shouldSendAMail() {
    var mailClient = new MailClient(this);
    mailClient.sendMessage("higorn@gmail.com", "Teste", "coco");
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
