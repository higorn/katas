package higorn.katas.booking.write;

public class BookingCommandService {
  private final EventStore registry;

  public BookingCommandService(EventStore registry) {
    this.registry = registry;
  }

  public void bookRoom(Booking booking) {
    registry.save(booking);
  }
}
