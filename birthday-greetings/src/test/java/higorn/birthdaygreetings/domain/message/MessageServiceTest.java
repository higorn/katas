package higorn.birthdaygreetings.domain.message;

import higorn.birthdaygreetings.domain.person.Person;
import higorn.birthdaygreetings.infra.BirthdayMessageComposer;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageServiceTest implements MessageService {
  private String messageSent;

  @Test
  void shouldSendABirthdayMessage() {
    MessageService messageService = this;
    var messageComposer = new BirthdayMessageComposer();
    var recipient = new Person("Doe", "John", LocalDate.now(), "john.doe@foobar.com");
    var expectedMessage = "Recipient: " + recipient.email + "\nSubject: Happy birthday!\n\nHappy birthday, dear John!";
    messageService.sendMessage(recipient, messageComposer.getMessage(recipient));
    assertEquals(expectedMessage, messageSent);
  }

  @Override
  public void sendMessage(Person recipient, Message message) {
    messageSent = "Recipient: " + recipient.email + "\n" + message.toString();
  }
}
