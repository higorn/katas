package higorn.birthdaygreetings.application;

import higorn.birthdaygreetings.domain.message.Message;
import higorn.birthdaygreetings.domain.message.MessageService;
import higorn.birthdaygreetings.domain.person.Person;
import higorn.birthdaygreetings.infra.BirthdayMessageComposer;
import higorn.birthdaygreetings.infra.dao.PersonCsvDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

public class BirthdayNotificatorTest implements MessageService, Supplier<Path> {

  private final List<String> messagesSent = new ArrayList<>();
  private       Path         filePath;

  @BeforeEach
  void setUp() throws IOException {
    var date = LocalDate.now();
    var fileContent =
        "last_name, first_name, date_of_birth, email\n"
            + "Doe, John, " + date + ", john.doe@foobar.com\n"
            + "Ann, Mary, " + date + ", mary.ann@foobar.com\n"
            + "Nor, Nica, 1975-09-11, nica.nor@foobar.com";
    filePath = Files.writeString(Files.createTempFile("friends", "txt"), fileContent);
  }

  @Test
  void givenAListOfFriends_thenSendABirthdayNotificationForEachOne() throws IOException {
    String[] expected = {
        "Recipient: john.doe@foobar.com\nSubject: Happy birthday!\n\nHappy birthday, dear John!",
        "Recipient: mary.ann@foobar.com\nSubject: Happy birthday!\n\nHappy birthday, dear Mary!",
    };
    var dao = new PersonCsvDao(this);

    var notificator = new BirthdayNotificator(this, new BirthdayMessageComposer(), dao);
    notificator.run();

    assertThat(expected).containsExactly(messagesSent.toArray(String[]::new));
  }

  @Override
  public void sendMessage(Person recipient, Message message) {
    messagesSent.add("Recipient: " + recipient.email + "\n" + message.toString());
  }

  @Override
  public Path get() {
    return filePath;
  }
}
