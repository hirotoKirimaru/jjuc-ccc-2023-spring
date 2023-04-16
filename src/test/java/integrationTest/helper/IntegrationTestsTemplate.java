package integrationTest.helper;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(classes = {}, webEnvironment = WebEnvironment.RANDOM_PORT)

//@SpringBootConfiguration
@Testcontainers
//@TestExecutionListeners({
//    MockitoTestExecutionListener.class // Mockをしたい
//})
public abstract class IntegrationTestsTemplate {

  @Autowired
  TestRestTemplate restTemplate;

  private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
      DockerImageName.parse("postgres")).withUsername("devuser").withPassword("devuser")
      .withDatabaseName("devdb");

  @DynamicPropertySource
  static void setup(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url",
        postgres::getJdbcUrl); // コンテナで起動中のPostgresへ接続するためのJDBC URLをプロパティへ設定
  }

  protected ResponseEntity get() {
    return restTemplate.getForEntity(URI.create("get"), List.class);
  }
}
