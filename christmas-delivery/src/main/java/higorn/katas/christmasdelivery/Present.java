package higorn.katas.christmasdelivery;

public class Present {
  public final int id;
  public final Integer familyId;

  public Present(int id) {
    this(id, 0);
  }

  public Present(int id, int familyId) {
    this.id = id;
    this.familyId = familyId;
  }
}
