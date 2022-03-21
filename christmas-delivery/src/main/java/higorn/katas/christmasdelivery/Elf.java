package higorn.katas.christmasdelivery;

import java.util.function.Consumer;

public class Elf {
  private Consumer<Elf> freeElfConsumer;

  public Elf() {
  }

  public Elf(Consumer<Elf> freeElfConsumer) {
    this.freeElfConsumer = freeElfConsumer;
  }

  public void loadSleigh(Present present, Sleigh sleigh) {
    packPresent();
    sleigh.addPresent(present);
    if (freeElfConsumer != null)
      freeElfConsumer.accept(this);
  }

  private void packPresent() {
    try {
      Thread.sleep(10);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
