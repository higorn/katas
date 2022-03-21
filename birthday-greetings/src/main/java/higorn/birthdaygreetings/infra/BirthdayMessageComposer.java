package higorn.birthdaygreetings.infra;

import higorn.birthdaygreetings.domain.message.Message;
import higorn.birthdaygreetings.domain.message.MessageComposer;
import higorn.birthdaygreetings.domain.person.Person;

public class BirthdayMessageComposer implements MessageComposer {

  @Override
  public Message getMessage(Person person) {
    return new Message("Happy birthday!", "Happy birthday, dear " +person.firstName + "!");
  }
}
