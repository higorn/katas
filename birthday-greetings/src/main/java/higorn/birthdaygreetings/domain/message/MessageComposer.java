package higorn.birthdaygreetings.domain.message;

import higorn.birthdaygreetings.domain.person.Person;

public interface MessageComposer {
  Message getMessage(Person person);
}
