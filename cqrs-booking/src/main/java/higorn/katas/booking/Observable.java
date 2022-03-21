package higorn.katas.booking;

import java.util.function.Consumer;

public interface Observable<T> {
  void subscribe(Consumer<Event<T>> consumer);
}
