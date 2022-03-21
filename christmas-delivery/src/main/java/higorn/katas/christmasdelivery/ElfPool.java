package higorn.katas.christmasdelivery;

import java.util.*;

public class ElfPool {
  private final Queue<Elf> elves;

  public ElfPool(int numOfElves) {
    elves = new LinkedList<>();

    for (int i = 0; i < numOfElves; i++)
      elves.offer(new Elf(this::returnToPool));
  }

  public synchronized Elf getElf() {
    if (elves.isEmpty()) {
      try {
        System.out.println("#### waiting for available elf...");
        this.wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    return elves.poll();
  }

  private synchronized void returnToPool(Elf elf) {
    elves.offer(elf);
    this.notify();
  }

  public int size() {
    return elves.size();
  }
}
