package kirimaru.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//public class JsonAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
//
//  ObjectMapper om = new ObjectMapper();
//  protected AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
//
//  protected JsonAuthenticationFilter(AuthenticationManager authenticationManager) {
//    super(new AntPathRequestMatcher("/login", HttpMethod.POST.name()));
//    this.setAuthenticationManager(authenticationManager);
//  }
//
//  @Override
//  public Authentication attemptAuthentication(HttpServletRequest request,
//      HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
//    LoginForm loginForm = om.readValue(request.getInputStream(), LoginForm.class);
//
//    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
//        loginForm.email(),
//        loginForm.password());
//
//    token.setDetails(authenticationDetailsSource.buildDetails(request));
//
//    return this.getAuthenticationManager().authenticate(token);
//  }
//}
