package kirimaru.api.sync;

import java.util.Collections;
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

  @MockBean(answer = Answers.CALLS_REAL_METHODS)
  DateTimeResolver dateTimeResolver;

  String url = "/users";

  @BeforeEach
  void beforeEach() {
    AuthUser user = new AuthUser(new User("user", "pass", Collections.emptyList()), new kirimaru.biz.domain.User());
    Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
    TestSecurityContextHolder.setAuthentication(authentication);
  }

  @Test
  void success() throws Exception {
    var result = this.mockMvc.perform(MockMvcRequestBuilders.get(url))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

    JSONAssert.assertEquals("{}", result.getResponse().getContentAsString(), true);
  }
}