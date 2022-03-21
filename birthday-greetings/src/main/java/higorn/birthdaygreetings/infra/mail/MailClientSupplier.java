package higorn.birthdaygreetings.infra.mail;

import javax.mail.Session;
import java.util.function.Supplier;

public class MailClientSupplier implements Supplier<MailClient> {
  private final Supplier<Session> mailSessionSupplier;

  public MailClientSupplier(Supplier<Session> mailSessionSupplier) {
    this.mailSessionSupplier = mailSessionSupplier;
  }

  @Override
  public MailClient get() {
    return new MailClient(mailSessionSupplier);
  }
}
