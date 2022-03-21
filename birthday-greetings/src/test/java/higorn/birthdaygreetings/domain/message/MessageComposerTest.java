package higorn.birthdaygreetings.domain.message;

import higorn.birthdaygreetings.domain.person.Person;
import higorn.birthdaygreetings.infra.BirthdayMessageComposer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageComposerTest {
  private String firstName;

  @ParameterizedTest
  @MethodSource("getTestCases")
  void givenAFriendsFirstName_thenComposeABirthdayMessage(String firstName, Message expectedMessage) {
    this.firstName = firstName;
    var person = new Person("Doe", firstName, LocalDate.now(), "john.doe@foobar.com");
    var messageComposer = new BirthdayMessageComposer();
    assertEquals(expectedMessage.toString(), messageComposer.getMessage(person).toString());
  }

  private static Stream<Arguments> getTestCases() {
    return Stream.of(
        Arguments.of("John", new Message("Happy birthday!", "Happy birthday, dear John!")),
        Arguments.of("Mary", new Message("Happy birthday!", "Happy birthday, dear Mary!"))
    );
  }
}
