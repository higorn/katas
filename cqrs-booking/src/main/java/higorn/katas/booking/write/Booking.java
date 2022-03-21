package higorn.katas.booking.write;

import java.time.LocalDate;
import java.util.UUID;

public class Booking {
  public final UUID      id;
  public final UUID      clientId;
  public final String     roomName;
  public final DatePeriod datePeriod;

  public Booking(UUID clientId, String roomName, LocalDate checkIn, LocalDate checkOut) {
    id = UUID.randomUUID();
    this.clientId = clientId;
    this.roomName = roomName;
    datePeriod = new DatePeriod(checkIn, checkOut);
  }

  public static class DatePeriod {
    public final LocalDate checkIn;
    public final LocalDate checkOut;

    public DatePeriod(LocalDate checkIn, LocalDate checkOut) {
      this.checkIn = checkIn;
      this.checkOut = checkOut;
    }

    @Override
    public boolean equals(Object obj) {
      if (!(obj instanceof DatePeriod))
        return false;

      var other = (DatePeriod)obj;
      return checkIn.equals(other.checkIn) && checkOut.equals(other.checkOut);
    }

    @Override
    public int hashCode() {
      return checkIn.hashCode() + checkOut.hashCode();
    }
  }
}
