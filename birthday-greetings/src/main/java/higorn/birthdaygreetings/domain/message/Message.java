package higorn.birthdaygreetings.domain.message;

public class Message {
  public final String subject;
  public final String body;

  public Message(String subject, String body) {
    this.subject = subject;
    this.body = body;
  }

  @Override
  public String toString() {
    return "Subject: " + subject + "\n\n" + body;
  }
}
