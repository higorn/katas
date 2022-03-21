package higorn.birthdaygreetings.domain.message;

import higorn.birthdaygreetings.domain.person.Person;

public interface MessageService {
  void sendMessage(Person recipient, Message message);
}
