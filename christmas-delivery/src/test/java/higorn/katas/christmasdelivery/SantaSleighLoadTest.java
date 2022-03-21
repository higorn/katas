package higorn.katas.christmasdelivery;

import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SantaSleighLoadTest {

  @Test
  void givenASingleElfSleighLoader_thenTheTimeToLoadTheSleighShouldBeLinear() {
    int numberOfToys = 4;
    var sleigh = new Sleigh();
    var toyMachine = new ToyMachine(numberOfToys);

    var sleighLoader = new SingleElfSleighLoader(new Elf(), sleigh, toyMachine);
    var time = sleighLoader.load();

    assertEquals(numberOfToys, sleigh.size());
    assertThat(numberOfToys * 10).isCloseTo(time, Percentage.withPercentage(3.0));
  }

  @Test
  void givenAMultiElfSleighLoader_thenTheTimeToLoadTheSleighShouldBeProportinalToTheNumberOfElves() {
    int numberOfToys = 8;
    int numOfElves = 4;
    var elfPool = new ElfPool(numOfElves);
    var sleigh = new Sleigh();
    var toyMachine = new ToyMachine(numberOfToys);

    var sleighLoader = new MultiElfSleighLoader(elfPool, sleigh, toyMachine);
    var time = sleighLoader.load();

    assertEquals(numberOfToys, sleigh.size());
    assertThat((numberOfToys / numOfElves) * 10).isCloseTo(time, Percentage.withPercentage(40.0));
  }

  @Test
  void givenAListOfFamilies_thenThePresentsShouldBeGroupedOnTheSleighByFamily() {
    List<Present> presents = getPresents();
    int numOfElves = 4;
    var elfPool = new ElfPool(numOfElves);
    var sleigh = new Sleigh();
    var toyMachine = new ToyMachine(presents);

    var sleighLoader = new MultiElfSleighLoader(elfPool, sleigh, toyMachine);
    var time = sleighLoader.load();

    var presentsByFamily = presents.stream().collect(Collectors.groupingBy(p -> p.familyId));
    assertFamilyPresents(presents.size(), presentsByFamily, numOfElves, sleigh, time);
  }

  @Test
  void givenAListOfFamilies_andAListOfNaughtyFamilies_thenTheNaughtyFamiliesShouldNotReceivePresents() {
    List<Present> presents = getPresents();
    Set<Integer> naughtyFamilies = getNaughtyFamilies(presents);
    var numOfElves = 4;
    var elfPool = new ElfPool(numOfElves);
    var sleigh = new Sleigh();
    var toyMachine = new ToyMachine(presents);

    var sleighLoader = new MultiElfSleighLoader(elfPool, sleigh, toyMachine, naughtyFamilies);
    var time = sleighLoader.load();

    var expectedPresentsByFamily = presents.stream()
        .filter(p -> !naughtyFamilies.contains(p.familyId))
        .collect(Collectors.groupingBy(p -> p.familyId));
    var expectedNumOfDeliveredPresents = (int) presents.stream()
        .filter(p -> !naughtyFamilies.contains(p.familyId)).count();
    assertFamilyPresents(expectedNumOfDeliveredPresents, expectedPresentsByFamily, numOfElves, sleigh, time);
  }

  private List<Present> getPresents() {
    return IntStream.rangeClosed(1, 300).mapToObj(i -> new Present(i, (int) (Math.random() * 100)))
        .collect(Collectors.toList());
  }

  private Set<Integer> getNaughtyFamilies(List<Present> presents) {
    return presents.stream()
        .map(p -> p.familyId)
        .filter(i -> i % 5 == 0)
        .collect(Collectors.toSet());
  }

  private void assertFamilyPresents(int presentsSize, Map<Integer, List<Present>> presentsByFamily,
      int numOfElves, Sleigh sleigh, int time) {
    assertEquals(presentsSize, sleigh.size());
    assertThat(presentsByFamily.keySet()).containsExactly(sleigh.presents.keySet().toArray(Integer[]::new));
    Integer familyId = presentsByFamily.entrySet().stream().filter(e -> !e.getValue().isEmpty()).map(Map.Entry::getKey)
        .findFirst().get();
    assertThat(presentsByFamily.get(familyId)).containsExactly(sleigh.presents.get(familyId).toArray(Present[]::new));
    assertThat((presentsSize / numOfElves) * 10).isCloseTo(time, Percentage.withPercentage(10.0));
  }
}
