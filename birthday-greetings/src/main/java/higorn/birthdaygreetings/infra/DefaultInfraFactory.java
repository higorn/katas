package higorn.birthdaygreetings.infra;

import higorn.birthdaygreetings.domain.InfraFactory;
import higorn.birthdaygreetings.domain.message.MessageComposer;
import higorn.birthdaygreetings.domain.message.MessageService;
import higorn.birthdaygreetings.domain.person.PersonDao;
import higorn.birthdaygreetings.infra.dao.CsvFilePathSupplier;
import higorn.birthdaygreetings.infra.dao.PersonCsvDao;
import higorn.birthdaygreetings.infra.mail.MailClientSupplier;
import higorn.birthdaygreetings.infra.mail.MailMessageService;
import higorn.birthdaygreetings.infra.mail.MailSessionSupplier;

public class DefaultInfraFactory implements InfraFactory {
  @Override
  public MessageService getMessageService() {
    return new MailMessageService(new MailClientSupplier(new MailSessionSupplier()));
  }

  @Override
  public MessageComposer getMessageComposer() {
    return new BirthdayMessageComposer();
  }

  @Override
  public PersonDao getPersonDao() {
    return new PersonCsvDao(new CsvFilePathSupplier());
  }
}
