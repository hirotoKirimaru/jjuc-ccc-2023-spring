package kirimaru.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

/**
 * 認証フィルター
 */
@Component
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter implements
    AuthErrorHelper {

  public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
    setAuthenticationManager(authenticationManager);
    // ログイン用のPATH
    setRequiresAuthenticationRequestMatcher(
        new AntPathRequestMatcher("/login", HttpMethod.POST.name())
    );

    // ログイン用のID/PWのパラメータ名
    setUsernameParameter("email");
    setPasswordParameter("password");
  }


  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest req,
      HttpServletResponse res
  ) throws AuthenticationException {

    try {
      var form = new ObjectMapper().readValue(req.getInputStream(), LoginForm.class);
      return this.getAuthenticationManager().authenticate(
          new UsernamePasswordAuthenticationToken(
              form.email(),
              form.password(),
              new ArrayList<>()
          )
      );

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res,
      FilterChain chain, Authentication auth) throws IOException, ServletException {

    String token = Jwts.builder()
        .setSubject(((AuthUser) auth.getPrincipal()).getUsername())
        .setExpiration(new Date(System.currentTimeMillis() + 2000000))
        .signWith(SecurityConstants.KEY)
        .compact();

//    super.successfulAuthentication(req, res, chain, auth);
    res.addHeader(SecurityConstants.HEADER, SecurityConstants.TOKEN_PREFIX + token);
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request,
      HttpServletResponse response, AuthenticationException failed)
      throws IOException, ServletException {
    SecurityContextHolder.clearContext();

    createErrorResponse(response, HttpStatus.UNAUTHORIZED);
  }
}
