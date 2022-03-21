package higorn.birthdaygreetings.infra.dao;

import higorn.birthdaygreetings.domain.person.Person;
import higorn.birthdaygreetings.domain.person.PersonDao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class PersonCsvDao implements PersonDao {
  private final List<Person> personList;

  public PersonCsvDao(Supplier<Path> filePathSupplier) {
    try {
      personList = Files.readAllLines(filePathSupplier.get()).stream()
          .skip(1)
          .map(this::getPerson)
          .collect(Collectors.toList());
    } catch (IOException e) {
      e.printStackTrace();
      throw new PersonDao.InstantiationException();
    }
  }

  private Person getPerson(String line) {
    var words = line.split(",");
    return new Person(words[0].trim(), words[1].trim(), getDate(words[2].trim()), words[3].trim());
  }

  private LocalDate getDate(String dateStr) {
    var dateParts = dateStr.split("-");
    var year = Integer.parseInt(dateParts[0]);
    var month = Integer.parseInt(dateParts[1]);
    var day = Integer.parseInt(dateParts[2]);
    return LocalDate.of(year, month, day);
  }

  @Override
  public List<Person> getAll() {
    return personList;
  }

  @Override
  public List<Person> getAllByDate(LocalDate date) {
    return personList.stream().filter(p -> p.birthDate.equals(date)).collect(Collectors.toList());
  }
}
