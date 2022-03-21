package higorn.katas.booking;

public class Event<T> {
  public final String name;
  public final T data;

  public Event(String name, T data) {
    this.name = name;
    this.data = data;
  }
}
