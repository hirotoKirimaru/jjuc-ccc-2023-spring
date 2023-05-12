package integrationTest.helper;

import java.net.URI;
import kirimaru.api.ControllerConstant;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public interface HttpTest {

  default ResponseEntity<?> get(TestRestTemplate restTemplate, ControllerConstant.Uri uri) {

    HttpEntity<String> stringHttpEntity = new HttpEntity<>(null, null);

    return restTemplate.exchange(
        URI.create(uri.value), HttpMethod.GET, stringHttpEntity, String.class
    );
  }
}
