package higorn.birthdaygreetings.infra.dao;

import java.nio.file.Path;
import java.util.function.Supplier;

public class CsvFilePathSupplier implements Supplier<Path> {
  @Override
  public Path get() {
    return Path.of("/fontes/codding-katas/birthday-greetings/friends.txt");
  }
}
