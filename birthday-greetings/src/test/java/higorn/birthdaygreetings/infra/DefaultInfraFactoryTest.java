package higorn.birthdaygreetings.infra;

import higorn.birthdaygreetings.domain.InfraFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DefaultInfraFactoryTest {

  @Test
  void shouldGetInfraImplementations() {
    InfraFactory factory = new DefaultInfraFactory();
    assertNotNull(factory.getMessageService());
    assertNotNull(factory.getMessageComposer());
    assertNotNull(factory.getPersonDao());
  }
}
