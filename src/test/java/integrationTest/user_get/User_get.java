package integrationTest.user_get;

import static org.assertj.core.api.Assertions.assertThat;

import integrationTest.helper.IntegrationTestsTemplate;
import java.net.URI;
import java.util.List;
import kirimaru.api.ControllerConstant;
import kirimaru.api.ControllerConstant.Uri;
import kirimaru.api.UsersApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

//public class User_get extends IntegrationTestsTemplate {


//@SpringBootTest
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ComponentScan(basePackages = "kirimaru") // プロダクトコードをDIさせるために必要
//@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)

//@SpringBootTest
@SpringBootConfiguration
@EnableAutoConfiguration
// RANDOM_PORTで起動させるために必要  https://stackoverflow.com/questions/48264118/spring-boot-test-fails-saying-unable-to-start-servletwebserverapplicationcontex
//@Disabled("結合試験？")
public class User_get {
//  @Configuration
//  public class TestCustomizer {
//    @Bean
//    public ServletWebServerFactory serverSettings() {
//      TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory("/api/v1", port);
//      return factory;
//    }
//  }
//  @Autowired
//  private WebApplicationContext context;

//  @Autowired
//  private ServletWebServerFactory webServerFactory;

  //  @BeforeEach
//  public void setup() {
//    context.getServletContext().setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, context);
//  }
  @LocalServerPort
  private int port;

  //  @Autowired
//  RestTemplate restTemplate;
  @Autowired
  TestRestTemplate restTemplate;
//  @Autowired
//  RestTemplate restTemplate;

  @Test
  void test_01() {
//    TestRestTemplate restTemplate = new TestRestTemplate();
//    var restTemplate = new RestTemplate();

//    ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/api/v1/users", String.class);
    ResponseEntity<String> response = restTemplate.getForEntity("/users", String.class);
//    ResponseEntity<String> response4 = restTemplate.getForEntity("http://localhost:" + port + "/api/v1/users", String.class);
//    ResponseEntity<String> response2 = restTemplate.getForEntity("/actuator/mappings", String.class);
//    ResponseEntity<String> response3 = restTemplate.getForEntity("/actuator/health", String.class);

//    var responseEntity = get(Uri.USERS);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

//  protected ResponseEntity get(ControllerConstant.Uri uri) {
//
//    HttpEntity<String> stringHttpEntity = new HttpEntity<>(null, null);
//
//    return restTemplate.exchange(
//        URI.create(uri.value), HttpMethod.GET, stringHttpEntity, String.class
////        URI.create("/api/v1/users"), HttpMethod.GET, stringHttpEntity, String.class
////        URI.create("http://localhost:" + port + "/api/v1/users"), HttpMethod.GET, stringHttpEntity, String.class
//    );
//  }
}
