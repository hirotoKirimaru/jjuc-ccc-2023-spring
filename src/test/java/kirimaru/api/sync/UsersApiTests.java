package kirimaru.api.sync;

import kirimaru.api.sync.sync.UsersApi;
import kirimaru.biz.service.UsersService;
import kirimaru.biz.service.date.DateTimeResolver;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

  @Test
  void success() throws Exception {
    var result = this.mockMvc.perform(MockMvcRequestBuilders.get(url))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

    JSONAssert.assertEquals("{}", result.getResponse().getContentAsString(), true);
  }
}