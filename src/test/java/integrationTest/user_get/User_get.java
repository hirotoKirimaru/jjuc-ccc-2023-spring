package integrationTest.user_get;

import static org.assertj.core.api.Assertions.assertThat;

import integrationTest.helper.IntegrationTestsTemplate;
import java.net.URI;
import java.util.List;
import kirimaru.api.ControllerConstant;
import kirimaru.api.ControllerConstant.Uri;
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
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.WebApplicationContext;

//public class User_get extends IntegrationTestsTemplate {


//@SpringBootTest
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@SpringBootConfiguration
@EnableAutoConfiguration
// RANDOM_PORTで起動させるために必要  https://stackoverflow.com/questions/48264118/spring-boot-test-fails-saying-unable-to-start-servletwebserverapplicationcontex
@Disabled("結合試験？")
public class User_get {
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

  @Autowired
  TestRestTemplate restTemplate;

  @Test
  void test_01() {
    var responseEntity = get(Uri.USERS);

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  protected ResponseEntity get(ControllerConstant.Uri uri) {

    HttpEntity<String> stringHttpEntity = new HttpEntity<>(null, null);

    return restTemplate.exchange(
        URI.create(uri.value), HttpMethod.GET, stringHttpEntity, String.class
//        URI.create("/api/v1/users"), HttpMethod.GET, stringHttpEntity, String.class
//        URI.create("/users"), HttpMethod.GET, stringHttpEntity, String.class
    );
  }
}
