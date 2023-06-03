package integrationTest.users_get;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import integrationTest.helper.IntegrationTestsTemplate;
import java.util.Collections;
import kirimaru.api.ControllerConstant.Uri;
import kirimaru.api.security.AuthUser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.TestSecurityContextHolder;

@SpringBootConfiguration
@EnableAutoConfiguration
public class Users_get extends IntegrationTestsTemplate {

  @Test
  void test_01() throws Exception {
    // GIVEN
    setUpDatabase("/integrationTest/users_get/A01/setup.xml");
    signIn();

    // WHEN
    var response = get(Uri.USERS);

    // THEN
    assertAll(
        () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK)
        , () -> assertResponse(response, "/integrationTest/users_get/A01/expected.json")
        , () -> assertDatabase("/integrationTest/users_get/A01/expected.xml")
    );
  }
}
