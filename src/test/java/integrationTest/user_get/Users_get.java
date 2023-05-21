package integrationTest.user_get;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.springtestdbunit.annotation.DatabaseSetup;
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
  @DatabaseSetup("/integrationTest/users_get/A01/setup.xml")
  void test_01() throws Exception {
    // GIVEN
//    login();
    // NOTE: これでもいい
    AuthUser user = new AuthUser(new User("user", "pass", Collections.emptyList()), new kirimaru.biz.domain.User());
    Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
    TestSecurityContextHolder.setAuthentication(authentication);

    // WHEN
    var response = get(Uri.USERS);

    // THEN
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//    JSONAssert.assertEquals(response.getBody(), "{}", true);
  }


}
