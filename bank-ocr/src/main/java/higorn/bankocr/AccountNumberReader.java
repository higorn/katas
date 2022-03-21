package higorn.bankocr;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AccountNumberReader {
  private final static int CHUNK_SIZE = 4;
  private final Collection<AccountNumber> accountNumbers;

  public static void main(String... args) throws IOException {
    var accountNumberReader = new AccountNumberReader(Path.of(args[0]));
    accountNumberReader.getAccountNumbers()
        .map(AccountNumber::toString)
        .forEach(System.out::println);
  }

  public AccountNumberReader(Path filePath) throws IOException {
    accountNumbers = parseNumbers(filePath);
  }

  private Collection<AccountNumber> parseNumbers(Path filePath) throws IOException {
    var fileContent = Files.readString(filePath);
    return getChunks(fileContent)
        .map(this::getAccountNumber)
        .collect(Collectors.toList());
  }

  private Stream<List<String>> getChunks(String fileContent) {
    final var counter = new AtomicInteger();
    return fileContent.lines()
        .collect(Collectors.groupingBy(line -> counter.getAndIncrement() / CHUNK_SIZE))
        .values().stream();
  }

  private AccountNumber getAccountNumber(List<String> chunk) {
    return new AccountNumber(chunk.stream().limit(3).toArray(String[]::new));
  }

  public Stream<AccountNumber> getAccountNumbers() {
    return accountNumbers.stream();
  }
}
