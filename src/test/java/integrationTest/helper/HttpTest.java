package integrationTest.helper;

import java.net.URI;
import kirimaru.api.ControllerConstant.Uri;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public interface HttpTest {

  private HttpHeaders getHttpHeaders() {
    var headers = new HttpHeaders();

    headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
//    headers.add(HttpHeaders.AUTHORIZATION, authorization);
    return headers;
  }
  default ResponseEntity<String> get(TestRestTemplate restTemplate, Uri uri) {
    return get(restTemplate, uri, getHttpHeaders());
  }

  default ResponseEntity<String> get(TestRestTemplate restTemplate, Uri uri, HttpHeaders headers) {

    HttpEntity<String> stringHttpEntity = new HttpEntity<>(null, headers);

    return restTemplate.exchange(
        URI.create(uri.value), HttpMethod.GET, stringHttpEntity, String.class
    );
  }
}
