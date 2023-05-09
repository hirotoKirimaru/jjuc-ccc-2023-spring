package kirimaru.api.async.sync;

import kirimaru.api.sync.async.Users2Api;
import kirimaru.biz.service.UsersService;
import kirimaru.biz.service.date.DateTimeResolver;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.BodyContentSpec;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@WebMvcTest(Users2Api.class)
//@AutoConfigureWebClient
class Users2ApiTests {

  @Autowired
  WebTestClient webTestClient;
  @MockBean
  UsersService usersService;

  @MockBean(answer = Answers.CALLS_REAL_METHODS)
  DateTimeResolver dateTimeResolver;

  String url = "/users2";

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