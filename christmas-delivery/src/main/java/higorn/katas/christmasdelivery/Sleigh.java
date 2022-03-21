package higorn.katas.christmasdelivery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Sleigh {
  public final Map<Integer, List<Present>> presents;

  public Sleigh() {
    this.presents = new ConcurrentHashMap<>();
  }

  public synchronized void addPresent(Present present) {
    if (presents.containsKey(present.familyId)) {
      presents.get(present.familyId).add(present);
      return;
    }
    var presetList = new ArrayList<Present>();
    presetList.add(present);
    presents.put(present.familyId, presetList);
  }

  public int size() {
    return presents.values().stream().map(List::size).reduce(Integer::sum).orElse(0);
  }
}
