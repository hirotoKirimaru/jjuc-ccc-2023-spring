package kirimaru.api;

import lombok.AllArgsConstructor;

public class ControllerConstant {

  @AllArgsConstructor
  public enum Uri {
    USERS("/users");

    public final String value;
  }

}
