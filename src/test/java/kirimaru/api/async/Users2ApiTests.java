package kirimaru.api.async;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.springSecurity;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

import java.util.Collections;
import kirimaru.api.security.AuthUser;
import kirimaru.biz.service.UsersService;
import kirimaru.biz.service.date.DateTimeResolver;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.TestSecurityContextHolder;
import org.springframework.test.web.reactive.server.WebTestClient;


@Disabled("SpringSecurityがわからない")
@WebFluxTest(Users2Api.class)
//@AutoConfigureWebClient
class Users2ApiTests {
  @Autowired
  ApplicationContext context;

//  @Autowired
  WebTestClient webTestClient;
  @MockBean
  UsersService usersService;

  @MockBean(answer = Answers.CALLS_REAL_METHODS)
  DateTimeResolver dateTimeResolver;

  String url = "/users2";

  @BeforeEach
  void beforeEach() {
//    AuthUser user = new AuthUser(new User("user", "pass", Collections.emptyList()), new kirimaru.biz.domain.User());
//    Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
//    TestSecurityContextHolder.setAuthentication(authentication);
    this.webTestClient = WebTestClient
        .bindToApplicationContext(this.context)
        // add Spring Security test Support
        .apply(springSecurity())
        .configureClient()
        .filter(basicAuthentication("user", "password"))
        .build();
  }

  @Test
  void success() throws Exception {
    this.webTestClient.get().uri(url)
        .exchange()
        .expectStatus().isOk()
        .expectBody(String.class)
        .value(res -> {
          try {
            JSONAssert.assertEquals("{}", res,true);
          } catch (JSONException e) {
            throw new RuntimeException(e);
          }

        });
//        .andReturn();
  }
}