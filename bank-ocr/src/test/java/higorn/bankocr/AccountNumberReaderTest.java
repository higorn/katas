package higorn.bankocr;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountNumberReaderTest {

  @Test
  void shouldReadAccountNumbersFromFile() throws IOException {
    var numbers =
        " _     _  _     _  _  _  _ \n" +
        "| |  | _| _||_||_ |_   ||_|\n" +
        "|_|  ||_  _|  | _||_|  ||_|\n" +
        "\n" +
        "    _  _     _  _  _  _  _ \n" +
        "  | _| _||_||_ |_   ||_||_|\n" +
        "  ||_  _|  | _||_|  ||_| _|\n" +
        "\n" +
        "    _  _  _  _  _  _  _  _ \n" +
        "|_||_   ||_ | ||_|| || || |\n" +
        "  | _|  | _||_||_||_||_||_|\n" +
        "\n";
    var expected = new String[] {
        "012345678",
        "123456789",
        "457508000"
    };

    var filePath = Files.writeString(Files.createTempFile("numbers", "txt"), numbers);
    var accountNumberReader = new AccountNumberReader(filePath);

    assertThat(expected).containsExactly(accountNumberReader.getAccountNumbers()
        .map(AccountNumber::toString).toArray(String[]::new));
  }
}
