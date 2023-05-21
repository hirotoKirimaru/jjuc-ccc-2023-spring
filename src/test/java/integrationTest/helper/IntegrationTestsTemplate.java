package integrationTest.helper;

import kirimaru.api.ControllerConstant;
import kirimaru.biz.service.date.DateTimeResolverImpl;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Answers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ComponentScan(basePackages = "kirimaru") // プロダクトコードをDIさせるために必要
@SpringBootConfiguration
@EnableAutoConfiguration
// RANDOM_PORTで起動させるために必要  https://stackoverflow.com/questions/48264118/spring-boot-test-fails-saying-unable-to-start-servletwebserverapplicationcontex
@Testcontainers
//@TestExecutionListeners({
//    MockitoTestExecutionListener.class // Mockをしたい
//})
public abstract class IntegrationTestsTemplate implements HttpTest {

  @LocalServerPort
  private int port;
  @Autowired
  public TestRestTemplate restTemplate;

  // NOTE: Mockはinterfaceが良いのだが、実装クラスをそのまま呼んだ方が好み
  @MockBean(answer = Answers.CALLS_REAL_METHODS)
  protected DateTimeResolverImpl dateTimeResolver;

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

  protected void login() {
    // reset
    HttpEntity<String> body = new HttpEntity<>("""
        {
           "email": "%1$s",
           "password": "%2$s"
        }
        """.formatted("testA@example.com", "password")
    );
    var result = restTemplate.exchange("/login", HttpMethod.POST, body, String.class);

    authorization = result.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
  }

  private String authorization;

//  protected ResponseEntity<?> get(ControllerConstant.Uri uri) {
//    return get(restTemplate, uri);
//  }
  private HttpHeaders getHttpHeaders(){
    var headers = new HttpHeaders();

    headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
    headers.add(HttpHeaders.AUTHORIZATION, authorization);
    return headers;
  }

  protected ResponseEntity<String> get(ControllerConstant.Uri uri) {
//    return get(restTemplate, uri, getHttpHeaders());
    return get(restTemplate, uri, null);
  }
}
