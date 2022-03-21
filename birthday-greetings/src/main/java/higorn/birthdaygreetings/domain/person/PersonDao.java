package higorn.birthdaygreetings.domain.person;

import java.time.LocalDate;
import java.util.List;

public interface PersonDao {
  List<Person> getAll();
  List<Person> getAllByDate(LocalDate date);

  public static class InstantiationException extends RuntimeException {
  }
}
