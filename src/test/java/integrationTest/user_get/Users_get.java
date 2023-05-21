package integrationTest.user_get;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import integrationTest.helper.IntegrationTestsTemplate;
import kirimaru.api.ControllerConstant.Uri;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;

@SpringBootConfiguration
@EnableAutoConfiguration
public class Users_get extends IntegrationTestsTemplate {

  @Test
  @DatabaseSetup("/integrationTest/users_get/A01/setup.xml")
  void test_01() throws Exception {
    // GIVEN
    login();

    // WHEN
    var response = get(Uri.USERS);

    // THEN
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//    JSONAssert.assertEquals(response.getBody(), "{}", true);
  }


}
