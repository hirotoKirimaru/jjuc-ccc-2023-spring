package integrationTest.users_get;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import integrationTest.helper.IntegrationTestsTemplate;
import java.io.IOException;
import java.util.Collections;
import kirimaru.api.ControllerConstant.Uri;
import kirimaru.api.security.AuthUser;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.TestSecurityContextHolder;
import org.testcontainers.shaded.org.apache.commons.io.IOUtils;

@SpringBootConfiguration
@EnableAutoConfiguration
public class Users_get extends IntegrationTestsTemplate {

  @Test
  void test_01() throws Exception {
    // GIVEN
    setUpDatabase("/integrationTest/users_get/A01/setup.xml");
//    login();
    // NOTE: これでもいい
    AuthUser user = new AuthUser(new User("user", "pass", Collections.emptyList()),
        new kirimaru.biz.domain.User());
    Authentication authentication = new UsernamePasswordAuthenticationToken(user,
        user.getPassword(), user.getAuthorities());
    TestSecurityContextHolder.setAuthentication(authentication);

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
