package higorn.katas.booking;

import higorn.katas.booking.read.ReadStorage;
import higorn.katas.booking.read.Room;
import higorn.katas.booking.read.RoomQueryService;
import higorn.katas.booking.write.Booking;
import higorn.katas.booking.write.BookingCommandService;
import higorn.katas.booking.write.EventStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BookingTest {

  @Nested
  @DisplayName("Given there are three rooms")
  class GivenRooms {
    private List<Room>       rooms;
    private RoomQueryService queryService;
    private BookingCommandService commandService;

    @BeforeEach
    void setUp() {
      rooms = Arrays.asList(new Room("Room A"), new Room("Room B"), new Room("Room C"));
      var eventHandler = new EventHandler<Booking>();
      ReadStorage readStorage = new ReadStorage(rooms, eventHandler);
      EventStore eventStore = new EventStore(eventHandler);
      queryService = new RoomQueryService(readStorage);
      commandService = new BookingCommandService(eventStore);
    }

    @Nested
    @DisplayName("When there are no bookings for the selected dates")
    class WhenNoBookings {

      @Test
      @DisplayName("Then I should see three rooms as available")
      void showFreeRooms() {
        var checkIn = LocalDate.now();
        var checkOut = checkIn.plusDays(5);

        var freeRooms = queryService.freeRooms(checkIn, checkOut);
        assertEquals(rooms.size(), freeRooms.size());
      }
    }

    @Nested
    @DisplayName("When I book a room")
    class whenIBookARoom {
      private final LocalDate checkIn  = LocalDate.now();
      private final LocalDate checkOut = checkIn.plusDays(5);

      @BeforeEach
      void setUp() {
        var booking = new Booking(UUID.randomUUID(), "Room A", checkIn, checkOut);
        commandService.bookRoom(booking);
      }

      @Test
      @DisplayName("Then I should see two rooms as available")
      void showFreeRooms() {
        var freeRooms = queryService.freeRooms(checkIn, checkOut);
        assertEquals(2, freeRooms.size());
      }
    }

    @Nested
    @DisplayName("When I book a room already booked")
    class whenIBookARoomAlreadyBooked {
      private final LocalDate             checkIn  = LocalDate.now();
      private final LocalDate             checkOut = checkIn.plusDays(5);

      @BeforeEach
      void setUp() {
        var booking = new Booking(UUID.randomUUID(), "Room A", checkIn, checkOut);
        commandService.bookRoom(booking);
      }

      @Test
      @DisplayName("Then I should get an error")
      void showFreeRooms() {
        var booking = new Booking(UUID.randomUUID(), "Room A", checkIn, checkOut);
        assertThrows(EventStore.RoomNotAvailableException.class, () -> commandService.bookRoom(booking));
      }
    }

    @Nested
    @DisplayName("When I book the same room in different period")
    class whenIBookARoomInDifferentPeriod {
      private final LocalDate checkIn  = LocalDate.now();
      private final LocalDate checkOut = checkIn.plusDays(5);

      @BeforeEach
      void setUp() {
        var booking1 = new Booking(UUID.randomUUID(), "Room A", checkIn, checkOut);
        commandService.bookRoom(booking1);

        var booking2 = new Booking(UUID.randomUUID(), "Room A", checkIn.plusDays(6), checkOut.plusDays(5));
        commandService.bookRoom(booking2);
      }

      @Test
      @DisplayName("Then I should see two rooms as available")
      void showFreeRooms() {
        var freeRooms = queryService.freeRooms(checkIn, checkOut);
        assertEquals(2, freeRooms.size());
      }
    }

    @Nested
    @DisplayName("When I book a room for a period and query for other period")
    class whenIBookARoomForAPeriodAndQueryForOtherPeriod {
      private final LocalDate checkIn  = LocalDate.now();
      private final LocalDate checkOut = checkIn.plusDays(5);

      @BeforeEach
      void setUp() {
        var booking = new Booking(UUID.randomUUID(), "Room A", checkIn, checkOut);
        commandService.bookRoom(booking);
      }

      @Test
      @DisplayName("Then I should see three rooms as available")
      void showFreeRooms() {
        var freeRooms = queryService.freeRooms(checkIn.plusDays(6), checkOut.plusDays(5));
        assertEquals(3, freeRooms.size());
      }
    }
  }
}
