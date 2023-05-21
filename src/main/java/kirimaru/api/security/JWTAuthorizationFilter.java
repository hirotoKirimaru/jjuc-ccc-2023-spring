package kirimaru.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * 認可フィルター
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter implements
    AuthErrorHelper {

  public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain) throws IOException, ServletException {
//    super.doFilterInternal(request, response, chain);
    try {
      String token = request.getHeader(SecurityConstants.HEADER);
      if (token == null || !token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
        return;
      }
      String email = Jwts.parserBuilder()
          .setSigningKey(SecurityConstants.KEY)
          .build()
          .parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
          .getBody()
          .getSubject();
      var authUser = new AuthUser(
          User.builder()
              .username(email)
              .password("{noop}" + "password")
              .build(),
          new kirimaru.biz.domain.User()
      );
      UsernamePasswordAuthenticationToken authedToken = new UsernamePasswordAuthenticationToken(
          authUser, null, null);
      SecurityContextHolder.getContext().setAuthentication(authedToken);

      chain.doFilter(request, response);
    } catch (JwtException e) {
      createErrorResponse(response, HttpStatus.UNAUTHORIZED);
    }


  }
}
