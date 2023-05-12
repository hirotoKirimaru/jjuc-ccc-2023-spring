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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * 認証フィルター
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter implements
    AuthErrorHelper {

  private AuthenticationManager authenticationManager;


  public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;

    // ログイン用のPATH
    setRequiresAuthenticationRequestMatcher(
        new AntPathRequestMatcher("/login", HttpMethod.POST.name()));

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
      return authenticationManager.authenticate(
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
        .setSubject(((User) auth.getCredentials()).getUsername())
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
//    super.unsuccessfulAuthentication(request, response, failed);
  }
}
