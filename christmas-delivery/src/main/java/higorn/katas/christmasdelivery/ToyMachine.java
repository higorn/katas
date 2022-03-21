package higorn.katas.christmasdelivery;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ToyMachine {
  private final Queue<Present> presents = new LinkedList<>();

  public ToyMachine(int numberOfPresents) {
    buildToys(numberOfPresents);
  }

  public ToyMachine(List<Present> presents) {
    presents.forEach(this.presents::offer);
  }

  private void buildToys(int numberOfToys) {
    for (int i = 0; i < numberOfToys; i++)
      presents.offer(new Present(i));
  }

  public Present getPresent() {
    return presents.poll();
  }

  public boolean hasPresent() {
    return !presents.isEmpty();
  }

  public int getNumberOfPresent() {
    return presents.size();
  }
}
