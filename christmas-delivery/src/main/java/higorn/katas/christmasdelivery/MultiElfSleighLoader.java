package higorn.katas.christmasdelivery;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class MultiElfSleighLoader {
  private final ElfPool         elfPool;
  private final SantasSleigh    sleigh;
  private final ToyMachine      toyMachine;
  private final ExecutorService threadPool;
  private final Set<Integer>    naughtyFamilies;

  public MultiElfSleighLoader(ElfPool elfPool, SantasSleigh sleigh, ToyMachine toyMachine) {
    this(elfPool, sleigh, toyMachine, new HashSet<>());
  }

  public MultiElfSleighLoader(ElfPool elfPool, SantasSleigh sleigh, ToyMachine toyMachine, Set<Integer> naughtyFamilies) {
    this.elfPool = elfPool;
    this.sleigh = sleigh;
    this.toyMachine = toyMachine;
    this.naughtyFamilies = naughtyFamilies;
    threadPool = new ForkJoinPool(elfPool.size());
  }

  public int load() {
    var start = System.currentTimeMillis();
    var numberOfPresent = toyMachine.getNumberOfPresent();
    var elfSize = elfPool.size();
    startSleighLoading();
    awaitLoadingTermination(numberOfPresent, elfSize);
    var end = System.currentTimeMillis();
    return (int) (end - start);
  }

  private void startSleighLoading() {
    while (toyMachine.hasPresent()) {
      var present = toyMachine.getPresent();
      if (naughtyFamilies.contains(present.familyId))
        continue;
      threadPool.submit(() -> elfPool.getElf().loadSleigh(present, sleigh));
    }
  }

  private void awaitLoadingTermination(int numberOfPresent, int elfSize) {
    threadPool.shutdown();
    try {
      var awaitTime = elfSize * numberOfPresent * 100;
      var termination = threadPool.awaitTermination(awaitTime, TimeUnit.MILLISECONDS);
      System.out.println(termination);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
