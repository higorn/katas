package higorn.commandline.args;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LexerTest implements TokenCollector {
  private String tokens = "";
  private boolean isFristToken = true;
  private Lexer lexer;

  @BeforeEach
  void setUp() {
    lexer = new Lexer(this);
  }

  private void addToken(String token) {
    if (!isFristToken)
      tokens += ",";
    tokens += token;
    isFristToken = false;
  }

  private void assertLexResult(String input, String expected) {
    lexer.lex(input);
    assertEquals(expected, tokens);
  }

  @Override
  public void dash(int pos) {
    addToken("D");
  }

  @Override
  public void name(String name, int pos) {
    addToken("#" + name + "#");
  }

  @Override
  public void error(String token, int pos) {
    addToken("E/" + pos);
  }

  @Nested
  public class SingleTokenTests {
    @Test
    void findDash() {
      assertLexResult("-", "D");
    }
    @Test
    void findDashWithLeadingSpaces() {
      assertLexResult(" \t\n  -", "D");
    }
    @Test
    void findSimpleName() {
      assertLexResult("name", "#name#");
    }
    @Test
    void findComplexName() {
      assertLexResult("Abcde_342", "#Abcde_342#");
    }
    @Test
    void findComplexNameWithSpaces() {
      assertLexResult("  \t\n  Abcde_342  ", "#Abcde_342#");
    }
    @Test
    void error() {
      assertLexResult(".", "E/1");
    }
    @Test
    void nothingButSpaces() {
      assertLexResult("  ", "");
    }
  }

  @Nested
  public class MultipleTokenTests {
    @Test
    void simpleSequence() {
      assertLexResult("-p 8080", "D,#p#,#8080#");
    }
    @Test
    void complexSequence() {
      assertLexResult("-l -p 8080 -a -b", "D,#l#,D,#p#,#8080#,D,#a#,D,#b#");
    }
  }
}
