package kirimaru.biz.repository.helper;


import org.junit.jupiter.api.BeforeAll;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class RepositoryTestTemplate {

  private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
      DockerImageName.parse("postgres"))
      .withUsername("testuser")
      .withPassword("password")
      .withDatabaseName("database");

  @BeforeAll
  public static void setUp() {
    postgres.start();
  }

  @DynamicPropertySource
  static void setup(DynamicPropertyRegistry registry) {
    // コンテナで起動中のPostgresへ接続するためのJDBC URLをプロパティへ設定
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
  }

}
