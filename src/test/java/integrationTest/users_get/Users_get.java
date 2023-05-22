package integrationTest.users_get;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import integrationTest.helper.IntegrationTestsTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@SpringBootConfiguration
@EnableAutoConfiguration
public class Users_get extends IntegrationTestsTemplate {

  @Test
  void test_01() throws Exception {
    // GIVEN
    setUpDatabase("/integrationTest/users_get/A01/setup.xml");
//    login();
    // NOTE: これでもいい
//    AuthUser user = new AuthUser(new User("user", "pass", Collections.emptyList()), new kirimaru.biz.domain.User());
//    Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
//    TestSecurityContextHolder.setAuthentication(authentication);

    // WHEN
//    var response = get(Uri.USERS);

    // THEN
//    assertAll(
//        () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK)
////        ,
////        () -> JSONAssert.assertEquals(response.getBody(), "{}", true)
//    );
    assertDatabase("/integrationTest/users_get/A01/expected.xml");
  }
}
