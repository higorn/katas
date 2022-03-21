package higorn.commandline.args;

import java.util.*;

public class ArgsSyntax {
  public Map<String, Option> opts = new HashMap<>();
  public Set<Argument>       args = new HashSet<>();
  public List<SyntaxError>   errors = new ArrayList<>();
  public boolean             done = false;


  public static class Option {
    public String name;
    public String value;

    public Option() {
    }

    public Option(String name, String value) {
      this.name = name;
      this.value = value;
    }
  }

  public static class Argument {
    public String name;

    public Argument(String name) {
      this.name = name;
    }

    @Override
    public int hashCode() {
      return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
      if (!(obj instanceof Argument))
        return false;
      Argument other = (Argument) obj;
      return name.equals(other.name);
    }
  }

  public static class SyntaxError {
    public enum Type {SYNTAX, OPTION}

    final Type type;
    final String msg;
    final int position;

    public SyntaxError(Type type, String msg, int position) {
      this.type = type;
      this.msg = msg;
      this.position = position;
    }
  }
}
