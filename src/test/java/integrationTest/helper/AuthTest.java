package integrationTest.helper;

import java.util.Collections;
import kirimaru.api.security.AuthUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.TestSecurityContextHolder;

public interface AuthTest {

  default void signIn() {
    AuthUser user = new AuthUser(
        new User("user", "pass", Collections.emptyList()),
        new kirimaru.biz.domain.User()
    );
    Authentication authentication = new UsernamePasswordAuthenticationToken(
        user,
        user.getPassword(),
        user.getAuthorities()
    );
    // Authenticationに登録する
    TestSecurityContextHolder.setAuthentication(authentication);
  }
}
