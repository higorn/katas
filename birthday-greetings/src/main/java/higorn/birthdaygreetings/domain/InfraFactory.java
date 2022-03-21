package higorn.birthdaygreetings.domain;

import higorn.birthdaygreetings.domain.message.MessageComposer;
import higorn.birthdaygreetings.domain.message.MessageService;
import higorn.birthdaygreetings.domain.person.PersonDao;

public interface InfraFactory {
  MessageService getMessageService();
  MessageComposer getMessageComposer();
  PersonDao getPersonDao();
}
