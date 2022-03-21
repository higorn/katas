package higorn.katas.booking.write;

import higorn.katas.booking.BookingEvent;
import higorn.katas.booking.Event;
import higorn.katas.booking.EventHandler;

import java.util.*;

public class EventStore {
  private final EventHandler<Booking> eventHandler;
  private final List<Event<Booking>>  events = new ArrayList<>();

  public EventStore(EventHandler<Booking> eventHandler) {
    this.eventHandler = eventHandler;
    eventHandler.on(BookingEvent.BOOKING_REJECTED.name()).subscribe(this::cancelBooking);
    eventHandler.on(BookingEvent.BOOKING_CONFIRMED.name()).subscribe(this::confirmBooking);
  }

  private void cancelBooking(Event<Booking> event) {
    events.add(event);
    throw new RoomNotAvailableException();
  }

  private void confirmBooking(Event<Booking> event) {
    events.add(event);
  }

  public void save(Booking booking) {
    var event = new Event<>(BookingEvent.BOOKING_REQUESTED.name(), booking);
    events.add(event);
    eventHandler.emitEvent(event);
  }

  public static class RoomNotAvailableException extends RuntimeException {
  }
}
