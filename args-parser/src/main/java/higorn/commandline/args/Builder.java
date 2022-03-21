package higorn.commandline.args;

public interface Builder {
  void setName(String name);
  void addOptionWithName();
  void setOptionValue();
  void addArgument();
  void done();
  void syntaxError(String token, int pos);
  void optionError(ParserState state, ParserEvent event, int pos);
}
