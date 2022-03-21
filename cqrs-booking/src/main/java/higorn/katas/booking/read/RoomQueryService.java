package higorn.katas.booking.read;

import java.time.LocalDate;
import java.util.List;

public class RoomQueryService {
  private final ReadStorage registry;

  public RoomQueryService(ReadStorage registry) {
    this.registry = registry;
  }

  public List<Room> freeRooms(LocalDate checkIn, LocalDate checkOut) {
    return registry.getFreeRooms(checkIn, checkOut);
  }
}
