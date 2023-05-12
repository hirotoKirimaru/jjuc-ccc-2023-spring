package integrationTest.user_get;

import static org.assertj.core.api.Assertions.assertThat;

import integrationTest.helper.IntegrationTestsTemplate;
import kirimaru.api.ControllerConstant.Uri;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;

@SpringBootConfiguration
@EnableAutoConfiguration
public class User_get extends IntegrationTestsTemplate {

  @Test
  void test_01() {
    // WHEN
    var response = get(Uri.USERS);

    // THEN
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }
}
