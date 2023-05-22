package kirimaru.api.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
class UserDetailsServiceImpl implements UserDetailsService {

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return new AuthUser(User.builder()
        .username(username)
//        .password("{bcrypt}" + "DBのハッシュ")
        .password("{noop}password")
        .roles("SYSTEM_ADMIN")
        .build(), new kirimaru.biz.domain.User());
  }
}
