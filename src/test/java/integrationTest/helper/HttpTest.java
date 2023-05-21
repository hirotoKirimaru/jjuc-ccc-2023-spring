package integrationTest.helper;

import java.net.URI;
import kirimaru.api.ControllerConstant.Uri;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public interface HttpTest {

  default ResponseEntity<String> get(TestRestTemplate restTemplate, Uri uri, HttpHeaders headers) {

    HttpEntity<String> stringHttpEntity = new HttpEntity<>(null, headers);

    var a = restTemplate.exchange(
        URI.create(uri.value), HttpMethod.GET, stringHttpEntity, String.class
    );

    return a;
  }
}
