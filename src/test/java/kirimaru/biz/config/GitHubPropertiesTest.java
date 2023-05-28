package kirimaru.biz.config;


import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(initializers = ConfigDataApplicationContextInitializer.class)
@EnableConfigurationProperties({GitHubProperties.class})
class GitHubPropertiesTest {

  @Autowired
  GitHubProperties target;

  @Test
  void test_01() {
    URI url = target.getUri();
    assertThat(url.toString()).isEqualTo("http://localhost:80/github");
  }

}