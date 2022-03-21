package higorn.katas.christmasdelivery;

public class SingleElfSleighLoader {
  private final Elf          elf;
  private final SantasSleigh sleigh;
  private final ToyMachine   toyMachine;

  public SingleElfSleighLoader(Elf elf, SantasSleigh sleigh, ToyMachine toyMachine) {
    this.elf = elf;
    this.sleigh = sleigh;
    this.toyMachine = toyMachine;
  }

  public int load() {
    var start = System.currentTimeMillis();
    while (toyMachine.hasPresent())
      elf.loadSleigh(toyMachine.getPresent(), sleigh);
    var end = System.currentTimeMillis();
    return (int) (end - start);
  }
}
