package higorn.commandline.args;

public interface TokenCollector {
  void dash(int pos);
  void name(String name, int pos);
  void error(String token, int pos);
}
