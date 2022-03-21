package higorn.birthdaygreetings.domain.person;

import java.time.LocalDate;

public class Person {
  public String lastName;
  public String firstName;
  public LocalDate birthDate;
  public String email;

  public Person(String lastName, String firstName, LocalDate birthDate, String email) {
    this.lastName = lastName;
    this.firstName = firstName;
    this.birthDate = birthDate;
    this.email = email;
  }
}
