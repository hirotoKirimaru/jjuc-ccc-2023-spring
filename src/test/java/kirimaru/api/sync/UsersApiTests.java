package kirimaru.api.sync;

import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import kirimaru.api.security.AuthUser;
import kirimaru.api.sync.UsersApi;
import kirimaru.biz.service.UsersService;
import kirimaru.biz.service.date.DateTimeResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.TestSecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(UsersApi.class)
//@AutoConfigureWebClient
class UsersApiTests {

  @Autowired
  MockMvc mockMvc;
  @MockBean
  UsersService usersService;
  @MockBean
  private AuthenticationManager authenticationManager;

  @MockBean(answer = Answers.CALLS_REAL_METHODS)
  DateTimeResolver dateTimeResolver;

  String url = "/users";

  @BeforeEach
  void beforeEach() {
    AuthUser user = new AuthUser(new User("user", "pass", Collections.emptyList()),
        new kirimaru.biz.domain.User());
    Authentication authentication = new UsernamePasswordAuthenticationToken(user,
        user.getPassword(), user.getAuthorities());
    TestSecurityContextHolder.setAuthentication(authentication);
  }

  @Test
  void success() throws Exception {
    // GIVEN
    when(usersService.execute()).thenReturn(List.of(
        kirimaru.biz.domain.User.builder().userId("1").email("1@example.com").name("1").build(),
        kirimaru.biz.domain.User.builder().userId("2").email("2@example.com").name("2").build()));

    // WHEN
    var result = this.mockMvc.perform(
            MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE))
//        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

    // THEN
    // LANGUAGE=JSON
    var expectedBody = """
        {
          "users": [
            {
              "userId": "1",
              "email": "1@example.com",
              "name": "1"
            },
            {
              "userId": "2",
              "email": "2@example.com",
              "name": "2"
            }
          ]
        }
        """;
    JSONAssert.assertEquals(expectedBody, result.getResponse().getContentAsString(), true);
  }
}