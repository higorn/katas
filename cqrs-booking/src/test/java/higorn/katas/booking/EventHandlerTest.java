package higorn.katas.booking;

import higorn.katas.booking.write.Booking;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventHandlerTest {

  private Event<Booking> currentEvent;

  @Test
  void whenABookingIsRequested_thenNotifySubscribers() {
    var checkIn  = LocalDate.now();
    var checkOut = checkIn.plusDays(5);
    var expectedBooking = new Booking(UUID.randomUUID(), "Room A", checkIn, checkOut);
    var expectedEvent = new Event<>(BookingEvent.BOOKING_REQUESTED.name(), expectedBooking);

    var eventHandler = new EventHandler<Booking>();
    eventHandler.on(expectedEvent.name).subscribe(this::assertEvent);
    eventHandler.emitEvent(expectedEvent);

    assertEquals(expectedEvent.name, currentEvent.name);
  }

  private void assertEvent(Event<Booking> event) {
    currentEvent = event;
  }
}
