package kirimaru.api.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * 認可フィルター
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter implements
    AuthErrorHelper {

  public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager);
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest req,
      HttpServletResponse res,
      FilterChain chain
  ) throws IOException, ServletException {
    try {
      var authedToken = getAuthToken(req);
      SecurityContextHolder.getContext().setAuthentication(authedToken);
      chain.doFilter(req, res);
//    super.doFilterInternal(req, res, chain);
    } catch (JwtException e) {
      createErrorResponse(res, HttpStatus.UNAUTHORIZED);
    }
  }

  private UsernamePasswordAuthenticationToken getAuthToken(
      HttpServletRequest request
  ) {
    AuthUser authUser = getAuthUser(request);
    return new UsernamePasswordAuthenticationToken(authUser, null, authUser.getAuthorities());
  }

  private AuthUser getAuthUser(HttpServletRequest request) {
    String token = request.getHeader(SecurityConstants.HEADER);
    if (token == null || !token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
      return createAnonymousUser();
    }
    String email = Jwts.parserBuilder()
        .setSigningKey(SecurityConstants.KEY)
        .build()
        .parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
        .getBody()
        .getSubject();
    return new AuthUser(
        User.builder()
            .username(email)
            .password("{noop}password")
            .roles("SYSTEM_ADMIN")
            .build(),
        new kirimaru.biz.domain.User()
    );
  }

  private AuthUser createAnonymousUser() {
    return new AuthUser(
        User.builder()
            .username("anonymousUser")
            .password("{noop}password")
            .roles("ANONYMOUS")
            .build(),
        new kirimaru.biz.domain.User()
    );
  }
}
