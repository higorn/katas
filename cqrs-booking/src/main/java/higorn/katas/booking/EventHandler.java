package higorn.katas.booking;

import java.util.*;
import java.util.function.Consumer;

import static java.util.Optional.ofNullable;

public class EventHandler<T> {
  private final Map<String, List<Consumer<Event<T>>>> subscribers = new HashMap<>();

  public void emitEvent(Event<T> event) {
    ofNullable(subscribers.get(event.name)).ifPresent(list -> list.forEach(c -> c.accept(event)));
  }

  public Observable<T> on(String eventName) {
    return consumer -> {
      if (subscribers.containsKey(eventName))
        subscribers.get(eventName).add(consumer);
      else {
        var consumerList = new ArrayList<Consumer<Event<T>>>();
        consumerList.add(consumer);
        subscribers.put(eventName, consumerList);
      }
    };
  }
}
