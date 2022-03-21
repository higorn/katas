package higorn.birthdaygreetings.infra.dao;

import higorn.birthdaygreetings.domain.person.PersonDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonCsvDaoTest implements Supplier<Path> {
  private Path filePath;
  private LocalDate date;

  @BeforeEach
  void setUp() throws IOException {
    date = LocalDate.now();
    var fileContent =
        "last_name, first_name, date_of_birth, email\n"
        + "Doe, John, " + date + ", john.doe@foobar.com\n"
        + "Ann, Mary, " + date + ", mary.ann@foobar.com\n"
        + "Nor, Nica, 1975-09-11, nica.nor@foobar.com";
    filePath = Files.writeString(Files.createTempFile("friends", "txt"), fileContent);
  }

  @Test
  void shouldGetAListOfPerson() throws IOException {
    String[] expected = {
        "Doe, John, " + date + ", john.doe@foobar.com",
        "Ann, Mary, " + date + ", mary.ann@foobar.com",
        "Nor, Nica, 1975-09-11, nica.nor@foobar.com"
    };

    PersonDao dao = new PersonCsvDao(this);
    var person = dao.getAll();

    assertThat(expected).containsExactly(person.stream()
        .map(p -> p.lastName + ", " + p.firstName + ", " + p.birthDate + ", " + p.email)
        .toArray(String[]::new));
  }

  @Test
  void shouldGetAllByDate() throws IOException {
    PersonDao dao = new PersonCsvDao(this);
    var person = dao.getAllByDate(date);
    assertEquals(2, person.size());
  }

  @Override
  public Path get() {
    return filePath;
  }
}
