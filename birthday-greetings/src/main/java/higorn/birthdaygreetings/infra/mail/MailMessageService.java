package higorn.birthdaygreetings.infra.mail;

import higorn.birthdaygreetings.domain.message.Message;
import higorn.birthdaygreetings.domain.message.MessageService;
import higorn.birthdaygreetings.domain.person.Person;

import java.util.function.Supplier;

public class MailMessageService implements MessageService {
  private final MailClient client;

  public MailMessageService(Supplier<MailClient> clientSupplier) {
    client = clientSupplier.get();
  }

  @Override
  public void sendMessage(Person recipient, Message message) {
    client.sendMessage(recipient.email, message.subject, message.body);
  }
}
