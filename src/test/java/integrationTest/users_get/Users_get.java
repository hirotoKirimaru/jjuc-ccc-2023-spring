package integrationTest.users_get;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import integrationTest.helper.IntegrationTestsTemplate;
import kirimaru.api.ControllerConstant.Uri;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@SpringBootConfiguration
@EnableAutoConfiguration
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
    DbUnitTestExecutionListener.class
})
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Users_get extends IntegrationTestsTemplate {

//  @BeforeEach
//  @Order(0)
//  public void setup() {
//    flyway.migrate();
//    System.out.println("*******************************************");
//  }

  @Test
  // TODO: うまくうごかない
//  @DatabaseSetup("/integrationTest/users_get/A01/setup.xml")
  @ExpectedDatabase("/integrationTest/users_get/A01/expected.xml")
  void test_01() throws Exception {
    // GIVEN
    login();
    // NOTE: これでもいい
//    AuthUser user = new AuthUser(new User("user", "pass", Collections.emptyList()), new kirimaru.biz.domain.User());
//    Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
//    TestSecurityContextHolder.setAuthentication(authentication);

    // WHEN
    var response = get(Uri.USERS);

    // THEN
//    assertAll(
//        () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK)
////        ,
////        () -> JSONAssert.assertEquals(response.getBody(), "{}", true)
//    );
  }


}
