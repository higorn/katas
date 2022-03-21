package higorn.commandline.args;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
  private final TokenCollector collector;
  private int position;

  public Lexer(TokenCollector collector) {
    this.collector = collector;
  }

  public void lex(String input) {
    for (position = 0; position < input.length();)
      lexToken(input);
  }

  private void lexToken(String token) {
    if (!findToken(token)) {
      collector.error(token, position + 1);
      position++;
    }
  }

  private boolean findToken(String line) {
    return findWhiteSpace(line) || findSingleCharToken(line) || findName(line);
  }

  private boolean findWhiteSpace(String line) {
    Matcher matcher = Pattern.compile("^\\s").matcher(line.substring(position));
    if (matcher.find()) {
      position += matcher.end();
      return true;
    }
    return false;
  }

  private boolean findSingleCharToken(String line) {
    String c = line.substring(position, position + 1);
    if ("-".equals(c)) {
      collector.dash(position);
    } else {
      return false;
    }
    position++;
    return true;
  }

  private boolean findName(String line) {
    Matcher matcher = Pattern.compile("^\\w+").matcher(line.substring(position));
    if (matcher.find()) {
      collector.name(matcher.group(0), position);
      position += matcher.end();
      return true;
    }
    return false;
  }
}
