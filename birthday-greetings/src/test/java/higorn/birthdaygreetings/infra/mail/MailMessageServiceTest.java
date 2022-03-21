package higorn.birthdaygreetings.infra.mail;

import higorn.birthdaygreetings.domain.message.MessageService;
import higorn.birthdaygreetings.domain.person.Person;
import higorn.birthdaygreetings.infra.BirthdayMessageComposer;

import java.time.LocalDate;
import java.util.function.Supplier;

public class MailMessageServiceTest implements Supplier<MailClient> {

//  @Test
  void shouldSendAMail() {
    MessageService messageService = new MailMessageService(this);
    var person = new Person("Nor", "Nica", LocalDate.now(), "higorn@gmail.com");
    var composer = new BirthdayMessageComposer();
    messageService.sendMessage(person, composer.getMessage(person));
  }

  @Override
  public MailClient get() {
    return new MailClient(new MailSessionSupplier());
  }
}
