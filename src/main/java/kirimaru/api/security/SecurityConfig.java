package kirimaru.api.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.List;
import kirimaru.api.ControllerConstant;
import kirimaru.api.ControllerConstant.Uri;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
class SecurityConfig implements AuthErrorHelper {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http,
      AuthenticationManager authenticationManager,
      JWTAuthenticationFilter jwtAuthenticationFilter) throws Exception {

    http.csrf().disable()
        .cors().and()
        .formLogin().disable()
        .addFilter(new JWTAuthorizationFilter(authenticationManager))
        .addFilterAt(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .logout(logout -> logout.logoutRequestMatcher(
                new AntPathRequestMatcher("/logout", HttpMethod.GET.name()))
            .logoutSuccessHandler((req, res, auth) -> res.setStatus(HttpServletResponse.SC_OK))
            .invalidateHttpSession(true))

        .authorizeHttpRequests(auth -> {
          for (Uri uri : Uri.values()) {
            uri.accepts.forEach((k, v) -> auth.requestMatchers(k, uri.value)
                .hasAnyRole(v.stream().map(Enum::name).toArray(String[]::new)));
            auth.requestMatchers(HttpMethod.OPTIONS, uri.value).permitAll();
            auth.requestMatchers(uri.value).denyAll();
          }

          auth.requestMatchers("/actuator/health").permitAll()
              .requestMatchers("/").permitAll()
              .anyRequest().authenticated();
        })
        .exceptionHandling(
            handler -> handler
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.NOT_FOUND))
                .accessDeniedHandler((req, res, ex) -> {
                  createErrorResponse(res, HttpStatus.NOT_FOUND);
                })
        );

    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();

    configuration.setAllowedOrigins(List.of("*"));
    configuration.setAllowCredentials(true);
    configuration.setAllowedMethods(List.of("GET", "POST", "PATCH", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(List.of("*"));

    configuration.setExposedHeaders(List.of(HttpHeaders.AUTHORIZATION));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
}
