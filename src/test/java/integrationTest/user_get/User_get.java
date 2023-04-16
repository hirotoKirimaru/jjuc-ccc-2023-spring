package integrationTest.user_get;

import static org.assertj.core.api.Assertions.assertThat;

import integrationTest.helper.IntegrationTestsTemplate;
import java.net.URI;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

//public class User_get extends IntegrationTestsTemplate {



@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@SpringBootConfiguration
@Disabled("結合試験？")
public class User_get {

  @Autowired
  TestRestTemplate restTemplate;

  @Test
  void test_01() {
    var responseEntity = get();

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  protected ResponseEntity get() {
    return restTemplate.getForEntity(URI.create("get"), List.class);
  }
}
