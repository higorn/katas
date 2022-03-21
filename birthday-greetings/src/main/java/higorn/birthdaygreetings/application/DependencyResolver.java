package higorn.birthdaygreetings.application;

import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

public class DependencyResolver {
  public static <T> T getInstanceOf(Class<T> subClass) {
    return getInstancesOf(subClass).get(0);
  }

  private static <T> List<T> getInstancesOf(Class<T> subClass) {
    var instances = ServiceLoader.load(subClass).stream()
        .map(ServiceLoader.Provider::get)
        .collect(Collectors.toList());
    if (instances.isEmpty())
      throw new ImplementationNotFoundException();
    return instances;
  }

  public static class ImplementationNotFoundException extends RuntimeException {
  }
}
