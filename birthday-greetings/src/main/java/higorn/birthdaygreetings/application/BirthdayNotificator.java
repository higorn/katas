package higorn.birthdaygreetings.application;

import higorn.birthdaygreetings.domain.InfraFactory;
import higorn.birthdaygreetings.domain.message.MessageComposer;
import higorn.birthdaygreetings.domain.message.MessageService;
import higorn.birthdaygreetings.domain.person.Person;
import higorn.birthdaygreetings.domain.person.PersonDao;

import java.time.LocalDate;

public class BirthdayNotificator {
  private final MessageService  messageService;
  private final MessageComposer composer;
  private final PersonDao       dao;

  public static void main(String... args) {
    var factory = DependencyResolver.getInstanceOf(InfraFactory.class);
    var notificator = new BirthdayNotificator(factory.getMessageService(), factory.getMessageComposer(),
        factory.getPersonDao());
    notificator.run();
  }

  public BirthdayNotificator(MessageService messageService, MessageComposer composer, PersonDao dao) {
    this.messageService = messageService;
    this.composer = composer;
    this.dao = dao;
  }

  public void run() {
    dao.getAllByDate(LocalDate.now())
        .forEach(this::sendMessage);
  }

  private void sendMessage(Person friend) {
    messageService.sendMessage(friend, composer.getMessage(friend));
  }
}
