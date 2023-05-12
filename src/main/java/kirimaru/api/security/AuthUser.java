package kirimaru.api.security;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@Value
@EqualsAndHashCode(callSuper = false)
public class AuthUser extends User {
  kirimaru.biz.domain.User user;

  public AuthUser(UserDetails userDetails, kirimaru.biz.domain.User user) {
    super(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
    this.user = user;
  }
}
