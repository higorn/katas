package higorn.katas.booking.read;

import higorn.katas.booking.*;
import higorn.katas.booking.write.Booking;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReadStorage {
  private final List<Room>                       rooms;
  private final Map<Booking.DatePeriod, Booking> bookings = new HashMap<>();
  private final EventHandler<Booking>            eventHandler;

  public ReadStorage(List<Room> rooms, EventHandler<Booking> eventHandler) {
    this.rooms = rooms;
    this.eventHandler = eventHandler;
    eventHandler.on(BookingEvent.BOOKING_REQUESTED.name()).subscribe(this::checkRoomAvailability);
  }

  private void checkRoomAvailability(Event<Booking> event) {
    var booking = event.data;
    if (bookings.containsKey(booking.datePeriod))
      eventHandler.emitEvent(new Event<>(BookingEvent.BOOKING_REJECTED.name(), booking));
    else {
      bookings.put(booking.datePeriod, booking);
      eventHandler.emitEvent(new Event<>(BookingEvent.BOOKING_CONFIRMED.name(), booking));
    }
  }

  public List<Room> getFreeRooms(LocalDate checkIn, LocalDate checkOut) {
    return rooms.stream()
        .filter(r -> notBooked(r, new Booking.DatePeriod(checkIn, checkOut)))
        .collect(Collectors.toList());
  }

  private boolean notBooked(Room r, Booking.DatePeriod period) {
    return bookings.values().stream()
        .filter(b -> b.datePeriod.equals(period))
        .noneMatch(b -> b.roomName.equals(r.name));
  }
}
