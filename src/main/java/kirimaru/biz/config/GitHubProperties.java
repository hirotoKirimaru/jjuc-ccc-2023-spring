package kirimaru.biz.config;

import java.net.URI;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "external.github")
public class GitHubProperties {
  private String host;
  private String protocol;
  private String port;
  private String endpoint;
  private String timeout;

  @ToString.Include(name = "rootUrl") // デフォルトはメソッド名になる
  public URI getUri() {
    return URI.create(protocol + "://" + host + ":" + port + "/" + endpoint);
  }
}
