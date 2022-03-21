package higorn.birthdaygreetings.application;

import higorn.birthdaygreetings.domain.InfraFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DependencyResolverTest {

  @Test
  void shouldGetAImplementationInstanceByItsAbstraction() {
    InfraFactory factory = DependencyResolver.getInstanceOf(InfraFactory.class);
    assertNotNull(factory);
  }
}
