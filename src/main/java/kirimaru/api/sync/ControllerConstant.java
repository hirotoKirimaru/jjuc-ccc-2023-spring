package kirimaru.api.sync;

import lombok.AllArgsConstructor;

public class ControllerConstant {

  @AllArgsConstructor
  public enum Uri {
    USERS("/users");

    public final String value;
  }

}
