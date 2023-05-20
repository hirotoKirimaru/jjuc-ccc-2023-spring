package kirimaru.api;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;

public class ControllerConstant {

  @AllArgsConstructor
  public enum Uri {
    USERS("/users", Map.of(
        HttpMethod.GET, UserRole.ALL,
        HttpMethod.POST, List.of(UserRole.SYSTEM_ADMIN)
    )),
    USERS2("/users2", Map.of(
        HttpMethod.GET, UserRole.ALL,
        HttpMethod.POST, List.of(UserRole.SYSTEM_ADMIN)
    ));
    ;

    public final String value;
    public final Map<HttpMethod, List<UserRole>> accepts;

    @AllArgsConstructor
    public enum UserRole {
      SYSTEM_ADMIN("1", "SYSTEM_ADMIN");

      public final String id;
      public final String value;

      public static final List<UserRole> ALL = List.of(SYSTEM_ADMIN);
    }
  }

}
